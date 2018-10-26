package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.data.UserData;

@RestController
public class UserController {
		
	@Autowired
	private UserData userData;
	
	@GetMapping(path = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserData> getUser(@PathVariable Integer userId) {
		userData.setTitle("Miss");
		userData.setName("Cass Martin");
		return new ResponseEntity<UserData>(userData, HttpStatus.OK);
	}
	
	@PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createUser(@RequestBody UserData userRequestData) {
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
