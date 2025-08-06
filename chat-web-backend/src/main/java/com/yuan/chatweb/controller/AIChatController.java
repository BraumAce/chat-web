package com.yuan.chatweb.controller;

import com.yuan.chatweb.common.Result;
import com.yuan.chatweb.manager.AIChatManager;
import com.yuan.chatweb.model.dto.LLMConfigDTO;
import com.yuan.chatweb.model.request.chat.ChatCreateRequest;
import com.yuan.chatweb.model.request.message.AIChatRequest;
import com.yuan.chatweb.model.request.message.MessageCreateRequest;
import com.yuan.chatweb.model.vo.ChatVO;
import com.yuan.chatweb.model.vo.MessageVO;
import com.yuan.chatweb.service.ChatService;
import com.yuan.chatweb.service.LLMConfigService;
import com.yuan.chatweb.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

/**
 * AI对话控制器
 *
 * @author BraumAce
 */
@RestController
@RequestMapping("/ai/chat")
@Slf4j
public class AIChatController {

    @Resource
    private ChatService chatService;

    @Resource
    private MessageService messageService;

    @Resource
    private LLMConfigService llmConfigService;

    @Resource
    private AIChatManager aiChatManager;

    @PostMapping
    @ApiOperation("创建对话")
    public Result<ChatVO> createChat(@RequestParam Long userId,
                                     @Validated @RequestBody ChatCreateRequest request) {
        ChatVO chatVO = chatService.createChat(userId, request);
        return Result.success(chatVO);
    }

    @GetMapping("/list")
    @ApiOperation("获取对话列表")
    public Result<List<ChatVO>> listChats(@RequestParam Long userId) {
        List<ChatVO> chatList = chatService.listChatsByUserId(userId);
        return Result.success(chatList);
    }

    @DeleteMapping("/{chatId}")
    @ApiOperation("删除对话")
    public Result<Boolean> deleteChat(@RequestParam Long userId,
                                      @PathVariable Long chatId) {
        Boolean result = chatService.deleteChat(userId, chatId);
        return Result.success(result);
    }

    @GetMapping("/messages")
    @ApiOperation("获取对话消息列表")
    public Result<List<MessageVO>> listMessages(@RequestParam Long chatId) {
        List<MessageVO> messageList = messageService.listMessagesByChatId(chatId);
        return Result.success(messageList);
    }

    @PostMapping("/message")
    @ApiOperation("保存对话消息")
    public Result<MessageVO> saveMessage(@RequestParam Long userId,
                                         @Validated @RequestBody MessageCreateRequest request) {
        MessageVO messageVO = messageService.saveMessage(userId, request);
        return Result.success(messageVO);
    }

    @PostMapping("/completions")
    @ApiOperation("AI对话")
    public SseEmitter sendMessage(@RequestParam Long userId,
                                  @RequestBody AIChatRequest aiChatRequest,
                                  HttpServletResponse response) {
        LLMConfigDTO llmConfigDTO = llmConfigService.getCurrentConfig(userId);
        if (aiChatRequest.getStream().equals(Boolean.TRUE)) {
            // 流式返回
            response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
            SseEmitter emitter = new SseEmitter(180000L);
            aiChatManager.chatTextStream(aiChatRequest, llmConfigDTO, createEventSourceListener(emitter));
            return emitter;
        } else {
            // 非流式返回
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            try (PrintWriter writer = response.getWriter()) {
                writer.write(aiChatManager.chatText(aiChatRequest, llmConfigDTO));
                writer.flush();
            } catch (IOException e) {
                log.error("write text error", e);
            }
            return null;
        }
    }

    /**
     * 创建EventSourceListener实例用于处理SSE事件
     *
     * @param emitter SseEmitter实例
     * @return EventSourceListener实例
     */
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
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NotNull EventSource eventSource, Throwable t, Response response) {
                try {
                    log.error("sse connection failure, url: {}, response code: {}", eventSource.request().url(), response.code(), t);
                    SseEmitter.SseEventBuilder errorEvent = SseEmitter.event().name("error");
                    if (response.body() != null) {
                        errorEvent.data(response.body().string());
                    }
                    emitter.send(errorEvent);
                } catch (IOException e) {
                    log.error("chat sse emitter error: ", e);
                    throw new RuntimeException(e);
                } finally {
                    emitter.complete();
                }
            }

            @Override
            public void onClosed(@NotNull EventSource eventSource) {
                log.info("sse connection closed, url: {}", eventSource.request().url());
                emitter.complete();
            }
        };
    }
}