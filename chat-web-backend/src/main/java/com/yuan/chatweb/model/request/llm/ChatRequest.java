package com.yuan.chatweb.model.request.llm;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 对话请求类
 *
 * @author BraumAce
 */
@Data
public class ChatRequest {

    /**
     * 对话标题
     */
    @NotBlank(message = "对话标题不能为空")
    private String title;

    /**
     * 模型配置ID
     */
    private Long modelConfigId;
}