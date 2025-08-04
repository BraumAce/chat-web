package com.yuan.chatweb.utils.convert;

import com.yuan.chatweb.model.dto.LLMConfigDTO;
import com.yuan.chatweb.model.entity.ChatDO;
import com.yuan.chatweb.model.entity.LLMConfigDO;
import com.yuan.chatweb.model.entity.MessageDO;
import com.yuan.chatweb.model.request.llm.LLMConfigRequest;
import com.yuan.chatweb.model.vo.ChatVO;
import com.yuan.chatweb.model.vo.LLMConfigVO;
import com.yuan.chatweb.model.vo.MessageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 大模型配置转换器
 *
 * @author BraumAce
 */
@Mapper
public interface LLMConfigConverter {

    LLMConfigConverter INSTANCE = Mappers.getMapper(LLMConfigConverter.class);

    /**
     * LLMConfigRequest转LLMConfigDO
     *
     * @param request 请求参数
     * @return DO对象
     */
    LLMConfigDO toLLMConfigDO(LLMConfigRequest request);

    /**
     * LLMConfigDO转LLMConfigVO
     *
     * @param configDO DO对象
     * @return VO对象
     */
    LLMConfigVO toLLMConfigVO(LLMConfigDO configDO);

    /**
     * LLMConfigDO转LLMConfigDTO
     *
     * @param configDO DO对象
     * @return DTO对象
     */
    LLMConfigDTO toLLMConfigDTO(LLMConfigDO configDO);

    /**
     * LLMConfigVO转LLMConfigDTO
      * @param configVO VO对象
      * @return DTO对象
     */
    LLMConfigDTO toLLMConfigDTO(LLMConfigVO configVO);
}