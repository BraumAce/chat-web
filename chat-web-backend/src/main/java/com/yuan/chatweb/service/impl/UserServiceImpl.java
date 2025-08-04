package com.yuan.chatweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.chatweb.enums.exception.UserErrorCode;
import com.yuan.chatweb.exception.BusinessException;
import com.yuan.chatweb.utils.ThrowUtil;
import com.yuan.chatweb.mapper.UserMapper;
import com.yuan.chatweb.model.dto.UserDTO;
import com.yuan.chatweb.model.entity.UserDO;
import com.yuan.chatweb.model.request.user.UserLoginRequest;
import com.yuan.chatweb.model.request.user.UserRegisterRequest;
import com.yuan.chatweb.model.request.user.UserEditRequest;
import com.yuan.chatweb.service.UserService;
import com.yuan.chatweb.utils.convert.UserConvert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现类
 *
 * @author BraumAce
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO login(UserLoginRequest request) {
        // 根据用户名查找用户
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", request.getUsername()));
        if (user == null) {
            throw new BusinessException(UserErrorCode.USER_NOT_EXIST);
        }

        // 验证密码
        if (!user.getPassword().equals(request.getPassword())) {
            throw new BusinessException(UserErrorCode.USER_PASSWORD_ERROR);
        }

        return UserConvert.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public UserDTO register(UserRegisterRequest request) {
        // 检查用户名是否已存在
        UserDO existingUser = this.getOne(new QueryWrapper<UserDO>().eq("username", request.getUsername()));
        ThrowUtil.throwIf(existingUser != null, UserErrorCode.USER_USERNAME_EXIST);

        // 检查邮箱是否已存在
        existingUser = this.getOne(new QueryWrapper<UserDO>().eq("email", request.getEmail()));
        ThrowUtil.throwIf(existingUser != null, UserErrorCode.USER_EMAIL_EXIST);

        // 检查两次输入的密码是否一致
        ThrowUtil.throwIf(!request.getPassword().equals(request.getConfirmPassword()), UserErrorCode.USER_PASSWORD_INCONSISTENT);

        // 创建新用户
        UserDO user = new UserDO();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setNickname(request.getUsername());
        user.setPassword(request.getPassword());

        userMapper.insert(user);
        return UserConvert.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public UserDTO findByUsernameOrEmail(String usernameOrEmail) {
        UserDO userDO = this.getOne(new QueryWrapper<UserDO>()
                .eq("username", usernameOrEmail)
                .or()
                .eq("email", usernameOrEmail));
        return userDO != null ? UserConvert.INSTANCE.convertToUserDTO(userDO) : null;
    }

    @Override
    public UserDTO findById(Long id) {
        UserDO userDO = userMapper.selectById(id);
        return userDO != null ? UserConvert.INSTANCE.convertToUserDTO(userDO) : null;
    }

    @Override
    public UserDTO editUserInfo(Long id, UserEditRequest request) {
        UserDO user = userMapper.selectById(id);
        ThrowUtil.throwIf(user == null, UserErrorCode.USER_NOT_EXIST);

        // 更新用户信息
        if (request.getNickname() != null) {
            user.setNickname(request.getNickname());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }
        
        // 修改密码逻辑：需要验证旧密码
        if (request.getOldPassword() != null && request.getNewPassword() != null) {
            // 验证旧密码
            if (!request.getOldPassword().equals(user.getPassword())) {
                throw new BusinessException(UserErrorCode.USER_OLD_PASSWORD_ERROR);
            }
            // 更新密码
            user.setPassword(request.getNewPassword());
        }

        userMapper.updateById(user);
        return UserConvert.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public boolean forgotPassword(String username, String email, String newPassword) {
        // 根据用户名查找用户
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", username));
        ThrowUtil.throwIf(user == null, UserErrorCode.USER_USERNAME_NOT_EXIST);

        // 验证邮箱
        ThrowUtil.throwIf(!user.getEmail().equals(email), UserErrorCode.USER_EMAIL_NOT_EXIST);

        // 更新密码
        user.setPassword(newPassword);
        return userMapper.updateById(user) > 0;
    }
}