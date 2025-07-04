package com.giannisdal.chatbot.service.impl;

import com.giannisdal.chatbot.config.AiConfiguration.AIResponse;
import com.giannisdal.chatbot.config.AiConfiguration.UserQuestion;
import com.giannisdal.chatbot.service.dto.ChatbotDto;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class AiServiceImplTest {

    @Test
    void getResponse_shouldSetResponseFromLlama() {
        // Arrange
        Function<UserQuestion, AIResponse> mockLlama = uq -> new AIResponse("mocked response");
        AiServiceImpl service = new AiServiceImpl(mockLlama);
        ChatbotDto input = new ChatbotDto();
        input.setRequest("Hello");

        // Act
        ChatbotDto result = service.getResponse(input);

        // Assert
        assertEquals("mocked response", result.getResponse());
        assertEquals("Hello", result.getRequest());
    }
}

