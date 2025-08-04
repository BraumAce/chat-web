package com.yuan.chatweb.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 消息VO类
 *
 * @author BraumAce
 */
@Data
@ApiModel(description = "消息VO类")
public class MessageVO {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("对话ID")
    private Long chatId;

    @ApiModelProperty("发送者类型")
    private String senderType;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;
}