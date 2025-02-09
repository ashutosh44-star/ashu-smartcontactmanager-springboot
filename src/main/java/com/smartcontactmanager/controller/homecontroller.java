package com.smartcontactmanager.controller;

import java.security.Principal;
 
import javax.validation.Valid;  
       
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
  
import com.smartcontactmanager.EmailService.EmailService;
import com.smartcontactmanager.Service.OTP_Generator;
import com.smartcontactmanager.dao.DaoRepo;
import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.jwt.JwtHelper;
import com.smartcontactmanager.jwt.MyAuthProvider;
import com.smartcontactmanager.loginConfig.CustomUserDetails;
import com.smartcontactmanager.loginConfig.UserServiceImpl; 

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class homecontroller {

	private String name;
	private String otp;

	@Autowired
	private DaoRepo dao;

	@Autowired 
	private MyAuthProvider provider;

	@Autowired
	private EmailService service;

	@Autowired
	private UserServiceImpl impl;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtHelper helper;
	@Autowired  
	private OTP_Generator otp_Generator;
 
	@GetMapping("/")
	private String home(Model m) {
		m.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}

	@GetMapping("/about") 
	private String about(Model m) {
		m.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}

	@GetMapping("/signup")
	private String signup(Model m) {
		User user = new User();
		m.addAttribute("user", user);
		m.addAttribute("title", "SignUp - Smart Contact Manager");
		return "signup";
	}

//	@RequestMapping(value = "/register", method = { RequestMethod.GET, RequestMethod.POST })
	@PostMapping("/register")
	private String rgisterHere(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") Boolean agreement, Model m) {
		try {
 			
			if (result.hasErrors()) {
				return "signup";
			} else if (!agreement) {
				m.addAttribute("alert", "You must agree with terms and conditions !!");
				m.addAttribute("type", "warning"); 
				return "signup";
			} else { 
				otp=OTP_Generator.generateOTP(6);
				boolean sent = this.service.sendEmail(user.getEmail(), "Your OTP is "+otp, "OTP - Verification");
				if (sent) {
					name = user.getName();
					m.addAttribute("email", user.getEmail());
					m.addAttribute("pass", user.getPassword());
					m.addAttribute("function", "register");
					return "OTP_Verification";
				} else {
					m.addAttribute("OTP", "Not Sent");
					m.addAttribute("alert", "Invalid Email Id..!");
					m.addAttribute("type", "warning");
					m.addAttribute("user",user);
					return "signup";
				}
			}
		} catch (Exception e) {
			if (e.getMessage().contains("Duplicate")) {
				m.addAttribute("alert", "User Already Exist..!");
				m.addAttribute("type", "warning");
			}
		}
		return "signup";
	}
 
	@GetMapping("/login")
	private String Customlogin(Model m, @ModelAttribute("login") User user,
			@RequestParam(value = "error", required = false) Boolean error) {
		String alert = (String) m.asMap().get("alert");
		String type = (String) m.asMap().get("type");
		String email = (String) m.asMap().get("userEmail");
		if (alert != "" || alert != null) {
			m.addAttribute("alert", alert);
			m.addAttribute("type", type);
		}
		if (email != "" || email != null)   {
			user.setEmail(email);
		}
		m.addAttribute("user", user); 
		m.addAttribute("title", "Login - Smart Contact Manager");
		return "login";
	}
	@RequestMapping("/error")
    public String handleError() {
        return "login";  // Ensure error.html exists in templates folder
    }

	@PostMapping("/dologin") 
	private String Validatelogin(@Valid @ModelAttribute("login") User user,BindingResult result,
			@RequestParam("username") String username, Model m,
			RedirectAttributes redir,HttpServletResponse response) {
		    m.addAttribute("user", user); 
		try {
			m.addAttribute("title", "Dashboard - Smart Contact Manager");
//			 m.addAttribute("title", "Dashboard - " + user.getName());
			if(result.hasErrors()) {
				return "redirect:/login";
			}
			Authentication authenticate = provider
					.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
			UserDetails userDetails = impl.loadUserByUsername(username);
  
			String token = this.helper.generateToken(userDetails);
			System.out.println("token========"+token);
//			String EncryptedToken=passwordEncoder.encode(token);
//			m.addAttribute("user", user); 
			m.addAttribute("token",token);
			m.addAttribute("login","Success");

		} catch (Exception e) {
			 
			m.addAttribute("alert", "Invalid Username or Password  !!");
			m.addAttribute("type", "warning");
			return "login";
		}
		 
		return "user/dashboard"; 
	}

	@PostMapping("/otpVerify")
	private String OtpVerification(Model m, @RequestParam("otp") String Otp, @RequestParam("email") String email,
			@RequestParam("pass") String pass, RedirectAttributes redir) {
		m.addAttribute("function", "register");
		m.addAttribute("email", email);
		m.addAttribute("pass", pass);
		if (Otp.equals(otp)) {

			try {
				User user = new User();
				redir.addFlashAttribute("userEmail", email);
				user.setName(name);
				user.setEmail(email);
				user.setRole("ROLE_USER");
				user.setEnabled("Enabled");
				user.setImageurl("blank.png");
				user.setPassword(passwordEncoder.encode(pass));
				dao.save(user);
				redir.addFlashAttribute("alert", "Your account has been successfully created, You can access now 😊");
				redir.addFlashAttribute("type", "success");
				otp="";
				m.addAttribute("title", "Success - Smart Contact Manager");
				return "redirect:/login";
			} catch (Exception e) {
				if (e.getMessage().contains("Duplicate")) {
					redir.addFlashAttribute("alert", "User Already Exist..!");
					redir.addFlashAttribute("type", "warning");
					otp="";
					return "redirect:/login";
 				}
			}

		} else {
			m.addAttribute("alert", "Incorrect OTP..!!");
			m.addAttribute("type", "warning");

		}
		return "OTP_Verification";
	}
  

@GetMapping("/forgotpassword")
private String ForgotPassword(Model m,
		 RedirectAttributes redir) {
	m.addAttribute("function", "passwordreset");

	return "forgotpassword";
}
@PostMapping("/resetPassword")
private String ResestPassword(Model m, RedirectAttributes redir,@RequestParam("email") String email) {
	
	try {

		
		UserDetails user = impl.loadUserByUsername(email);
	}catch (Exception e) {
		m.addAttribute("email",email);
		m.addAttribute("alert","User not found..!");
		m.addAttribute("type","warning");
		
		return "forgotpassword";
	}
	
      otp=OTP_Generator.generateOTP(6);
    boolean sent = this.service.sendEmail(email, "Your OTP is "+otp, "OTP - Verification");
	if (sent) {
		m.addAttribute("function", "passwordreset");
		m.addAttribute("email",email);
	
				return "OTP_Verification";
	} 
	

	return "forgotpassword";
}
@PostMapping("/changeyourpassword")
private String OtpVerificationForPasswordReset(Model m, @RequestParam("email") String email,
		 @RequestParam("otp") String Otp, RedirectAttributes redir) {

	m.addAttribute("email",email);
	
	m.addAttribute("email", email);
		if (Otp.equals(otp)) {
			return "changePassword";
		
	} else {
		m.addAttribute("alert", "Incorrect OTP..!!");
		m.addAttribute("type", "warning");
		m.addAttribute("function", "passwordreset");
		return "OTP_Verification";
	
	}
			
}


@PostMapping("/passwordUpdate")
private String UpdatingPassword(Model m, @RequestParam("email") String email,
		 @RequestParam("password") String password, RedirectAttributes redir) {

		try {
			
			User user = dao.getUserByEmail(email);
		    user.setPassword(passwordEncoder.encode(password));
			dao.save(user);
			m.addAttribute("userEmail", email);
			redir.addFlashAttribute("alert", "Your password has been changed successfully.");
			redir.addFlashAttribute("type", "success");
			otp="";
			return "redirect:/login";
		} catch (Exception e) {
			if (e.getMessage()!="") {
				redir.addFlashAttribute("alert", "Something went wrong, please check weather user exist or not..!");
				redir.addFlashAttribute("type", "warning");
				otp="";
				return "redirect:/login";
			}
		}

			return "changePassword";	
}


}

