package com.smartcontactmanager.entities;

import org.springframework.stereotype.Component;


//@Component
public class LoginResponse {
	public LoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	private String message;
	private String token;

}
