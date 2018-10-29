package com.consumer.userservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.user.data.AccountResponseData;
import com.user.data.UserRequestData;
import com.user.data.UserResponseData;
import com.user.service.UserServiceClient;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceConsumerContractTest {

//	@Rule
//	public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("user-service-provider", "localhost", 8081, this);
	
	@Autowired
	private UserServiceClient userServiceClient;

	@Rule
	public PactProviderRuleMk2 mockTestProvider = new PactProviderRuleMk2("user-service-provider", this);

	@Pact(provider = "user-service-provider", consumer = "user-service-consumer")
	public RequestResponsePact createPactForGetUser(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		
		return builder
				.given("user-service-provider api should return response with 200 when request is make with valid userId")
					.uponReceiving("should return response status code 200 and user data for valid userId") 
						.path("/users/100")
						.method("GET")
					.willRespondWith()
						.status(200)
						.headers(headers)
						.body("{\"id\": 100, \"title\": \"Miss\", \"name\": \"Cass Martin\"}")
				.toPact();
	}

	@Pact(provider = "user-service-provider", consumer = "user-service-consumer")
	public RequestResponsePact createPactForCreateUser(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");

		return builder
				.given("user-service-provider api should return response status code 201 when request is make with valid request data")
					.uponReceiving("should return response status code 201 for valid user request")
						.path("/users")
						.headers(headers)
						.method("POST")
						.body("{\"title\":\"Mr\",\"name\":\"John Clark\"}")
					.willRespondWith()
						.status(201)
						.headers(headers)
						.body("{\"id\": 1, \"title\": \"Mr\", \"name\": \"John Clark\"}")
				.toPact();
	}
	
	@Pact(provider = "user-service-provider", consumer = "user-service-consumer")
	public RequestResponsePact createPactForGetUserAccounts(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		return builder
				.given("user-service-provider api should return status code 200 and account data when request is make with valid userId")
					.uponReceiving("should return response status code 200 and account data for valid userId") 
						.path("/users/100/accounts")
						.method("GET")
					.willRespondWith()
						.status(200)
						.headers(headers)
						.body("{\"accountType\":\"Current\",\"accountNumber\":\"123456789\"}")
				.toPact();
	}

	@Test
	@PactVerification(value = "user-service-provider", fragment = "createPactForGetUser")
	public void UserServiceProvider_ShouldReturnUserDataWhenRequestIsMakeWithValidUserId() {
		UserResponseData expectedData = new UserResponseData();
		expectedData.setId(100L);
		expectedData.setTitle("Miss");
		expectedData.setName("Cass Martin");

/*		final RestTemplate restTemplate = new RestTemplate();
		String url = mockTestProvider.getUrl() + "/users/100";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<UserResponseData> userResponseData = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				UserResponseData.class);*/
		
		userServiceClient.setUserServiceUrl(mockTestProvider.getUrl());
		ResponseEntity<UserResponseData> userResponseData = userServiceClient.getUser(100L);
		assertThat(userResponseData.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(userResponseData.getBody().toString()).isEqualTo(expectedData.toString());
	}

	@Test
	@PactVerification(value = "user-service-provider", fragment = "createPactForCreateUser")
	public void UserServiceProvider_ShouldReturn201WhenRequestIsMakeWithValidUserData() {
		UserRequestData userRequestData = new UserRequestData();
		userRequestData.setTitle("Mr");
		userRequestData.setName("John Clark");

/*		final RestTemplate restTemplate = new RestTemplate();
		String url = mockTestProvider.getUrl() + "/users";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(userRequestData, headers);
		ResponseEntity<Void> userResponseData = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);*/
		
		userServiceClient.setUserServiceUrl(mockTestProvider.getUrl());
		ResponseEntity<Void> userResponseData = userServiceClient.CreateUser(userRequestData);
		assertThat(userResponseData.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@PactVerification(value = "user-service-provider", fragment = "createPactForGetUserAccounts")
	public void UserServiceProvider_ShouldReturnUserAccountDataWhenRequestIsMakeWithValidUserId() {
		AccountResponseData expectedData = new AccountResponseData();
		expectedData.setAccountType("Current");
		expectedData.setAccountNumber("123456789");

/*		final RestTemplate restTemplate = new RestTemplate();
		String url = mockTestProvider.getUrl() + "/users/100/accounts";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<AccountResponseData> accountResponseData = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				AccountResponseData.class);*/
		
		userServiceClient.setUserServiceUrl(mockTestProvider.getUrl());
		ResponseEntity<AccountResponseData> accountResponseData = userServiceClient.getUserAccounts(100L);
		assertThat(accountResponseData.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(accountResponseData.getBody().toString()).isEqualTo(expectedData.toString());
	}	

}
