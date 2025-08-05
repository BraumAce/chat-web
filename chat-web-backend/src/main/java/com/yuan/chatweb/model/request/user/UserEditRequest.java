package com.yuan.chatweb.model.request.user;

import lombok.Data;

/**
 * 用户编辑个人信息请求参数
 *
 * @author BraumAce
 */
@Data
public class UserEditRequest {
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 模型配置ID
     */
    private Long modelConfigId;

    /**
     * 原密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;
}