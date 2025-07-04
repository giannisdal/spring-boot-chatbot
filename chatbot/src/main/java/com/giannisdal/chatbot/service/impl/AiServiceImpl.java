package com.giannisdal.chatbot.service.impl;

import com.giannisdal.chatbot.config.AiConfiguration.UserQuestion;
import com.giannisdal.chatbot.config.AiConfiguration.AIResponse;
import com.giannisdal.chatbot.service.AiService;
import com.giannisdal.chatbot.service.dto.ChatbotDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AiServiceImpl implements AiService {

    public static final String INITIAL_VALUE = "";
    private final Logger log = LoggerFactory.getLogger(AiServiceImpl.class);

    private final Function<UserQuestion, AIResponse> llama;

    public AiServiceImpl(Function<UserQuestion, AIResponse> llm) {
        this.llama = llm;
    }

    /**
     * Processes the chatbot request and generates a response using the Llama model.
     *
     * @param chatbot The input DTO containing the user's request.
     * @return ChatbotDto with the generated response.
     */
    @Override
    public ChatbotDto getResponse(ChatbotDto chatbot) {
        log.debug("Received chatbot request");

        UserQuestion userQuestion = new UserQuestion(chatbot.getRequest());
        log.debug("Constructed user question");

        String response = INITIAL_VALUE;
        try {
            response = llama.apply(userQuestion).response();
            log.debug("Raw Ollama response: {}", response); // Add this line!
            chatbot.setResponse(response);
        } catch (Exception e) {
            log.error("Error during Ollama call or response parsing: {}", response, e);
            chatbot.setResponse("Sorry, an error occurred while processing your request.");
        }
        return chatbot;
    }

}
