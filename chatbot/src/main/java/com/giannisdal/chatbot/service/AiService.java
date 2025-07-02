package com.giannisdal.chatbot.service;

import com.giannisdal.chatbot.service.dto.ChatbotDto;

public interface AiService {

    ChatbotDto getResponse(ChatbotDto chat);

}
