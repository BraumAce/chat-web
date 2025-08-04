package com.yuan.chatweb.model.request.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 对话请求类
 *
 * @author BraumAce
 */
@Data
@ApiModel(description = "创建对话请求类")
public class ChatCreateRequest {

    @ApiModelProperty("对话标题")
    private String title;

    @ApiModelProperty("模型配置ID")
    private Long modelConfigId;
}