package com.yuan.chatweb.model.vo;

import lombok.Data;

/**
 * 大模型配置VO类
 *
 * @author BraumAce
 */
@Data
public class LLMConfigVO {

    /**
     * 主键ID
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
     * 是否启用
     */
    private Boolean isEnabled;
}