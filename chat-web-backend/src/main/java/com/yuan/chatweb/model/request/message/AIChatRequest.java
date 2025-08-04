package com.yuan.chatweb.model.request.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * AI对话请求类
 *
 * @author BraumAce
 */
@Data
@ApiModel(description = "AI对话请求类")
public class AIChatRequest {

    @ApiModelProperty("模型名称")
    private String model;

    @ApiModelProperty("消息列表")
    private List<AIChatMessage> messages;

    @ApiModelProperty("是否流式返回")
    private Boolean stream;
}
