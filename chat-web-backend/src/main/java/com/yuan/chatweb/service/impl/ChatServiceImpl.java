package com.yuan.chatweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.chatweb.convert.LLMConfigConverter;
import com.yuan.chatweb.enums.LLMErrorCode;
import com.yuan.chatweb.mapper.ChatMapper;
import com.yuan.chatweb.model.entity.ChatDO;
import com.yuan.chatweb.model.request.llm.ChatRequest;
import com.yuan.chatweb.model.vo.ChatVO;
import com.yuan.chatweb.service.ChatService;
import com.yuan.chatweb.service.LLMConfigService;
import com.yuan.chatweb.utils.ThrowUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对话服务实现类
 *
 * @author BraumAce
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, ChatDO> implements ChatService {

    @Resource
    private ChatMapper chatMapper;

    @Resource
    private LLMConfigService llmConfigService;

    @Override
    public ChatVO createChat(Long userId, ChatRequest request) {
        // 创建对话DO
        ChatDO chatDO = new ChatDO();
        chatDO.setUserId(userId);
        chatDO.setTitle(request.getTitle());

        // 如果没有指定模型配置ID，则使用用户当前的模型配置
        if (request.getModelConfigId() != null) {
            chatDO.setModelConfigId(request.getModelConfigId());
        } else {
            // 获取用户当前使用的模型配置
            chatDO.setModelConfigId(llmConfigService.getCurrentConfig(userId).getId());
        }

        // 保存对话到数据库
        boolean saved = chatMapper.insert(chatDO) > 0;
        ThrowUtil.throwIf(!saved, LLMErrorCode.CONFIG_OPERATION_ERROR, "创建对话失败");

        // 转换为VO并返回
        return LLMConfigConverter.INSTANCE.toChatVO(chatDO);
    }

    @Override
    public List<ChatVO> listChatsByUserId(Long userId) {
        // 根据用户ID查询对话列表
        QueryWrapper<ChatDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<ChatDO> chatList = chatMapper.selectList(queryWrapper);

        // 转换为VO列表并返回
        return chatList.stream()
                .map(LLMConfigConverter.INSTANCE::toChatVO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteChat(Long userId, Long chatId) {
        // 检查是否存在指定ID的对话且属于当前用户
        QueryWrapper<ChatDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", chatId).eq("user_id", userId);
        ChatDO existingChat = chatMapper.selectOne(queryWrapper);
        ThrowUtil.throwIf(existingChat == null, LLMErrorCode.CONFIG_NOT_FOUND, "对话不存在");

        // 从数据库中删除对话
        boolean removed = chatMapper.deleteById(chatId) > 0;
        ThrowUtil.throwIf(!removed, LLMErrorCode.CONFIG_OPERATION_ERROR, "删除对话失败");

        return true;
    }

    @Override
    public ChatDO getChatById(Long chatId) {
        return chatMapper.selectById(chatId);
    }
}