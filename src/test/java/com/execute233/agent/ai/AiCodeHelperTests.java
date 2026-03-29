package com.execute233.agent.ai;

import com.execute233.agent.AgentApplication;
import dev.langchain4j.service.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author execute233
 * @date 2026/3/29
 **/
@Slf4j
@SpringBootTest(classes = AgentApplication.class)
public class AiCodeHelperTests {

    @Resource
    private AiCodeHelperService aiCodeHelperService;
    @Test
    public void testAiCodeHelper() {
        Result<String> chat = aiCodeHelperService.chat("hello");
        log.info(chat.content());
    }

}
