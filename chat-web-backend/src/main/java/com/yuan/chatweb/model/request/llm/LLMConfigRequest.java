package com.yuan.chatweb.model.request.llm;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

/**
 * 大模型配置请求类
 *
 * @author BraumAce
 */
@Data
public class LLMConfigRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 模型名称
     */
    @NotBlank(message = "模型名称不能为空")
    private String modelName;

    /**
     * 模型ID
     */
    @NotBlank(message = "模型ID不能为空")
    private String modelId;

    /**
     * API地址
     */
    @NotBlank(message = "API URL不能为空")
    @URL(message = "API URL格式不正确")
    private String apiUrl;

    /**
     * API密钥
     */
    @NotBlank(message = "API Key不能为空")
    private String apiKey;

    /**
     * 是否启用
     */
    private Boolean isEnabled;
}