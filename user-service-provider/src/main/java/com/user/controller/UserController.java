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

import com.user.data.AccountResponseData;
import com.user.data.UserData;
import com.user.data.UserRequestData;
import com.user.service.UserService;

@RestController
//@EnableConfigurationProperties
//@ConfigurationProperties
public class UserController {
		
	@Autowired
	private UserService userService;
		
	@GetMapping(path = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserData> getUser(@PathVariable Long userId) {
		return new ResponseEntity<UserData>(userService.getUser(userId), HttpStatus.OK);
	}
	
	@PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserData> createUser(@RequestBody UserRequestData userRequestData) {
		return new ResponseEntity<>(userService.createUser(userRequestData), HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/users/{userId}/accounts")
	public ResponseEntity<AccountResponseData> getUserAccounts(@PathVariable Long userId) {	
		return new ResponseEntity<AccountResponseData>(userService.getUserAccounts(), HttpStatus.OK);
	}
}
