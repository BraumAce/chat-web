package com.yuan.chatweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.chatweb.model.entity.ChatDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对话Mapper接口
 *
 * @author BraumAce
 */
@Mapper
public interface ChatMapper extends BaseMapper<ChatDO> {
}