package com.learning.banking.accounts.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.learning.banking.accounts.model.Cards;
import com.learning.banking.accounts.model.Customer;

//Feign client to call other microservices endpts @FeignClient("<app_Name>")
@FeignClient("cards")
public interface CardsFeignClient {
	// Endpoint to call in cards micro service with passing Customer details in body
	// and passing correlationid from request header
	@RequestMapping(method = RequestMethod.POST, value = "myCards", consumes = "application/json")
	List<Cards> getCardDetails(@RequestHeader("bank-correlation-id") String correlationid,
			@RequestBody Customer customer);
}
