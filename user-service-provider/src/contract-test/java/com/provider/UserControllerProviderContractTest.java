package com.provider;

import static net.jadler.Jadler.closeJadler;
import static net.jadler.Jadler.initJadlerUsing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.UserServiceProvicerApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.user.controller.UserController;
import com.user.data.AccountResponseData;
import com.user.data.UserData;
import com.user.data.UserRequestData;
import com.user.service.UserService;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.VerificationReports;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import net.jadler.stubbing.server.jdk.JdkStubHttpServer;

@RunWith(SpringRestPactRunner.class)
@SpringBootTest(classes = UserServiceProvicerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Provider("user-service-provider")
//@PactFolder("../user-service-consumer/target/pacts")
@PactBroker(host = "shared-jenk-14.sandbox.local", port = "30080", protocol = "http") 
@VerificationReports({"console", "markdown"})
public class UserControllerProviderContractTest {
		
//	public final Target target = new HttpTarget("http", "localhost", 7070);
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;
	
	@InjectMocks
	@Autowired
	private UserController userController;
	
	@TestTarget
	public final Target target = new SpringBootHttpTarget();
	
	@Before
	public void setUp() {
//		MockitoAnnotations.initMocks(this);
		initJadlerUsing(new JdkStubHttpServer());
		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	
	@After
	public void tearDown() throws Exception {
		closeJadler();
	}
	
    @State({"user-service-provider api should return response with 200 when request is make with valid userId"})
    public void ShouldReturnUserDataAndStatusCode200() throws Exception {
    	UserData userData = new UserData();
    	userData.setId(100L);
		userData.setTitle("Miss");
		userData.setName("Cass Martin");
		when(userService.getUser(anyLong())).thenReturn(userData);
    }
    
    @State({"user-service-provider api should return response status code 201 when request is make with valid request data"})
    public void ShouldReturnResponseStatusCode201() throws Exception {
    	UserData userData = new UserData();
    	userData.setId(1L);
		userData.setTitle("Mr");
		userData.setName("John Clark");
    	when(userService.createUser(any(UserRequestData.class))).thenReturn(userData);
    }
    
    @State({"user-service-provider api should return status code 200 and account data when request is make with valid userId"})
    public void ShouldReturnAccountDataAndStatusCode200() throws Exception {
    	System.out.println("=>>>>>>>>>>>>>>>>>>>> Enter");
    	AccountResponseData accountData = new AccountResponseData();
		accountData.setAccountType("Current");
		accountData.setAccountNumber("123456789");
		when(userService.getUserAccounts()).thenReturn(accountData);
		
		
//		final String responseData = objectMapper.writeValueAsString(accountData);
//		onRequest()
//			.havingMethodEqualTo("GET")			
//			.havingPathEqualTo("/account-service/accounts")		
//			.havingHeaderEqualTo("Accept", "application/json")					
//		.respond()
//			.withStatus(200)
//			.withBody(responseData)
//			.withContentType("application/json; charset=UTF-8");
//		
//		AccountProxyService service = new AccountProxyService();
//		String url = "http://localhost:" + Jadler.port();
//		service.setAccountServiceUrl(url);		
//		System.out.println("=>>>>>>>>>>>>>>>>>>>> Exit"+ url);

    }
}
