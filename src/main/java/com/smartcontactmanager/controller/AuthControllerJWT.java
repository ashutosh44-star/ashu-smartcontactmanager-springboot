package com.smartcontactmanager.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontactmanager.dao.DaoRepo;
import com.smartcontactmanager.jwt.JwtHelper;
import com.smartcontactmanager.jwt.JwtRequest;
import com.smartcontactmanager.jwt.JwtResponse;
import com.smartcontactmanager.jwt.MyAuthProvider;
//import com.smartcontactmanager.jwt.MyAuthProvider;
import com.smartcontactmanager.jwt.jwtConfig;
import com.smartcontactmanager.loginConfig.CustomUserDetails;
import com.smartcontactmanager.loginConfig.UserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthControllerJWT {

	@Autowired
	private UserServiceImpl impl;

	@Autowired
	private MyAuthProvider provider;

	@Autowired
	private JwtHelper helper;

	private Logger logger = LoggerFactory.getLogger(AuthControllerJWT.class);

	String email;

	@PostMapping("/login")
	public String login(@RequestBody JwtRequest request) {
		email = request.getUsername();
        System.out.println("emsil==========="+email);
		try {
			provider.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (Exception e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}

		UserDetails userDetails = impl.loadUserByUsername(request.getUsername());

		String token = this.helper.generateToken(userDetails);
		System.out.println("token===="+token);
		/*
		 * JwtResponse response = JwtResponse.builder() .jwtToken(token)
		 * .username(userDetails.getUsername()).build();
		 */
		return token;
	}

	@GetMapping("/test")
	public ResponseEntity<String> get() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("user of authentication----" + authentication.getName());
		return ResponseEntity.ok("Hello");
	}

	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Credentials Invalid !!";
	}

}
