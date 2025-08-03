package com.yuan.chatweb.controller;

import com.yuan.chatweb.model.entity.UserDO;
import com.yuan.chatweb.model.request.user.UserLoginRequest;
import com.yuan.chatweb.model.request.user.UserRegisterRequest;
import com.yuan.chatweb.model.request.user.UserEditRequest;
import com.yuan.chatweb.utils.common.Result;
import com.yuan.chatweb.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户控制器
 *
 * @author BraumAce
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param request 登录请求参数
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<UserDO> login(@RequestBody UserLoginRequest request) {
        try {
            UserDO user = userService.login(request);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户注册
     *
     * @param request 注册请求参数
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<UserDO> register(@RequestBody UserRegisterRequest request) {
        try {
            UserDO user = userService.register(request);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户编辑个人信息
     *
     * @param id      用户ID
     * @param request 编辑个人信息请求参数
     * @return 编辑结果
     */
    @PutMapping("/{id}/edit")
    public Result<UserDO> editUserInfo(@PathVariable Long id, @RequestBody UserEditRequest request) {
        try {
            UserDO user = userService.editUserInfo(id, request);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 忘记密码
     *
     * @param request 忘记密码请求参数
     * @return 忘记密码结果
     */
    @PutMapping("/forgotPassword")
    public Result<Boolean> forgotPassword(@RequestBody UserEditRequest request) {
        try {
            boolean result = userService.forgotPassword(request.getUsername(), request.getEmail(), request.getNewPassword());
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public Result<UserDO> getUserInfo(@PathVariable Long id) {
        try {
            UserDO user = userService.findById(id);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}