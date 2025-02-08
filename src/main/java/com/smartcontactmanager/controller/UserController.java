package com.smartcontactmanager.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.data.redis.RedisConnectionDetails.Standalone;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.smartcontactmanager.Service.OTP_Generator;
import com.smartcontactmanager.dao.ContactRepo;
import com.smartcontactmanager.dao.DaoRepo;
import com.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.entities.User;
//import com.smartcontactmanager.loginConfig.config;

@Controller
@RequestMapping("/user")
public class UserController {

	Integer cp;
	Integer id;
	String img="";
	@Autowired
	private DaoRepo repo;
	@Autowired
	private ContactRepo contactRepo;


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute
	public void CommonData(Model m, Principal principal) {
		User user = repo.getUserByEmail(principal.getName());
		m.addAttribute("user", user);
	}

	@GetMapping("/dashboard")
	public String dashboard(Model m, Principal principal) {
		 if (principal != null) {
		        User user = repo.getUserByEmail(principal.getName());
		        m.addAttribute("title", "Dashboard - " + user.getName());
		        return "user/dashboard";
		    } else {
		        return "redirect:/login";  // Redirect to login if no principal (unauthenticated)
		    }
	}
	

	@GetMapping("/addContact")
	public String addContact(Model m, Principal principal) {
		Contact contacts = new Contact();
		m.addAttribute("contacts", contacts);
		m.addAttribute("title", "Add Contact");
		return "user/addContact";
	}

	@PostMapping("/add_contact")
	public String add_Contact(Model m, Principal principal, @RequestParam("profileImage") MultipartFile file,
			@ModelAttribute Contact contact) {
			m.addAttribute("title", "Add Contact");

		try {
			User user = repo.getUserByEmail(principal.getName());
			user.getContacts().add(contact);
			contact.setUser(user);
			if (file.isEmpty()) {
				contact.setImage("blank.png");
				
			} else {
			
				contact.setImage(file.getOriginalFilename());
				File f = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			
			m.addAttribute("alert", "Your Contact has been added Successfully..!");
			m.addAttribute("type", "success");
			m.addAttribute("contacts", contact);
			repo.save(user);

		} catch (Exception e) {
			e.printStackTrace();
	
//			e.getMessage();
		}
		return "user/addContact";
	}

	@GetMapping("/showContacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model m, Principal principal) {
		User user = repo.getUserByEmail(principal.getName());
		Pageable pageable = PageRequest.of(page, 5);
		Page<Contact> contacts = contactRepo.findContactByUser(user.getId(), pageable);
		if (contacts.isEmpty()) {
			m.addAttribute("head","There is not any contact saved till date,You must add first to view your contacts");
			return "user/showContacts";
		} else {
			m.addAttribute("head", "View Your Contacts");
			m.addAttribute("contact", contacts);
			m.addAttribute("currentPage", page);
			cp=page;
			m.addAttribute("totalPage", (Integer) contacts.getTotalPages());
			String alert = (String) m.asMap().get("alert");
			String type = (String) m.asMap().get("type");
			System.out.println("string===="+alert+" "+type);
			if(alert != "" || alert != null) {
			m.addAttribute("alert", alert);
			m.addAttribute("type", type);
			}
			m.addAttribute("title", "View All Contacts");
			return "user/showContacts";
		}
	}

	@GetMapping("/contact/{cId}")
	public String showContactDetails(@PathVariable("cId") Integer cid, Model m, Principal principal) {
		Optional<Contact> contacOptional = this.contactRepo.findById(cid);
		Contact contact = contacOptional.get();
		m.addAttribute("title", "Viewing Contact - "+contact.getName());
		User user = repo.getUserByEmail(principal.getName());
		
		if(user.getId()==contact.getUser().getId()) {
		m.addAttribute("contact", contact);
		}
		return "user/contact_details";
	}
	@GetMapping("/delete/{cId}")
	public RedirectView  deleteContact(@PathVariable("cId") Integer cid, Model m, Principal principal,  RedirectAttributes redir) {
		User user = repo.getUserByEmail(principal.getName());
		Contact contact=contactRepo.getById(cid);
	
		contactRepo.delete(contact);
			m.addAttribute("delete", "delete");
			RedirectView redirectView= new RedirectView("/user/showContacts/"+cp,true);
			redir.addFlashAttribute("alert",contact.getName()+" has been deleted from records..!");
			redir.addFlashAttribute("type","success");

		    return redirectView;
			
		}
	
	@GetMapping("/update/{cId}")
	public String updateContactDetails(@PathVariable("cId") Integer cid, Model m, Principal principal) throws IOException {
		Optional<Contact> contacOptional = this.contactRepo.findById(cid);
		Contact contact = contacOptional.get();
//		String img=contactRepo.findById(cid);
		User user = repo.getUserByEmail(principal.getName());
		contact.setImage(contact.getImage());
		m.addAttribute("title", "Updating Contact - "+contact.getName());
		System.out.println("desription===="+contact.getDescription());
		if(user.getId()==contact.getUser().getId()) {
			contact.setDescription(contact.getDescription());
		m.addAttribute("contacts", contact);
		
		img=contact.getImage();
		System.out.println("contact image=="+contact.getImage());
		id=cid;
		}
		String alert = (String) m.asMap().get("alert");
		String type = (String) m.asMap().get("type");
		if(alert != "" || alert != null) {
		m.addAttribute("alert", alert);
		m.addAttribute("type", type);
		}
		return "user/updateContact";
	}
	
	@PostMapping("/updateContacts")
	public String updateContact(Model m, Principal principal, @RequestParam("profileImage") MultipartFile file,
			@ModelAttribute Contact contact,   RedirectAttributes redir) {
			m.addAttribute("title", "Update Your Contact");

		try {
			User user = repo.getUserByEmail(principal.getName());
			Contact contacts =contact;
			contacts.setUser(user);
			contacts.setCid(id);
			
			if (file.isEmpty()) {
				if(img != null || img!= "") {
					contact.setImage(img);
				}else {
					contact.setImage("blank.png");
				}
				 
			} else { 
			
				contact.setImage(file.getOriginalFilename());
				File f = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			contactRepo.save(contacts);
//			 m.addAttribute("cId", id);
			redir.addFlashAttribute("alert","Your contact has been updated successfully..!");
			redir.addFlashAttribute("type","success");
			m.addAttribute("contacts", contacts); 
			
		} catch (Exception e) {
			   e.printStackTrace();
		}
		return "redirect:/user/update/"+id;
	}
	
	@GetMapping("/userProfile")
	public String showUserProfileDetails( Model m, Principal principal) {
		/*
		 * Optional<Contact> contacOptional = this.contactRepo.findById(cid); Contact
		 * contact = contacOptional.get();   
		 */
		User user = repo.getUserByEmail(principal.getName());
		m.addAttribute("title", "User Profile - "+user.getName());
		
		return "user/userProfile";
	}

	@PostMapping("/updateImage")
	public String updateImage( Model m, Principal principal, @RequestParam("imageurl") MultipartFile file) {
		
		User user = repo.getUserByEmail(principal.getName());
		try {
		if (file.isEmpty()) {
				user.setImageurl("blank.png");			
		} else {
			user.setImageurl(file.getOriginalFilename());
			File f = new ClassPathResource("static/image").getFile();
			Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		}
		}catch (Exception e) {
			e.getMessage();
		}
		repo.save(user);
		m.addAttribute("title", "User Profile - "+user.getName());
		
		return "user/userProfile";
	}
	
	}
	

