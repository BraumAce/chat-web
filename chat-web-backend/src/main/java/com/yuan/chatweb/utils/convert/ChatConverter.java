package com.yuan.chatweb.utils.convert;

import com.yuan.chatweb.model.entity.ChatDO;
import com.yuan.chatweb.model.entity.MessageDO;
import com.yuan.chatweb.model.vo.ChatVO;
import com.yuan.chatweb.model.vo.MessageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对话转换器
 *
 * @author BraumAce
 */
@Mapper
public interface ChatConverter {

    ChatConverter INSTANCE = Mappers.getMapper(ChatConverter.class);

    ChatVO toChatVO(ChatDO chatDO);

    ChatDO toChatDO(ChatVO chatVO);

    MessageVO toMessageVO(MessageDO messageDO);

    MessageDO toMessageDO(MessageVO messageVO);
}
