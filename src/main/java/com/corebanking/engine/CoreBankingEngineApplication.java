package com.corebanking.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.corebanking.engine")
public class CoreBankingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreBankingEngineApplication.class, args);
	}

}
