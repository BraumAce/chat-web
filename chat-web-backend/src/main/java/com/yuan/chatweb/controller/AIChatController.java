package com.yuan.chatweb.controller;

import com.yuan.chatweb.common.Result;
import com.yuan.chatweb.model.request.llm.ChatMessageRequest;
import com.yuan.chatweb.model.request.llm.ChatRequest;
import com.yuan.chatweb.model.vo.ChatVO;
import com.yuan.chatweb.model.vo.MessageVO;
import com.yuan.chatweb.service.ChatService;
import com.yuan.chatweb.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * AI对话控制器
 *
 * @author BraumAce
 */
@RestController
@RequestMapping("/ai/chat")
@Api(tags = "AI对话接口")
public class AIChatController {

    @Resource
    private ChatService chatService;

    @Resource
    private MessageService messageService;

    @PostMapping
    @ApiOperation("创建对话")
    public Result<ChatVO> createChat(
            @ApiParam("用户ID") @RequestParam Long userId,
            @Valid @RequestBody ChatRequest request) {
        ChatVO chatVO = chatService.createChat(userId, request);
        return Result.success(chatVO);
    }

    @GetMapping("/list")
    @ApiOperation("获取对话列表")
    public Result<List<ChatVO>> listChats(@ApiParam("用户ID") @RequestParam Long userId) {
        List<ChatVO> chatList = chatService.listChatsByUserId(userId);
        return Result.success(chatList);
    }

    @DeleteMapping("/{chatId}")
    @ApiOperation("删除对话")
    public Result<Boolean> deleteChat(
            @ApiParam("用户ID") @RequestParam Long userId,
            @ApiParam("对话ID") @PathVariable Long chatId) {
        Boolean result = chatService.deleteChat(userId, chatId);
        return Result.success(result);
    }

    @PostMapping("/completions")
    @ApiOperation("发送消息")
    public Result<MessageVO> sendMessage(
            @ApiParam("用户ID") @RequestParam Long userId,
            @Valid @RequestBody ChatMessageRequest request) {
        MessageVO messageVO = messageService.sendMessage(userId, request);
        return Result.success(messageVO);
    }

    @GetMapping("/completions/stream")
    @ApiOperation("流式发送消息")
    public SseEmitter streamMessage(
            @ApiParam("用户ID") @RequestParam Long userId,
            @ApiParam("对话ID") @RequestParam Long chatId,
            @ApiParam("消息内容") @RequestParam String content) {
        ChatMessageRequest request = new ChatMessageRequest();
        request.setChatId(chatId);
        request.setContent(content);
        request.setStream(true);
        return messageService.streamMessage(userId, request);
    }

    @GetMapping("/messages")
    @ApiOperation("获取对话消息列表")
    public Result<List<MessageVO>> listMessages(@ApiParam("对话ID") @RequestParam Long chatId) {
        List<MessageVO> messageList = messageService.listMessagesByChatId(chatId);
        return Result.success(messageList);
    }
}