package com.giannisdal.chatbot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupMessage {

    private static final Logger log = LoggerFactory.getLogger(StartupMessage.class);

    @Bean
    public ApplicationRunner applicationStartupMessage() {
        return args -> log.info("\n========================================\n   🚀 Spring Boot application started!   \n========================================\n");
    }
}
