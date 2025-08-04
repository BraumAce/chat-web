package com.yuan.chatweb.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息DTO
 *
 * @author BraumAce
 */
@Data
public class MessageDTO {
    /**
     * 消息ID
     */
    private Long id;

    /**
     * 对话ID
     */
    private Long chatId;

    /**
     * 发送者类型(system, user, assistant)
     */
    private String senderType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
