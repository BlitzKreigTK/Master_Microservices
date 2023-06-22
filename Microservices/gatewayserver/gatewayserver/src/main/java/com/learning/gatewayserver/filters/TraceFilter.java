package com.learning.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Order(1)
//Order for filtering is defined using @Order in case of many filters
@Component
//Component need to be managed via spring
//Filter needs to implement Global Filter from spring library
public class TraceFilter implements GlobalFilter {

	private static final Logger logger = LoggerFactory.getLogger(TraceFilter.class);

	@Autowired
	FilterUtility filterUtility;

	@Override
	// Overriding filter method
	// GatewayFilterChain chain is passed as we may have chain of filters
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// Getting the request header from exchange
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		// If CorrelationId is present, just logging the details
		if (isCorrelationIdPresent(requestHeaders)) {
			logger.debug("Bank-correlation-id found in tracing filter: {}. ",
					filterUtility.getCorrelationId(requestHeaders));
		} else {
			// If CorrelationId is not present, generating a random alphanumeric ID
			String correlationID = generateCorrelationId();
			// Setting a new request header with generated CorrelationId
			exchange = filterUtility.setCorrelationId(exchange, correlationID);
			logger.debug("Bank-correlation-id generated in tracing filter: {}.", correlationID);
		}
		return chain.filter(exchange);
	}

	private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
		if (filterUtility.getCorrelationId(requestHeaders) != null) {
			return true;
		} else {
			return false;
		}
	}

	// Generating a random alphanumeric ID using Java util package
	private String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}

}