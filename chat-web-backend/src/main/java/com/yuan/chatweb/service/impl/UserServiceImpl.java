package com.yuan.chatweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.chatweb.mapper.UserMapper;
import com.yuan.chatweb.model.entity.UserDO;
import com.yuan.chatweb.model.request.user.UserLoginRequest;
import com.yuan.chatweb.model.request.user.UserRegisterRequest;
import com.yuan.chatweb.model.request.user.UserEditRequest;
import com.yuan.chatweb.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用户服务实现类
 *
 * @author BraumAce
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDO login(UserLoginRequest request) {
        // 根据用户名查找用户
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", request.getUsername()));
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        return user;
    }

    @Override
    public UserDO register(UserRegisterRequest request) {
        // 检查用户名是否已存在
        UserDO existingUser = findByUsernameOrEmail(request.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱是否已存在
        existingUser = userMapper.selectOne(new QueryWrapper<UserDO>().eq("email", request.getEmail()));
        if (existingUser != null) {
            throw new RuntimeException("邮箱已被注册");
        }

        // 检查两次输入的密码是否一致
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // 创建新用户
        UserDO user = new UserDO();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setNickname(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userMapper.insert(user);
        return user;
    }

    @Override
    public UserDO findByUsernameOrEmail(String usernameOrEmail) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", usernameOrEmail).or().eq("email", usernameOrEmail);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public UserDO findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public UserDO editUserInfo(Long id, UserEditRequest request) {
        UserDO user = userMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

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
            if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new RuntimeException("原密码错误");
            }
            // 更新密码
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        userMapper.updateById(user);
        return user;
    }

    @Override
    public boolean forgotPassword(String username, String email, String newPassword) {
        // 根据用户名查找用户
        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", username));
        if (user == null) {
            throw new RuntimeException("用户名不正确或不存在");
        }

        // 验证邮箱
        if (!user.getEmail().equals(email)) {
            throw new RuntimeException("邮箱不正确或不存在");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        return userMapper.updateById(user) > 0;
    }
}