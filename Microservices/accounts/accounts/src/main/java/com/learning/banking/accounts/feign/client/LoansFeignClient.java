package com.learning.banking.accounts.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.learning.banking.accounts.model.Customer;
import com.learning.banking.accounts.model.Loans;

//Feign client to call other microservices endpts @FeignClient("<app_Name>")
@FeignClient("loans")
public interface LoansFeignClient {
	// Endpoint to call in loans micro service with passing Customer details in body
	// and passing correlationid from request header
	@RequestMapping(method = RequestMethod.POST, value = "myLoans", consumes = "application/json")
	List<Loans> getLoansDetails(@RequestHeader("bank-correlation-id") String correlationid,
			@RequestBody Customer customer);
}