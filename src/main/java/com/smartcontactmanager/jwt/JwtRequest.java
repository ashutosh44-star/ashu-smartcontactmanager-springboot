package com.smartcontactmanager.jwt;

import org.springframework.stereotype.Component;


@Component
public class JwtRequest {
	public JwtRequest() {
	
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String username;
	private String password;
	@Override
	public String toString() {
		return "JwtRequest [username=" + username + ", password=" + password + "]";
	}

}
