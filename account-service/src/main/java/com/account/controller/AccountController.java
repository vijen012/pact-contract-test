package com.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.account.data.AccountData;

@RestController
public class AccountController {	
	
	@GetMapping(path = "/account-service/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountData> getAccount() {
		AccountData accountData = new AccountData();
		accountData.setAccountType("Current");
		accountData.setAccountNumber("123456789");
		return new ResponseEntity<AccountData>(accountData, HttpStatus.OK);
	}
}
