package com.corebanking.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
@SpringBootApplication(scanBasePackages = "com.corebanking.engine")
@EnableWebSecurity
public class CoreBankingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreBankingEngineApplication.class, args);
	}

}
