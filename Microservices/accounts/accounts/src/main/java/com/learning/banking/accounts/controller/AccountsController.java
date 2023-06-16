package com.learning.banking.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learning.banking.accounts.config.AccountsServiceConfig;
import com.learning.banking.accounts.model.Accounts;
import com.learning.banking.accounts.model.Customer;
import com.learning.banking.accounts.repository.AccountsRepository;

@RestController
public class AccountsController {

	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	AccountsServiceConfig accountsConfig;

	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}
	}

	@GetMapping("/account/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		return accountsConfig.toString();
	}
}