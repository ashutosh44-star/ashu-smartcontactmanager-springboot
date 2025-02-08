package com.smartcontactmanager.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.smartcontactmanager.dao.DaoRepo;
import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.loginConfig.CustomUserDetails;
import com.smartcontactmanager.loginConfig.UserServiceImpl;

@Component
public class MyAuthProvider implements AuthenticationProvider {
	@Autowired
	private UserServiceImpl userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
 
		System.out.println("In MyAuthProvider.authenticate(): "+authentication.getPrincipal());

		// Get the User from UserDetailsService
		String providedUsername = authentication.getPrincipal().toString();
		System.out.println("providedUsername====" + providedUsername);
		UserDetails user = userDetailsService.loadUserByUsername(providedUsername);
		System.out.println("User Details from UserService based on username-" + providedUsername + " : " + user);

		String providedPassword = authentication.getCredentials().toString();
		System.out.println("Postman Password=====" + providedPassword);
		String correctPassword = user.getPassword();
		System.out.println("User DB Password=====" + correctPassword);

		System.out.println("Provided Password - " + providedPassword + " Correct Password: " + correctPassword);

		// Authenticate   
		if (!bCryptPasswordEncoder.matches(providedPassword, user.getPassword())) {

			throw new RuntimeException("Incorrect Credentials");
		} else {

			System.out.println("Passwords Match....\n");
			Authentication authenticationResult = new UsernamePasswordAuthenticationToken(user,
					authentication.getCredentials(), user.getAuthorities());
			return authenticationResult;
		}
	}
	// return Authentication Object

	@Override
	public boolean supports(Class<?> authentication) {
		System.out.println("\nIn MyAuthProvider.supports(): ");
		System.out.println("Checking whether MyAuthProvider supports Authentication type\n");
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}