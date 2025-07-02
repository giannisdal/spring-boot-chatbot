package com.giannisdal.chatbot.enums;

import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;

import java.util.Locale;

@Getter
public enum OllamaSetup {
    MODEL(0, "ollama.model"),
    PROMPT(1, "ollama.prompt"),
    STREAM(2, "ollama.stream"),
    TEMPERATURE(3, "ollama.temperature"),
    NUM_PREDICT(4, "ollama.num_predict"),
    PRESENCE_PENALTY(5, "ollama.presence_penalty"),
    RESPONSE(6, "ollama.response"),
    CONTENT_TYPE(7, "ollama.contentType"),
    CONTENT_TYPE_VALUE(8, "ollama.contentTypeValue"),
    INITIAL_PROMPT(9, "ollama.initialPrompt"),
    STOP_SEQUENCE(10, "ollama.stopSequence");

    private static final OllamaSetup[] VALUES;

    private final Integer code;
    private final String descr;

    static {
        VALUES = values();
    }

    OllamaSetup(Integer code, String descr) {
        this.code = code;
        this.descr = descr;
    }

    @Nullable
    public static OllamaSetup resolve(int ollamaSetup) {
        for (OllamaSetup type : VALUES) {
            if (type.code == ollamaSetup) {
                return type;
            }
        }
        return null;
    }

    public String getMessage(MessageSource messageSource) {
        return messageSource.getMessage(descr, null, Locale.ENGLISH);
    }
}
