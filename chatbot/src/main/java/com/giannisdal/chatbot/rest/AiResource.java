package com.giannisdal.chatbot.rest;

import com.giannisdal.chatbot.service.AiService;
import com.giannisdal.chatbot.service.dto.ChatbotDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing classification using spring-ai.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AiResource {

    private final Logger log = LoggerFactory.getLogger(AiResource.class);

    private final AiService aiService;

    @PostMapping("/chatbots")
    public ChatbotDto chat(@RequestBody ChatbotDto chatbot) {
        log.debug("Received chatbot request body: {}", chatbot.getRequest());
        return aiService.getResponse(chatbot);
    }

}
