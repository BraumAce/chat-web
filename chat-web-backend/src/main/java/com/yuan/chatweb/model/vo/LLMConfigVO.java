package com.yuan.chatweb.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大模型配置VO类
 *
 * @author BraumAce
 */
@Data
@ApiModel(description = "大模型配置VO类")
public class LLMConfigVO {

    @ApiModelProperty("模型配置ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("模型名称")
    private String modelName;

    @ApiModelProperty("模型ID")
    private String modelId;

    @ApiModelProperty("API地址")
    private String apiUrl;

    @ApiModelProperty("API密钥")
    private String apiKey;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;
}