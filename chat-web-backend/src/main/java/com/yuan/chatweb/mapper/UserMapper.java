package com.yuan.chatweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.chatweb.model.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 *
 * @author BraumAce
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}