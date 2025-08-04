package com.yuan.chatweb.model.dto;

import lombok.Data;

/**
 * 模型配置DTO
 *
 * @author BraumAce
 */
@Data
public class LLMConfigDTO {
    /**
     * 模型配置ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 模型ID
     */
    private String modelId;

    /**
     * API地址
     */
    private String apiUrl;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * 是否启用 0:禁用 1:启用
     */
    private Boolean isEnabled;
}
