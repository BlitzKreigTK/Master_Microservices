package com.learning.gatewayserver;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableEurekaClient requires for connecting with Eureka server but not used cloud >= 2022.0.0
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				// Intercept /bank/accounts/** calls
				.route(p -> p.path("/bank/accounts/**")
						// Filter route path and extract segment add response header with current time
						.filters(f -> f.rewritePath("/bank/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString()))
						// Redirect new url to Accounts microservice url
						.uri("lb://ACCOUNTS"))
				// Intercept /bank/accounts/** calls
				.route(p -> p.path("/bank/loans/**")
						// Filter route path and extract segment add response header with current time
						.filters(f -> f.rewritePath("/bank/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString()))
						// Redirect new url to Loans microservice url
						.uri("lb://LOANS"))
				// Intercept /bank/cards/** calls
				.route(p -> p.path("/bank/cards/**")
						// Filter route path and extract segment add response header with current time
						.filters(f -> f.rewritePath("/bank/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString()))
						// Redirect new url to Cards microservice url
						.uri("lb://CARDS"))
				.build();
	}
}
