package com.consumer.userservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.user.data.UserResponseData;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class UserServiceConsumerContractTest {

//	@Rule
//	public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("user-service-provider", "localhost", 8081, this);

	@Rule
	public PactProviderRuleMk2 mockTestProvider = new PactProviderRuleMk2("user-service-provider", this);

	@Pact(provider = "user-service-provider", consumer = "user-service-consumer")
	public RequestResponsePact createPactForGet(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		return builder
				.given("user-service-provider api should return response with 200 when request is make with valid userId")
					.uponReceiving("should return response status code 200 and response body for valid userId") 
						.path("/users/1")
						.method("GET")
					.willRespondWith()
						.status(200)
						.headers(headers)
						.body("{\"title\":\"Miss\",\"name\":\"Cass Martin\"}")
				.toPact();
	}

	@Pact(provider = "user-service-provider", consumer = "user-service-consumer")
	public RequestResponsePact createPactForPost(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");

		return builder
				.given("user-service-provider api should return response status code 201 when request is make with valid request data")
					.uponReceiving("should return response status code 201 for valid request")
						.path("/users")
						.headers(headers)
						.method("POST")
						.body("{\"title\":\"Mr\",\"name\":\"John Clark\"}")
					.willRespondWith()
						.status(201)
				.toPact();
	}

	@Test
	@PactVerification(value = "user-service-provider", fragment = "createPactForGet")
	public void WhenMakeRequestToBookStore_WithValidBookIdEqualToOneShouldReturn200() {
		UserResponseData expectedData = new UserResponseData();
		expectedData.setTitle("Miss");
		expectedData.setName("Cass Martin");
		final RestTemplate restTemplate = new RestTemplate();
		String url = mockTestProvider.getUrl() + "/users/1";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<UserResponseData> userResponseData = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				UserResponseData.class);
		assertThat(userResponseData.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(userResponseData.getBody().toString()).isEqualTo(expectedData.toString());
	}

	@Test
	@PactVerification(value = "user-service-provider", fragment = "createPactForPost")
	public void WhenMakeRequestToBookStore_WithBookValidRequestShouldReturn201() {
		UserResponseData userData = new UserResponseData();
		userData.setTitle("Mr");
		userData.setName("John Clark");
		final RestTemplate restTemplate = new RestTemplate();
		String url = mockTestProvider.getUrl() + "/users";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(userData, headers);
		ResponseEntity<Void> userResponseData = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
		assertThat(userResponseData.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

}
