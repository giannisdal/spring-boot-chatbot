package com.giannisdal.chatbot.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;
import java.util.function.Function;

import com.giannisdal.chatbot.enums.OllamaSetup;

@Configuration
public class AiConfiguration {

    private final Logger log = LoggerFactory.getLogger(AiConfiguration.class);
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${ollama.url}")
    private String ollamaUrl;

    @Value("${ollama.model}")
    private String ollamaModel;

    @Value("${ollama.temperature}")
    private double ollamaTemperature;

    @Value("${ollama.stream}")
    private boolean ollamaStream;

    @Value("${ollama.num_predict}")
    private int ollamaNumPredict;

    @Value("${ollama.presence_penalty}")
    private double ollamaPresencePenalty;

    private final MessageSource messageSource;

    public AiConfiguration(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public record UserQuestion(String question) {
    }

    public record AIResponse(String response) {
    }

    @Bean
    public Function<UserQuestion, AIResponse> chatWithLlama() {
        log.debug("Configuring Llama chatbot function");
        return this::processUserQuestion;
    }

    private AIResponse processUserQuestion(UserQuestion userQuestion) {
        try {
            // Build prompt
            String prompt = OllamaSetup.INITIAL_PROMPT.getMessage(messageSource)
                    + userQuestion.question()
                    + OllamaSetup.STOP_SEQUENCE.getMessage(messageSource);

            // Create request body
            String requestBody = objectMapper.writeValueAsString(Map.of(
                    OllamaSetup.MODEL.getMessage(messageSource), ollamaModel,
                    OllamaSetup.PROMPT.getMessage(messageSource), prompt,
                    OllamaSetup.STREAM.getMessage(messageSource), ollamaStream,
                    OllamaSetup.TEMPERATURE.getMessage(messageSource), ollamaTemperature,
                    OllamaSetup.NUM_PREDICT.getMessage(messageSource), ollamaNumPredict,
                    OllamaSetup.PRESENCE_PENALTY.getMessage(messageSource), ollamaPresencePenalty
            ));

            // Send HTTP call
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ollamaUrl))
                    .header(OllamaSetup.CONTENT_TYPE.getMessage(messageSource),
                            OllamaSetup.CONTENT_TYPE_VALUE.getMessage(messageSource))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> httpResp =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON into Map
            TypeReference<Map<String, Object>> tr = new TypeReference<>() {
            };
            Map<String, Object> llamaResponse = objectMapper.readValue(httpResp.body(), tr);

            // Extract the text whether it's /api/generate or /api/chat
            String responseText = extractAnswer(llamaResponse);

            return new AIResponse(responseText);

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Processing was interrupted. Please try again later.", ie);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while processing the AI response.", e);
        }
    }

    @SuppressWarnings("unchecked")
    private String extractAnswer(Map<String, Object> resp) {
        // 1) If /api/generate → top-level "response"
        String key = OllamaSetup.RESPONSE.getMessage(messageSource);
        if (resp.containsKey(key) && resp.get(key) != null) {
            return resp.get(key).toString();
        }
        // 2) Otherwise assume /api/chat → nested message.content
        Map<String, Object> msg = (Map<String, Object>) resp.get("message");
        return msg.get("content").toString();
    }
}
