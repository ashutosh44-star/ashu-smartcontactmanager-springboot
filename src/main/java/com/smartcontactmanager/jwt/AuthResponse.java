package com.smartcontactmanager.jwt;

import java.util.Collection;

public class AuthResponse {
	
	private String userId;
	private String jwtTokenValue;
	private String refreshJwtTokenValue;
	private Collection<String> authorities;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getJwtTokenValue() {
		return jwtTokenValue;
	}
	public void setJwtTokenValue(String jwtTokenValue) {
		this.jwtTokenValue = jwtTokenValue;
	}
	public String getRefreshJwtTokenValue() {
		return refreshJwtTokenValue;
	}
	public void setRefreshJwtTokenValue(String refreshJwtTokenValue) {
		this.refreshJwtTokenValue = refreshJwtTokenValue;
	}
	public Collection<String> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<String> authorities) {
		this.authorities = authorities;
	}
	
	

}
