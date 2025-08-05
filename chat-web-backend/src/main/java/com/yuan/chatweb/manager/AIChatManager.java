package com.yuan.chatweb.manager;

import com.alibaba.fastjson.JSONObject;
import com.yuan.chatweb.model.dto.LLMConfigDTO;
import com.yuan.chatweb.model.request.message.AIChatRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * AI对话
 *
 * @author BraumAce
 */
@Slf4j
@Service
public class AIChatManager {

    private OkHttpClient buildOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(180000, TimeUnit.MILLISECONDS)
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .build();
    }

    private Request buildSseRequest(AIChatRequest aiChatRequest, LLMConfigDTO llmConfigDTO) {
        return new Request.Builder()
                .url(llmConfigDTO.getApiUrl())
                .post(RequestBody.create(JSONObject.toJSONString(aiChatRequest), MediaType.parse("application/json; charset=utf-8")))
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Authorization", "Bearer " + llmConfigDTO.getApiKey())
                .addHeader("Accept", "text/event-stream")
                .build();
    }

    private Request buildRequest(AIChatRequest aiChatRequest, LLMConfigDTO llmConfigDTO) {
        return new Request.Builder()
                .url(llmConfigDTO.getApiUrl())
                .post(RequestBody.create(JSONObject.toJSONString(aiChatRequest), MediaType.parse("application/json; charset=utf-8")))
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Authorization", "Bearer " + llmConfigDTO.getApiKey())
                .build();
    }

    public void chatTextStream(AIChatRequest aiChatRequest, LLMConfigDTO llmConfigDTO, EventSourceListener eventSourceListener) {
        OkHttpClient okHttpClient = buildOkHttpClient();
        Request request = buildSseRequest(aiChatRequest, llmConfigDTO);

        EventSource.Factory factory = EventSources.createFactory(okHttpClient);
        EventSource eventSource = factory.newEventSource(request, eventSourceListener);
        eventSource.request();
    }

    public String chatText(AIChatRequest aiChatRequest, LLMConfigDTO llmConfigDTO) {
        OkHttpClient okHttpClient = buildOkHttpClient();

        Request request = buildRequest(aiChatRequest, llmConfigDTO);
        return getResponseContent(okHttpClient, request);
    }

    private String getResponseContent(OkHttpClient okHttpClient, Request request) {
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.warn("request: {}, response: {}", response.request().url(), response);
                throw new IOException("Unexpected code " + response);
            }
            if (response.body() == null) {
                log.warn("request: {}, response body is null, response code: {}", response.request().url(), response.code());
            }
            return response.body().string();
        } catch (Exception e) {
            log.error("request: {}, error: {}", request.url(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
