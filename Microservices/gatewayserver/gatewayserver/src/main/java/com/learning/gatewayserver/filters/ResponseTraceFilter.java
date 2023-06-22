package com.learning.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import reactor.core.publisher.Mono;

@Configuration
//Spring boot application is aware of Configuration
public class ResponseTraceFilter {

	private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

	@Autowired
	FilterUtility filterUtility;

	@Bean
	// Post filter which will send correlationid to client in header
	public GlobalFilter postGlobalFilter() {
		return (exchange, chain) -> {
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				// Getting request header
				HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
				// Getting the correlationid from request header
				String correlationId = filterUtility.getCorrelationId(requestHeaders);
				// logging the correlationid
				logger.debug("Updated the correlation id to the outbound headers. {}", correlationId);
				// adding correlationid in the request header to be sent to client
				exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID, correlationId);
			}));
		};
	}
}