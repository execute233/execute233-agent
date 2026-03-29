package com.execute233.agent.ai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author execute233
 * @date 2026/3/28
 **/
@Service
@Slf4j
public class AiCodeHelper {

    @Autowired
    private ChatModel chatModel;

    public String chat(String message) {
        UserMessage userMessage = UserMessage.from(message);
        ChatResponse chat = chatModel.chat(userMessage);
        AiMessage aiMessage = chat.aiMessage();
        return aiMessage.text();
    }
}
