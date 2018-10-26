package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.user.data.UserResponseData;

@SpringBootApplication
public class UserServiceConsumerApplication {
	
	private void getUser(Integer userId) {
		final RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:7070/users/" + userId;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<UserResponseData> userResponseData = restTemplate.exchange(url, HttpMethod.GET, requestEntity, UserResponseData.class);
		System.out.println("============>"+ userResponseData.getBody());
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceConsumerApplication.class, args);
		new UserServiceConsumerApplication().getUser(1);
	}
}
