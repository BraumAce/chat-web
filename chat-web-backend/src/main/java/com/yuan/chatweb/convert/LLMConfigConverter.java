package com.yuan.chatweb.convert;

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
     * ChatDO转ChatVO
     *
     * @param chatDO DO对象
     * @return VO对象
     */
    @Mapping(source = "modelConfigId", target = "modelConfigId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    ChatVO toChatVO(ChatDO chatDO);

    /**
     * MessageDO转MessageVO
     *
     * @param messageDO DO对象
     * @return VO对象
     */
    @Mapping(source = "conversationId", target = "conversationId")
    @Mapping(source = "senderType", target = "senderType")
    @Mapping(source = "createdAt", target = "createdAt")
    MessageVO toMessageVO(MessageDO messageDO);
}