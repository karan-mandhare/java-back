package com.edigest.journelApp.controller;

import com.edigest.journelApp.entity.User;
import com.edigest.journelApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/health-check")
	public String healthCheck() {
		return "Ok";
	}

	@PostMapping("/create-user")
	public ResponseEntity<String> createUser(@RequestBody User user){
		boolean ans = userService.saveNewUser(user);
		if(ans) {
			return new ResponseEntity<>("user created successfully.", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("error occur while user creation.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
