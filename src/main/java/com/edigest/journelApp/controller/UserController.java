package com.edigest.journelApp.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	
	@GetMapping("/getall")
	public List<User> getAllUsers(){
		return userService.getAll();
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createUser(@RequestBody User user){
		boolean ans = userService.saveEntry(user);
		if(ans) {
			return new ResponseEntity<>("user created successfully.", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("error occur while user creation.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
		User userInDb = userService.findByUsername(username);
		if(userInDb != null) {
			userInDb.setUsername(user.getUsername());
			userInDb.setPassword(user.getPassword());
			userService.saveEntry(userInDb);
		}
		return new ResponseEntity<User>(userInDb, HttpStatus.NO_CONTENT);
	}
	
//	@DeleteMapping("/delete")
//	public ResponseEntity<User> deleteUser(@PathVariable ObjectId myId){
//		
//	}
	
}
