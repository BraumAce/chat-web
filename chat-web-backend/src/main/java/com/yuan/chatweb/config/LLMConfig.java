package com.yuan.chatweb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 大模型配置类
 *
 * @author BraumAce
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "llm.default")
public class LLMConfig {

    /**
     * 默认模型名称
     */
    private String modelName;

    /**
     * 默认模型ID
     */
    private String modelId;

    /**
     * 默认API URL
     */
    private String apiUrl;

    /**
     * 默认API密钥
     */
    private String apiKey;
}