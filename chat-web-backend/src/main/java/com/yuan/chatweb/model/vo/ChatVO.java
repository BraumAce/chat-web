package com.yuan.chatweb.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 对话VO类
 *
 * @author BraumAce
 */
@Data
public class ChatVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 对话标题
     */
    private String title;

    /**
     * 模型配置ID
     */
    private Long modelConfigId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}