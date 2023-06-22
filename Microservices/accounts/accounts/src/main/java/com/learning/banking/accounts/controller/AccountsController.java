package com.learning.banking.accounts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learning.banking.accounts.config.AccountsServiceConfig;
import com.learning.banking.accounts.feign.client.CardsFeignClient;
import com.learning.banking.accounts.feign.client.LoansFeignClient;
import com.learning.banking.accounts.model.Accounts;
import com.learning.banking.accounts.model.Cards;
import com.learning.banking.accounts.model.Customer;
import com.learning.banking.accounts.model.CustomerDetails;
import com.learning.banking.accounts.model.Loans;
import com.learning.banking.accounts.repository.AccountsRepository;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;

@RestController
public class AccountsController {
	private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	AccountsServiceConfig accountsConfig;

	@Autowired
	LoansFeignClient loansFeignClient;

	@Autowired
	CardsFeignClient cardsFeignClient;

	@PostMapping("/myAccount")
	@Timed(value = "getAccountDetails.time", description = "Time taken to return Account Details")
	// Custom micrometer metrics "getAccountDetails.time" will be registered to get
	// time for getAccountDetailsAPI response
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		logger.info("getAccountDetails() method started");
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}
	}

	@GetMapping("/account/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		logger.info("getPropertyDetails() method started");
		return accountsConfig.toString();
	}

	@PostMapping("/myCustomerDetails")
	// Circuit breaker
	// @CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod =
	// "myCustomerDetailsFallBack")
	// Retry
	@Retry(name = "retryForCustomerDetails", fallbackMethod = "myCustomerDetailsFallBack")
	public CustomerDetails myCustomerDetails(@RequestHeader("bank-correlation-id") String correlationid,
			@RequestBody Customer customer) {
		logger.info("myCustomerDetails() method started");
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		// passing correlationid for tracing request to loans and cards
		List<Loans> loans = loansFeignClient.getLoansDetails(correlationid, customer);
		List<Cards> cards = cardsFeignClient.getCardDetails(correlationid, customer);
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setLoans(loans);
		customerDetails.setCards(cards);
		return customerDetails;
	}

	@SuppressWarnings("unused")
	private CustomerDetails myCustomerDetailsFallBack(@RequestHeader("bank-correlation-id") String correlationid,
			Customer customer, Throwable t) {
		logger.info("myCustomerDetailsFallBack() method started");
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		// passing correlationid for tracing request to loans
		List<Loans> loans = loansFeignClient.getLoansDetails(correlationid, customer);
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setLoans(loans);
		return customerDetails;
	}

	@GetMapping("/sayHello")
	@RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
	public String sayHello() {
		logger.info("sayHello() method started");
		return "Hello, Welcome to Bank";
	}

	@SuppressWarnings("unused")
	private String sayHelloFallback(Throwable t) {
		logger.info("sayHelloFallback() method started");
		return "Hi, Welcome to failback Bank";
	}
}