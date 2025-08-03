package com.yuan.chatweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.chatweb.model.entity.LLMConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 大模型配置Mapper接口
 *
 * @author BraumAce
 */
@Mapper
public interface LLMConfigMapper extends BaseMapper<LLMConfigDO> {
}