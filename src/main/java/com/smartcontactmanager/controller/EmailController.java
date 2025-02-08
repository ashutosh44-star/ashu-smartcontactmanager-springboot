
 package com.smartcontactmanager.controller; 
 import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import
 org.springframework.web.bind.annotation.RestController;
 
 import com.smartcontactmanager.entities.EmailEntity;

import ch.qos.logback.core.subst.Token;
 
 @RestController
 @RequestMapping("/user")
 public class EmailController {
 
	@Autowired 
	private EmailEntity emailEntity; 
	
 	 @GetMapping("/sent")
      public String emailsent() {
    	  String token=UUID.randomUUID().toString();
//    	  Token t=new Token
    	  return token;
      }
 
 
 }
 
