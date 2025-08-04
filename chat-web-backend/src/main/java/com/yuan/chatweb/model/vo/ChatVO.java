package com.yuan.chatweb.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 对话VO类
 *
 * @author BraumAce
 */
@Data
@ApiModel(description = "对话VO类")
public class ChatVO {

    @ApiModelProperty("对话ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("对话标题")
    private String title;

    @ApiModelProperty("模型配置ID")
    private Long modelConfigId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;
}