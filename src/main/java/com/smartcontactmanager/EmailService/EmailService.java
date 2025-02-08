package com.smartcontactmanager.EmailService;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.File;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.entities.EmailEntity;

@Component
public class EmailService {
	
	@Autowired
	private EmailEntity emailEntity;
	
	
	

public boolean sendEmail(String to,String message,String subject) {
 
	boolean sent=false;
//	String to = "ashutosh.jmd31@gmail.com";
	String from="xxxxxxxxx"; //----Used Dummy for git and now it will be skipped from next push onwards
//	String subject="Mail sent via Smart Contact Management";
//	String message="Message Received Successfully..!";
	try {
		String host="smtp.gmail.com";
		Properties ashu=System.getProperties();
		ashu.put("mail.smtp.host", host);
		ashu.put("mail.smtp.port", "587");
		ashu.put("mail.smtp.auth", "true");
		ashu.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getInstance(ashu, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "vkhd lorc ufnf ystp");
			}
		});
		
		session.setDebug(true);
		
		Message m=new MimeMessage(session);
		
//		MimeMessage m=new MimeMessage(session);
		try {
			m.setFrom(new InternetAddress(from));
			m.setSubject(subject);
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			m.setText(message);
			Transport.send(m);
			sent=true;
				} catch (Exception e) {
			e.printStackTrace();
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		}
	return sent;
	
}
}