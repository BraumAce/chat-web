package com.yuan.chatweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.chatweb.enums.exception.LLMErrorCode;
import com.yuan.chatweb.mapper.MessageMapper;
import com.yuan.chatweb.model.entity.MessageDO;
import com.yuan.chatweb.model.request.message.MessageCreateRequest;
import com.yuan.chatweb.model.vo.MessageVO;
import com.yuan.chatweb.service.ChatService;
import com.yuan.chatweb.service.MessageService;
import com.yuan.chatweb.utils.ThrowUtil;
import com.yuan.chatweb.utils.convert.ChatConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息服务实现类
 *
 * @author BraumAce
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageDO> implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private ChatService chatService;

    @Override
    public MessageVO saveMessage(Long userId, MessageCreateRequest request) {
        // 检查对话是否存在且属于当前用户
        Long chatId = request.getChatId();
        ThrowUtil.throwIf(chatService.getChatById(chatId) == null, LLMErrorCode.CONFIG_NOT_FOUND, "对话不存在");

        // 保存消息
        MessageDO messageDO = new MessageDO();
        messageDO.setChatId(chatId);
        messageDO.setSenderType(request.getRole());
        messageDO.setContent(request.getContent());
        messageMapper.insert(messageDO);
        return ChatConverter.INSTANCE.toMessageVO(messageDO);
    }

    @Override
    public List<MessageVO> listMessagesByChatId(Long chatId) {
        // 根据对话ID查询消息列表
        LambdaQueryWrapper<MessageDO> queryWrapper = new LambdaQueryWrapper<MessageDO>()
                .eq(MessageDO::getChatId, chatId);
        List<MessageDO> messageList = messageMapper.selectList(queryWrapper);

        // 转换为VO列表并返回
        return messageList.stream()
                .map(ChatConverter.INSTANCE::toMessageVO)
                .collect(Collectors.toList());
    }
}