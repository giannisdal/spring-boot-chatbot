package com.giannisdal.chatbot.service.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ChatbotDto implements Serializable {
    private String request;
    private String response;
}
