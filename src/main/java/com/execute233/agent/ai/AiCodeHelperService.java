package com.execute233.agent.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import java.util.List;

/**
 *
 * @author execute233
 * @date 2026/3/29
 **/
public interface AiCodeHelperService {
    // 支持本地文件读取，resource下也可以
    // 也可以直接写系统提示词
    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> chat(String userMessage);
}
