package com.smartcontactmanager.jwt;

import java.io.IOException;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smartcontactmanager.loginConfig.CustomUserDetails;
import com.smartcontactmanager.loginConfig.UserServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private org.slf4j.Logger logger =  LoggerFactory.getLogger(OncePerRequestFilter.class);
   
	
	@Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserServiceImpl impl;
    
//    @Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        //Authorization
    	String username = null;
        String token = null;
        String requestHeader = request.getHeader("Authorization");
        //Bearer 2352345235sdfrsfgsdfsdf
        logger.info(" Header :  {}", requestHeader);
        System.out.println(" Header :  {}"+ requestHeader);
        
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            //looking good
            token = requestHeader.substring(7);
            try {

                username = this.jwtHelper.getUsernameFromToken(token);
                System.out.println("Ashutosh====="+username);

            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }


        } else {
            logger.info("Invalid Header Value !! ");
        }


        //
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	System.out.println("context holder========");

            //fetch user detail from username------From DB
            UserDetails userDetails = this.impl.loadUserByUsername(username);
            System.out.println("inside ashuuuuuuuuuuuuuuuuuuuuuuuu===PAssword =="+userDetails.getPassword());
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            System.out.println("validated tokennnnnnnnnnnnnnnn==="+validateToken);
            if (validateToken) {
            	 logger.info("Inside Validate======Authorities=="+userDetails.getAuthorities());
                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                logger.info("Validation After Everyting==");
            } else {
                logger.info("Validation fails !!");
            }
        }

        filterChain.doFilter(request, response);


    }
}
