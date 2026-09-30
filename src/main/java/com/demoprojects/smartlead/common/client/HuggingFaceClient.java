package com.demoprojects.smartlead.common.client;

import com.demoprojects.smartlead.ai.dto.ChatRequest;
import com.demoprojects.smartlead.ai.dto.ChatResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface HuggingFaceClient {

    @PostExchange(("/v1/chat/completions"))
    ChatResponse completion(@RequestBody ChatRequest request);
}