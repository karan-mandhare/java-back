package com.edigest.journelApp.controller;

import com.edigest.journelApp.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journelApp.entity.User;
import com.edigest.journelApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/create")
	public ResponseEntity<String> createUser(@RequestBody User user){
		boolean ans = userService.saveNewUser(user);
		if(ans) {
			return new ResponseEntity<>("user created successfully.", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("error occur while user creation.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody User user){
//		User userInDb = userService.findByUsername(username);
//		if(userInDb != null) {
//			userInDb.setUsername(user.getUsername());
//			userInDb.setPassword(user.getPassword());
//			userService.saveEntry(userInDb);
//		}
//		return new ResponseEntity<User>(userInDb, HttpStatus.NO_CONTENT);

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = authUser.getName();

		User userInDb = userService.findByUsername(userName);
		userInDb.setUsername(user.getUsername());
		userInDb.setPassword(user.getPassword());
		userService.saveNewUser(userInDb);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}
	
	@DeleteMapping("/user")
	public ResponseEntity<?> deleteUser(){
		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		userRepository.deleteByUsername(authUser.getName());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	// hello
}
