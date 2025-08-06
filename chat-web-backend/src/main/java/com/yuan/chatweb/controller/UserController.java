package com.yuan.chatweb.controller;

import com.yuan.chatweb.model.dto.UserDTO;
import com.yuan.chatweb.model.request.user.UserLoginRequest;
import com.yuan.chatweb.model.request.user.UserRegisterRequest;
import com.yuan.chatweb.model.request.user.UserEditRequest;
import com.yuan.chatweb.model.vo.UserVO;
import com.yuan.chatweb.utils.convert.UserConvert;
import com.yuan.chatweb.common.Result;
import com.yuan.chatweb.service.UserService;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserVO> login(@RequestBody UserLoginRequest request) {
        UserDTO userDTO = userService.login(request);
        UserVO userVO = UserConvert.INSTANCE.convertToUserVO(userDTO);
        return Result.success(userVO);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<UserVO> register(@RequestBody UserRegisterRequest request) {
        UserDTO userDTO = userService.register(request);
        UserVO userVO = UserConvert.INSTANCE.convertToUserVO(userDTO);
        return Result.success(userVO);
    }

    @PutMapping("/{id}/edit")
    @ApiOperation("编辑用户个人信息")
    public Result<UserVO> editUserInfo(@PathVariable Long id,
                                       @RequestBody UserEditRequest request) {
        UserDTO userDTO = userService.editUserInfo(id, request);
        UserVO userVO = UserConvert.INSTANCE.convertToUserVO(userDTO);
        return Result.success(userVO);
    }

    @PutMapping("/forgotPassword")
    @ApiOperation("忘记密码")
    public Result<Boolean> forgotPassword(@RequestBody UserEditRequest request) {
        boolean result = userService.forgotPassword(request.getUsername(), request.getEmail(), request.getNewPassword());
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取用户信息")
    public Result<UserVO> getUserInfo(@PathVariable Long id) {
        UserDTO userDTO = userService.findById(id);
        UserVO userVO = UserConvert.INSTANCE.convertToUserVO(userDTO);
        return Result.success(userVO);
    }
}