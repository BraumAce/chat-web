package com.yuan.chatweb.model.vo;

import com.yuan.chatweb.model.dto.ExtraInfo;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户VO类
 *
 * @author BraumAce
 */
@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 额外信息
     */
    private ExtraInfo extraInfo;
}