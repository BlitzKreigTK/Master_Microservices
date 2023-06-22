package com.learning.banking.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
@RefreshScope
@ComponentScans({ @ComponentScan("com.learning.banking.accounts.controller") })
@EnableJpaRepositories("com.learning.banking.accounts.repository")
@EntityScan("com.learning.banking.accounts.model")
@EnableFeignClients
public class AccountsApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

	@Bean
	// Timely registering the metrics comes from micrometer library
	public TimedAspect timeaspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}
}