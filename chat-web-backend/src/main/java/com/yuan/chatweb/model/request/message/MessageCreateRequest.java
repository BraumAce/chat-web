package com.yuan.chatweb.model.request.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 消息请求类
 *
 * @author BraumAce
 */
@Data
@ApiModel(description = "消息请求类")
public class MessageCreateRequest {

    @ApiModelProperty("对话ID")
    private Long chatId;

    @ApiModelProperty("模型配置ID")
    private Long modelConfigId;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("消息角色")
    private String role;

    @ApiModelProperty("是否流式输出")
    private Boolean stream;
}
