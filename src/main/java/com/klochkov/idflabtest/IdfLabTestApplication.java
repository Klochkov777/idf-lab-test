package com.klochkov.idflabtest;

import com.klochkov.idflabtest.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
public class IdfLabTestApplication {
	/**
	 * @param args - args
	 */
	public static void main(String[] args) {
		SpringApplication.run(IdfLabTestApplication.class, args);
	}
}
