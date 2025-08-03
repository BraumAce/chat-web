package com.yuan.chatweb.controller;

import com.yuan.chatweb.model.dto.UserDTO;
import com.yuan.chatweb.model.request.user.UserLoginRequest;
import com.yuan.chatweb.model.request.user.UserRegisterRequest;
import com.yuan.chatweb.model.request.user.UserEditRequest;
import com.yuan.chatweb.model.vo.UserVO;
import com.yuan.chatweb.convert.UserConvert;
import com.yuan.chatweb.common.Result;
import com.yuan.chatweb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户控制器
 *
 * @author BraumAce
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户相关接口")
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
    @ApiOperation(value = "用户登录", notes = "用户登录系统")
    public Result<UserVO> login(@RequestBody @ApiParam("登录请求参数") UserLoginRequest request) {
        UserDTO userDTO = userService.login(request);
        UserVO userVO = UserConvert.INSTANCE.convertToUserVO(userDTO);
        return Result.success(userVO);
    }

    /**
     * 用户注册
     *
     * @param request 注册请求参数
     * @return 注册结果
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "用户注册新账户")
    public Result<UserVO> register(@RequestBody @ApiParam("注册请求参数") UserRegisterRequest request) {
        UserDTO userDTO = userService.register(request);
        UserVO userVO = UserConvert.INSTANCE.convertToUserVO(userDTO);
        return Result.success(userVO);
    }

    /**
     * 用户编辑个人信息
     *
     * @param id      用户ID
     * @param request 编辑个人信息请求参数
     * @return 编辑结果
     */
    @PutMapping("/{id}/edit")
    @ApiOperation(value = "编辑用户个人信息", notes = "编辑用户个人信息，包括昵称、邮箱、头像和密码")
    public Result<UserVO> editUserInfo(@PathVariable @ApiParam("用户ID") Long id, 
                                       @RequestBody @ApiParam("编辑个人信息请求参数") UserEditRequest request) {
        UserDTO userDTO = userService.editUserInfo(id, request);
        UserVO userVO = UserConvert.INSTANCE.convertToUserVO(userDTO);
        return Result.success(userVO);
    }

    /**
     * 忘记密码
     *
     * @param request 忘记密码请求参数
     * @return 忘记密码结果
     */
    @PutMapping("/forgotPassword")
    @ApiOperation(value = "忘记密码", notes = "用户忘记密码时，通过用户名和邮箱验证后重置密码")
    public Result<Boolean> forgotPassword(@RequestBody @ApiParam("忘记密码请求参数") UserEditRequest request) {
        boolean result = userService.forgotPassword(request.getUsername(), request.getEmail(), request.getNewPassword());
        return Result.success(result);
    }

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取用户信息", notes = "根据用户ID获取用户信息")
    public Result<UserVO> getUserInfo(@PathVariable @ApiParam("用户ID") Long id) {
        UserDTO userDTO = userService.findById(id);
        UserVO userVO = UserConvert.INSTANCE.convertToUserVO(userDTO);
        return Result.success(userVO);
    }
}