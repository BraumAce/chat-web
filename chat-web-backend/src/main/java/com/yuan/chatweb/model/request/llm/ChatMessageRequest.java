package com.yuan.chatweb.model.request.llm;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 对话消息请求类
 *
 * @author BraumAce
 */
@Data
public class ChatMessageRequest {

    /**
     * 对话ID
     */
    @NotNull(message = "对话ID不能为空")
    private Long chatId;

    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String content;

    /**
     * 是否流式输出
     */
    private Boolean stream = false;
}