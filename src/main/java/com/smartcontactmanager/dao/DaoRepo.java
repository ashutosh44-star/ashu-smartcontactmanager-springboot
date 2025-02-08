package com.smartcontactmanager.dao;



import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.smartcontactmanager.entities.User;

@Component
@EnableJpaRepositories
public interface DaoRepo extends JpaRepository<User, Integer> {
	
	
//	@Query("select u from User u where LOWER(u.email) = LOWER(:email)")
	@Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
	public User getUserByEmail(@Param("email") String email);
    
}

