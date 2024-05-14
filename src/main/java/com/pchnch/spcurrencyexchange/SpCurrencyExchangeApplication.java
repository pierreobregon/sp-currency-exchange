package com.pchnch.spcurrencyexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

@SpringBootApplication(exclude = {ReactiveSecurityAutoConfiguration.class })
public class SpCurrencyExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpCurrencyExchangeApplication.class, args);
	}

}
