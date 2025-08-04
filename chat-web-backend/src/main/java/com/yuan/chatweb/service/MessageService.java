package com.yuan.chatweb.service;

import com.yuan.chatweb.model.entity.MessageDO;
import com.yuan.chatweb.model.request.message.MessageCreateRequest;
import com.yuan.chatweb.model.vo.MessageVO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * 消息服务接口
 *
 * @author BraumAce
 */
public interface MessageService {

    /**
     * 流式发送消息
     *
     * @param userId  用户ID
     * @param request 请求参数
     * @return SseEmitter对象
     */
    SseEmitter chatTextStream(Long userId, MessageCreateRequest request);

    /**
     * 非流式发送消息
     *
     * @param userId  用户ID
     * @param request 请求参数
     * @return SseEmitter对象
     */
    SseEmitter chatText(Long userId, MessageCreateRequest request);

    /**
     * 发送消息
     *
     * @param userId  用户ID
     * @param request 请求参数
     * @return 消息信息
     */
    MessageVO sendMessage(Long userId, MessageCreateRequest request);

    /**
     * 获取对话消息列表
     *
     * @param chatId 对话ID
     * @return 消息列表
     */
    List<MessageVO> listMessagesByChatId(Long chatId);

    /**
     * 保存用户消息
     *
     * @param chatId  对话ID
     * @param content 消息内容
     * @return 消息信息
     */
    MessageDO saveUserMessage(Long chatId, String content);

    /**
     * 保存AI消息
     *
     * @param chatId  对话ID
     * @param content 消息内容
     * @return 消息信息
     */
    MessageDO saveAIMessage(Long chatId, String content);
}