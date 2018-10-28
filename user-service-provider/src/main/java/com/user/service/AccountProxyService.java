package com.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.data.AccountResponseData;

@Service
public class AccountProxyService {
	
	@Value("${accountServiceUrl}")
	private String accountServiceUrl;
	
	public void setAccountServiceUrl(String accountServiceUrl) {
		System.out.println("==============================="+ accountServiceUrl);
		this.accountServiceUrl = accountServiceUrl;
	}

	public AccountResponseData getUserAccounts( ) {
		System.out.println("==============================="+ accountServiceUrl);
		final RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		String serviceUrl = accountServiceUrl +"/account-service/accounts";
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<AccountResponseData> accountResponseData = restTemplate.exchange(serviceUrl, HttpMethod.GET, requestEntity, AccountResponseData.class);
		return accountResponseData.getBody();
	}
}
