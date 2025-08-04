package com.yuan.chatweb.service;

import com.yuan.chatweb.model.entity.ChatDO;
import com.yuan.chatweb.model.request.chat.ChatCreateRequest;
import com.yuan.chatweb.model.vo.ChatVO;
import java.util.List;

/**
 * 对话服务接口
 *
 * @author BraumAce
 */
public interface ChatService {

    /**
     * 创建对话
     *
     * @param userId  用户ID
     * @param request 请求参数
     * @return 对话信息
     */
    ChatVO createChat(Long userId, ChatCreateRequest request);

    /**
     * 获取用户对话列表
     *
     * @param userId 用户ID
     * @return 对话列表
     */
    List<ChatVO> listChatsByUserId(Long userId);

    /**
     * 删除对话
     *
     * @param userId 用户ID
     * @param chatId 对话ID
     * @return 是否删除成功
     */
    Boolean deleteChat(Long userId, Long chatId);

    /**
     * 根据ID获取对话
     *
     * @param chatId 对话ID
     * @return 对话信息
     */
    ChatDO getChatById(Long chatId);
}