package com.yuan.chatweb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.chatweb.enums.exception.LLMErrorCode;
import com.yuan.chatweb.enums.SenderType;
import com.yuan.chatweb.manager.AIChatManager;
import com.yuan.chatweb.mapper.MessageMapper;
import com.yuan.chatweb.model.dto.LLMConfigDTO;
import com.yuan.chatweb.model.entity.ChatDO;
import com.yuan.chatweb.model.entity.LLMConfigDO;
import com.yuan.chatweb.model.entity.MessageDO;
import com.yuan.chatweb.model.request.message.AIChatMessage;
import com.yuan.chatweb.model.request.message.AIChatRequest;
import com.yuan.chatweb.model.request.message.MessageCreateRequest;
import com.yuan.chatweb.model.vo.LLMConfigVO;
import com.yuan.chatweb.model.vo.MessageVO;
import com.yuan.chatweb.service.ChatService;
import com.yuan.chatweb.service.LLMConfigService;
import com.yuan.chatweb.service.MessageService;
import com.yuan.chatweb.utils.ThrowUtil;
import com.yuan.chatweb.utils.convert.ChatConverter;
import com.yuan.chatweb.utils.convert.LLMConfigConverter;
import okhttp3.sse.EventSourceListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息服务实现类
 *
 * @author BraumAce
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageDO> implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private ChatService chatService;

    @Resource
    private LLMConfigService llmConfigService;

    @Resource
    private AIChatManager aiChatManager;

    @Override
    public SseEmitter chatTextStream(Long userId, MessageCreateRequest request) {
        // 检查对话是否存在且属于当前用户
        Long chatId = request.getChatId();
        ChatDO chatDO = chatService.getChatById(chatId);
        ThrowUtil.throwIf(chatDO == null, LLMErrorCode.CONFIG_NOT_FOUND, "对话不存在");

        // 保存用户消息
        MessageDO userMessage = saveUserMessage(chatId, request.getContent());

        // 获取模型配置
        LLMConfigDTO llmConfigDTO = getLLMConfig(request.getModelConfigId(), userId);

        // 构造AI对话请求
        AIChatRequest aiChatRequest = buildAIChatRequest(request, chatId, llmConfigDTO);

        // 创建SSE emitter用于流式输出
        SseEmitter emitter = new SseEmitter(0L);
        
        // 流式调用AI接口
        aiChatManager.chatTextStream(aiChatRequest, llmConfigDTO, new EventSourceListener() {
            private final StringBuilder contentBuilder = new StringBuilder();
            
            // 保存AI消息占位符
            private MessageDO aiMessage;

            @Override
            public void onOpen(okhttp3.sse.EventSource eventSource, okhttp3.Response response) {
                try {
                    // 创建AI消息占位符
                    aiMessage = new MessageDO();
                    aiMessage.setChatId(chatId);
                    aiMessage.setSenderType(SenderType.ASSISTANT.getCode());
                    aiMessage.setContent("");
                    messageMapper.insert(aiMessage);
                    
                    emitter.send(SseEmitter.event().name("open").data("连接已建立"));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }

            @Override
            public void onEvent(okhttp3.sse.EventSource eventSource, String id, String type, String data) {
                try {
                    if ("[DONE]".equals(data)) {
                        // 更新AI消息内容
                        aiMessage.setContent(contentBuilder.toString());
                        messageMapper.updateById(aiMessage);
                        
                        emitter.send(SseEmitter.event().name("done").data("[DONE]"));
                        emitter.complete();
                    } else {
                        // 解析数据
                        JSONObject jsonObject = JSONObject.parseObject(data);
                        if (jsonObject.containsKey("choices") && jsonObject.getJSONArray("choices").size() > 0) {
                            JSONObject choice = jsonObject.getJSONArray("choices").getJSONObject(0);
                            if (choice.containsKey("delta")) {
                                JSONObject delta = choice.getJSONObject("delta");
                                String content = delta.getString("content");
                                
                                if (content != null) {
                                    contentBuilder.append(content);
                                    emitter.send(SseEmitter.event().name("message").data(content));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    emitter.completeWithError(e);
                }
            }

            @Override
            public void onFailure(okhttp3.sse.EventSource eventSource, Throwable t, okhttp3.Response response) {
                emitter.completeWithError(t);
            }

            @Override
            public void onClosed(okhttp3.sse.EventSource eventSource) {
                emitter.complete();
            }
        });
        
        return emitter;
    }

    @Override
    public SseEmitter chatText(Long userId, MessageCreateRequest request) {
        // 检查对话是否存在且属于当前用户
        Long chatId = request.getChatId();
        ChatDO chatDO = chatService.getChatById(chatId);
        ThrowUtil.throwIf(chatDO == null, LLMErrorCode.CONFIG_NOT_FOUND, "对话不存在");

        // 保存用户消息
        MessageDO userMessage = saveUserMessage(chatId, request.getContent());

        // 获取模型配置
        LLMConfigDTO llmConfigDTO = getLLMConfig(request.getModelConfigId(), userId);

        // 构造AI对话请求
        AIChatRequest aiChatRequest = buildAIChatRequest(request, chatId, llmConfigDTO);

        // 创建SSE emitter用于非流式输出
        SseEmitter emitter = new SseEmitter(0L);

        // 异步调用AI接口获取回复
        new Thread(() -> {
            try {
                String aiResponse = aiChatManager.chatText(aiChatRequest, llmConfigDTO);

                // 解析AI回复内容
                JSONObject jsonObject = JSONObject.parseObject(aiResponse);
                String content = jsonObject.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");

                // 保存AI消息
                MessageDO aiMessage = saveAIMessage(chatId, content);

                // 发送完整响应给前端
                emitter.send(SseEmitter.event().name("result").data(content));
                emitter.complete();
            } catch (Exception e) {
                try {
                    emitter.send(SseEmitter.event().name("error").data(e.getMessage()));
                } catch (IOException ioException) {
                    // 忽略
                }
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }

    @Override
    public MessageVO sendMessage(Long userId, MessageCreateRequest request) {
        // 检查对话是否存在且属于当前用户
        Long chatId = request.getChatId();
        ThrowUtil.throwIf(chatService.getChatById(chatId) == null, LLMErrorCode.CONFIG_NOT_FOUND, "对话不存在");

        // 保存用户消息
        MessageDO userMessage = saveUserMessage(chatId, request.getContent());

        // 获取模型配置
        LLMConfigDTO llmConfigDTO = getLLMConfig(request.getModelConfigId(), userId);

        // 构造AI对话请求
        AIChatRequest aiChatRequest = buildAIChatRequest(request, chatId, llmConfigDTO);

        // 调用AI接口获取回复
        String aiResponse = aiChatManager.chatText(aiChatRequest, llmConfigDTO);

        // 解析AI回复内容
        JSONObject jsonObject = JSONObject.parseObject(aiResponse);
        String content = jsonObject.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        // 保存AI消息
        MessageDO aiMessage = saveAIMessage(chatId, content);

        // 转换为VO并返回
        return ChatConverter.INSTANCE.toMessageVO(aiMessage);
    }

    @Override
    public List<MessageVO> listMessagesByChatId(Long chatId) {
        // 根据对话ID查询消息列表
        LambdaQueryWrapper<MessageDO> queryWrapper = new LambdaQueryWrapper<MessageDO>()
                .eq(MessageDO::getChatId, chatId);
        List<MessageDO> messageList = messageMapper.selectList(queryWrapper);

        // 转换为VO列表并返回
        return messageList.stream()
                .map(ChatConverter.INSTANCE::toMessageVO)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDO saveUserMessage(Long chatId, String content) {
        MessageDO messageDO = new MessageDO();
        messageDO.setChatId(chatId);
        messageDO.setSenderType(SenderType.USER.getCode());
        messageDO.setContent(content);
        messageMapper.insert(messageDO);
        return messageDO;
    }

    @Override
    public MessageDO saveAIMessage(Long chatId, String content) {
        MessageDO messageDO = new MessageDO();
        messageDO.setChatId(chatId);
        messageDO.setSenderType(SenderType.ASSISTANT.getCode());
        messageDO.setContent(content);
        messageMapper.insert(messageDO);
        return messageDO;
    }
    
    /**
     * 获取模型配置
     * @param modelConfigId 模型配置ID
     * @param userId 用户ID
     * @return 模型配置DTO
     */
    private LLMConfigDTO getLLMConfig(Long modelConfigId, Long userId) {
        // 获取模型配置
        LLMConfigDTO llmConfigDTO;
        if (modelConfigId != null) {
            // 使用指定的模型配置
            llmConfigDTO = LLMConfigConverter.INSTANCE.toLLMConfigDTO(llmConfigService.getConfigById(modelConfigId));
        } else {
            // 使用用户当前的模型配置
            llmConfigDTO = LLMConfigConverter.INSTANCE.toLLMConfigDTO(llmConfigService.getCurrentConfig(userId));
        }
        return llmConfigDTO;
    }

    /**
     * 构造AI对话请求
     * @param request 消息创建请求
     * @param chatId 对话ID
     * @param llmConfigDTO 模型配置
     * @return AI对话请求
     */
    private AIChatRequest buildAIChatRequest(MessageCreateRequest request, Long chatId, LLMConfigDTO llmConfigDTO) {
        // 获取历史消息
        List<MessageVO> historyMessages = listMessagesByChatId(chatId);
        
        // 构造消息列表
        List<AIChatMessage> aiChatMessages = new ArrayList<>();
        for (MessageVO historyMessage : historyMessages) {
            AIChatMessage aiChatMessage = new AIChatMessage();
            // 根据发送者类型设置角色
            if (SenderType.USER.getCode().equals(historyMessage.getSenderType())) {
                aiChatMessage.setRole("user");
            } else if (SenderType.ASSISTANT.getCode().equals(historyMessage.getSenderType())) {
                aiChatMessage.setRole("assistant");
            } else {
                aiChatMessage.setRole("system");
            }
            aiChatMessage.setContent(historyMessage.getContent());
            aiChatMessages.add(aiChatMessage);
        }
        
        // 添加当前用户消息
        AIChatMessage currentMessage = new AIChatMessage();
        currentMessage.setRole(request.getRole());
        currentMessage.setContent(request.getContent());
        aiChatMessages.add(currentMessage);

        // 构造AI对话请求
        AIChatRequest aiChatRequest = new AIChatRequest();
        aiChatRequest.setModel(llmConfigDTO.getModelId());
        aiChatRequest.setMessages(aiChatMessages);
        aiChatRequest.setStream(request.getStream());
        return aiChatRequest;
    }
}