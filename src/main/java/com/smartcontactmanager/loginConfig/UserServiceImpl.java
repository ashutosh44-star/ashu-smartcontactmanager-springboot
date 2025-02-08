package com.smartcontactmanager.loginConfig;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.smartcontactmanager.dao.DaoRepo;
//import com.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.entities.User;

@Service
public class UserServiceImpl implements UserDetailsService {
	
	@Autowired
	private DaoRepo daoRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// fetching user by database---and checking if exists---verification 
		//----it will call username by using CUstomUserDetails return method
		User user=new User();
			user=daoRepo.getUserByEmail(username);
		if(user.equals(null)) {
			throw new UsernameNotFoundException("User not Found");
		}
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails; 

	}
	
}
