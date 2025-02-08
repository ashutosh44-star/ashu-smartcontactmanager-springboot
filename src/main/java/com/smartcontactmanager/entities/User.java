package com.smartcontactmanager.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.*;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Size;

@Entity
@Component
public class User {
	
	public User() {
		super();
	}
	public User(int id,
			@Size(min = 4, max = 12, message = "Username length must be between 3 - 12 characters..!") String name,
			String email, String password, String role, String enabled, String imageurl, String about,
			List<Contact> contacts) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.imageurl = imageurl;
		this.about = about;
		this.contacts = contacts;
	}
	public User(User user) {
		
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Size(min = 4, max = 12, message = "Username length must be between 3 - 12 characters..!")
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	private String role;
	private String enabled;
	private String imageurl;
	@Column(length = 500)
	private String about;
//	@ManyToMany
//	private Set<Role> role;
	/*
	 * public void setRole(Set<Role> role) { this.role = role; }
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<Contact> contacts=new ArrayList<>();
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
//	public User() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", enabled=" + enabled + ", imageurl=" + imageurl + ", about=" + about + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		System.out.println("email=====User=="+email);
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public String getRole() {
//		return role;
//	}
//	public void setRole(String role) {
//		this.role = role;
//	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
//	User (User user){
//		
//		this.user=user;
//	}

}
