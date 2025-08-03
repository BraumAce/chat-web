package com.yuan.chatweb.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 消息VO类
 *
 * @author BraumAce
 */
@Data
public class MessageVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 对话ID
     */
    private Long conversationId;

    /**
     * 发送者类型(1:用户,2:AI)
     */
    private Integer senderType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}