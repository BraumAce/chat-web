package com.yuan.chatweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.chatweb.model.entity.MessageDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息Mapper接口
 *
 * @author BraumAce
 */
@Mapper
public interface MessageMapper extends BaseMapper<MessageDO> {
}