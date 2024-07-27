package com.blimp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MultipartProperties.class)
public class BeBlimpApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeBlimpApplication.class, args);
	}

}
