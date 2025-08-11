package com.yuan.chatweb.manager;

import com.alibaba.fastjson.JSONObject;
import com.yuan.chatweb.model.dto.LLMConfigDTO;
import com.yuan.chatweb.model.request.message.AIChatRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.yaml.snakeyaml.emitter.Emitter;

import java.io.IOException;
import java.util.Objects;
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

    public Object chatTextStream(AIChatRequest aiChatRequest, LLMConfigDTO llmConfigDTO) {
        OkHttpClient okHttpClient = buildOkHttpClient();
        Request request = buildSseRequest(aiChatRequest, llmConfigDTO);

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful() && response.body() != null) {
                return handleResponseEntity(response);
            }

            SseEmitter emitter = new SseEmitter(180000L);
            EventSource.Factory factory = EventSources.createFactory(okHttpClient);
            EventSource eventSource = factory.newEventSource(request, createEventSourceListener(emitter));
            eventSource.request();
            return emitter;
        } catch (IOException e) {
            log.error("request: {}, error: {}", request.url(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<byte[]> chatText(AIChatRequest aiChatRequest, LLMConfigDTO llmConfigDTO) {
        OkHttpClient okHttpClient = buildOkHttpClient();

        Request request = buildRequest(aiChatRequest, llmConfigDTO);
        return getResponseEntity(okHttpClient, request);
    }

    private EventSourceListener createEventSourceListener(SseEmitter emitter) {
        return new EventSourceListener() {
            @Override
            public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
                log.info("sse connection opened, url: {}, response code: {}", eventSource.request().url(), response.code());
            }

            @Override
            public void onEvent(@NotNull EventSource eventSource, String id, String type, String data) {
                try {
                    SseEmitter.SseEventBuilder build = SseEmitter.event();
                    if (Objects.nonNull(id)) {
                        build.id(id);
                    }
                    if (Objects.nonNull(type)) {
                        build.name(type);
                    }
                    if (Objects.nonNull(data)) {
                        build.data(data);
                    }
                    emitter.send(build);
                } catch (IOException e) {
                    log.error("chat sse emitter error: ", e);
                    emitter.completeWithError(e);
                }
            }

            @Override
            public void onFailure(@NotNull EventSource eventSource, Throwable t, Response response) {
                log.error("sse connection failure, url: {}, response code: {}", eventSource.request().url(), response.code(), t);
                log.error("sse connection failure, exception :", t);
                emitter.completeWithError(t);
            }

            @Override
            public void onClosed(@NotNull EventSource eventSource) {
                log.info("sse connection closed, url: {}", eventSource.request().url());
                emitter.complete();
            }
        };
    }

    private ResponseEntity<byte[]> getResponseEntity(OkHttpClient okHttpClient, Request request) {
        try (Response response = okHttpClient.newCall(request).execute()) {
            return handleResponseEntity(response);
        } catch (Exception e) {
            log.error("request: {}, error: {}", request.url(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<byte[]> handleResponseEntity(Response response) throws IOException {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        response.headers().forEach(h -> headers.add(h.getFirst(), h.getSecond()));
        byte[] body = response.body() == null ? new byte[0] : response.body().bytes();

        return ResponseEntity.status(response.code())
                .headers(headers)
                .body(body);
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
