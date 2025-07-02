package com.giannisdal.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}

	@Bean
	public ApplicationRunner showFinalStartupMessage() {
		return args -> {
			Logger log = LoggerFactory.getLogger(ChatbotApplication.class);
			log.info("\n========================================\n   🚀 Spring Boot application started!   \n========================================\n");
		};
	}
}
