package com.yuan.chatweb.service;

import com.yuan.chatweb.model.request.message.MessageCreateRequest;
import com.yuan.chatweb.model.vo.MessageVO;

import java.util.List;

/**
 * 消息服务接口
 *
 * @author BraumAce
 */
public interface MessageService {
    /**
     * 保存消息
     *
     * @param userId  用户ID
     * @param request 请求参数
     * @return 消息信息
     */
    MessageVO saveMessage(Long userId, MessageCreateRequest request);

    /**
     * 获取对话消息列表
     *
     * @param chatId 对话ID
     * @return 消息列表
     */
    List<MessageVO> listMessagesByChatId(Long chatId);
}