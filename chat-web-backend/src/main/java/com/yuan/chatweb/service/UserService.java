package com.yuan.chatweb.service;

import com.yuan.chatweb.model.dto.UserDTO;
import com.yuan.chatweb.model.request.user.UserLoginRequest;
import com.yuan.chatweb.model.request.user.UserRegisterRequest;
import com.yuan.chatweb.model.request.user.UserEditRequest;

/**
 * 用户服务接口
 *
 * @author BraumAce
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param request 登录请求参数
     * @return 登录成功的用户信息
     */
    UserDTO login(UserLoginRequest request);

    /**
     * 用户注册
     *
     * @param request 注册请求参数
     * @return 注册成功的用户信息
     */
    UserDTO register(UserRegisterRequest request);

    /**
     * 根据用户名或邮箱查找用户
     *
     * @param usernameOrEmail 用户名或邮箱
     * @return 用户信息
     */
    UserDTO findByUsernameOrEmail(String usernameOrEmail);

    /**
     * 根据ID查找用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserDTO findById(Long id);

    /**
     * 用户编辑个人信息
     *
     * @param id      用户ID
     * @param request 编辑个人信息请求参数
     * @return 编辑后的用户信息
     */
    UserDTO editUserInfo(Long id, UserEditRequest request);

    /**
     * 忘记密码
     *
     * @param username    用户名
     * @param email       邮箱
     * @param newPassword 新密码
     * @return 是否重置成功
     */
    boolean forgotPassword(String username, String email, String newPassword);
}