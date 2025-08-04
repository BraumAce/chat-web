package com.yuan.chatweb.model.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户DTO类
 *
 * @author BraumAce
 */
@Data
public class UserDTO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 原密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}