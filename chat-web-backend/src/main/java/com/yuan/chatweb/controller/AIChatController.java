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
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public Object sendMessage(@RequestParam Long userId,
                              @RequestBody AIChatRequest aiChatRequest,
                              HttpServletResponse response) {
        LLMConfigDTO llmConfigDTO = llmConfigService.getCurrentConfig(userId);
        if (aiChatRequest.getStream().equals(Boolean.TRUE)) {
            response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
            return aiChatManager.chatTextStream(aiChatRequest, llmConfigDTO);
        } else {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            return aiChatManager.chatText(aiChatRequest, llmConfigDTO);
        }
    }


}