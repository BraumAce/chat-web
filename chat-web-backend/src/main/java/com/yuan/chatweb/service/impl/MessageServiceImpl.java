package com.yuan.chatweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.chatweb.convert.LLMConfigConverter;
import com.yuan.chatweb.enums.LLMErrorCode;
import com.yuan.chatweb.mapper.MessageMapper;
import com.yuan.chatweb.model.entity.MessageDO;
import com.yuan.chatweb.model.request.llm.ChatMessageRequest;
import com.yuan.chatweb.model.vo.MessageVO;
import com.yuan.chatweb.service.ChatService;
import com.yuan.chatweb.service.MessageService;
import com.yuan.chatweb.utils.ThrowUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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

    @Override
    public MessageVO sendMessage(Long userId, ChatMessageRequest request) {
        // 检查对话是否存在且属于当前用户
        Long chatId = request.getChatId();
        ThrowUtil.throwIf(chatService.getChatById(chatId) == null, LLMErrorCode.CONFIG_NOT_FOUND, "对话不存在");

        // 保存用户消息
        MessageDO userMessage = saveUserMessage(chatId, request.getContent());

        // TODO: 调用大模型API获取回复，这里暂时返回固定内容
        String aiResponse = "这是AI的回复内容";

        // 保存AI消息
        MessageDO aiMessage = saveAIMessage(chatId, aiResponse);

        // 转换为VO并返回
        return LLMConfigConverter.INSTANCE.toMessageVO(aiMessage);
    }

    @Override
    public SseEmitter streamMessage(Long userId, ChatMessageRequest request) {
        // 检查对话是否存在且属于当前用户
        Long chatId = request.getChatId();
        ThrowUtil.throwIf(chatService.getChatById(chatId) == null, LLMErrorCode.CONFIG_NOT_FOUND, "对话不存在");

        // 保存用户消息
        saveUserMessage(chatId, request.getContent());

        // 创建SSE连接
        SseEmitter emitter = new SseEmitter(0L);

        // 异步处理流式响应
        CompletableFuture.runAsync(() -> {
            try {
                // 模拟流式输出
                String aiResponse = "这是AI的流式回复内容";
                for (int i = 0; i < aiResponse.length(); i++) {
                    char c = aiResponse.charAt(i);
                    emitter.send(SseEmitter.event().data(String.valueOf(c)));
                }
                
                // 保存AI消息
                saveAIMessage(chatId, aiResponse);
                
                // 结束连接
                emitter.complete();
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    @Override
    public List<MessageVO> listMessagesByChatId(Long chatId) {
        // 根据对话ID查询消息列表
        QueryWrapper<MessageDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conversation_id", chatId);
        List<MessageDO> messageList = messageMapper.selectList(queryWrapper);

        // 转换为VO列表并返回
        return messageList.stream()
                .map(LLMConfigConverter.INSTANCE::toMessageVO)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDO saveUserMessage(Long chatId, String content) {
        MessageDO messageDO = new MessageDO();
        messageDO.setConversationId(chatId);
        messageDO.setSenderType(1); // 用户消息
        messageDO.setContent(content);
        messageMapper.insert(messageDO);
        return messageDO;
    }

    @Override
    public MessageDO saveAIMessage(Long chatId, String content) {
        MessageDO messageDO = new MessageDO();
        messageDO.setConversationId(chatId);
        messageDO.setSenderType(2); // AI消息
        messageDO.setContent(content);
        messageMapper.insert(messageDO);
        return messageDO;
    }
}