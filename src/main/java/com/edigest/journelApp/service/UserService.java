package com.edigest.journelApp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import com.edigest.journelApp.entity.User;
import com.edigest.journelApp.respository.UserRepository;

@Component
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	
	public boolean saveEntry(User user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(Arrays.asList("USER"));
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean saveUserEntry(User user) {
		try {
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	public Optional<User> findById(ObjectId id){
		return userRepository.findById(id);
	}
	
//	public UserRepository deleteById(ObjectId id) {
//		return userRepository.deleteById(id);
//	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	
}
