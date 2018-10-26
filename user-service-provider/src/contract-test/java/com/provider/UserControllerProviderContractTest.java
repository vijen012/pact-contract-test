package com.provider;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.UserServiceProvicerApplication;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.VerificationReports;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;

@RunWith(SpringRestPactRunner.class)
@SpringBootTest(classes = UserServiceProvicerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Provider("user-service-provider")
@PactFolder("../user-service-consumer/target/pacts")
@VerificationReports({"console", "markdown"})
public class UserControllerProviderContractTest {
		
//	public final Target target = new HttpTarget("http", "localhost", 7070);

	@TestTarget
	public final Target target = new SpringBootHttpTarget();
	
    @State({"user-service-provider api should return response with 200 when request is make with valid userId"})
    public void ShouldReturnResponseStatusCode200() throws Exception {
 
    }
    
    @State({"user-service-provider api should return response status code 201 when request is make with valid request data"})
    public void ShouldReturnResponseStatusCode201() throws Exception {
 
    }
}
