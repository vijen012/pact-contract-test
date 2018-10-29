package com.user.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.data.AccountResponseData;
import com.user.data.UserRequestData;
import com.user.data.UserResponseData;

@Service
public class UserServiceClient {
	
	private String userServiceUrl;

	public void setUserServiceUrl(String userServiceUrl) {
		this.userServiceUrl = userServiceUrl;
	}
	
	public ResponseEntity<UserResponseData> getUser(Long userId) {
		final RestTemplate restTemplate = new RestTemplate();
		String url = userServiceUrl + "/users/" + userId;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<UserResponseData> userResponseDataEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				UserResponseData.class);
		return userResponseDataEntity;
	}
	
	public ResponseEntity<Void> CreateUser(UserRequestData userRequestData) {
		final RestTemplate restTemplate = new RestTemplate();
		String url = userServiceUrl + "/users";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(userRequestData, headers);
		ResponseEntity<Void> userResponseDataEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
		return userResponseDataEntity;
	}
	
	public ResponseEntity<AccountResponseData> getUserAccounts(Long userId) {
		final RestTemplate restTemplate = new RestTemplate();
		String url = userServiceUrl + "/users/"+ userId + "/accounts";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<AccountResponseData> accountResponseDataEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				AccountResponseData.class);
		return accountResponseDataEntity;
	}
}
