package com.ticket.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import com.ticket.crud.configuration.JpaConfiguration;


@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages={"com.ticket.crud"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootCRUDApp  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application");
		SpringApplication.run(SpringBootCRUDApp.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootCRUDApp.class);
	}
}
