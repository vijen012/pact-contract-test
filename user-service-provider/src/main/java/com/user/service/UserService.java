package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.data.AccountResponseData;
import com.user.data.UserData;
import com.user.data.UserRequestData;
import com.user.repos.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private AccountProxyService accountProxyService;
	
	@Autowired
	private UserRepository userRepository;
				
	public UserData getUser(Long userId) {
		return userRepository.findById(userId).get();
	}
	
	public UserData createUser(UserRequestData userRequestData) {
		UserData userData = new UserData();
		userData.setTitle(userRequestData.getTitle());
		userData.setName(userRequestData.getName());
		return userRepository.save(userData);
	}
	
	public AccountResponseData getUserAccounts( ) {		
		return accountProxyService.getUserAccounts();
	}
}
