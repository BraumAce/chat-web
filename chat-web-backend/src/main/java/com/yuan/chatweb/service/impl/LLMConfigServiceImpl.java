package com.yuan.chatweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.chatweb.config.LLMConfig;
import com.yuan.chatweb.model.entity.UserDO;
import com.yuan.chatweb.utils.convert.LLMConfigConverter;
import com.yuan.chatweb.enums.exception.LLMErrorCode;
import com.yuan.chatweb.mapper.LLMConfigMapper;
import com.yuan.chatweb.model.entity.LLMConfigDO;
import com.yuan.chatweb.model.request.llm.LLMConfigRequest;
import com.yuan.chatweb.model.vo.LLMConfigVO;
import com.yuan.chatweb.service.LLMConfigService;
import com.yuan.chatweb.utils.ThrowUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 大模型配置服务实现类
 *
 * @author BraumAce
 */
@Service
public class LLMConfigServiceImpl extends ServiceImpl<LLMConfigMapper, LLMConfigDO> implements LLMConfigService {

    @Resource
    private LLMConfigMapper llmConfigMapper;

    @Resource
    private LLMConfig llmConfig;

    @Override
    public LLMConfigVO addConfig(LLMConfigRequest request) {
        // 使用转换器将Request转换为DO
        LLMConfigDO configDO = LLMConfigConverter.INSTANCE.toLLMConfigDO(request);
        
        // 保存模型配置到数据库
        boolean saved = llmConfigMapper.insert(configDO) > 0;
        ThrowUtil.throwIf(!saved, LLMErrorCode.CONFIG_OPERATION_ERROR, "添加模型配置失败");

        // 转换为VO并返回
        return LLMConfigConverter.INSTANCE.toLLMConfigVO(configDO);
    }

    @Override
    public LLMConfigVO updateConfig(Long id, LLMConfigRequest request) {
        // 检查是否存在指定ID的模型配置
        LLMConfigDO existingConfig = llmConfigMapper.selectById(id);
        ThrowUtil.throwIf(existingConfig == null, LLMErrorCode.CONFIG_NOT_FOUND, "模型配置不存在");

        // 更新DO
        LLMConfigDO configDO = LLMConfigConverter.INSTANCE.toLLMConfigDO(request);
        configDO.setId(id);

        // 更新模型配置信息
        boolean updated = llmConfigMapper.updateById(configDO) > 0;
        ThrowUtil.throwIf(!updated, LLMErrorCode.CONFIG_OPERATION_ERROR, "更新模型配置失败");

        // 查询更新后的数据并返回
        LLMConfigDO updatedConfig = llmConfigMapper.selectById(id);
        return LLMConfigConverter.INSTANCE.toLLMConfigVO(updatedConfig);
    }

    @Override
    public Boolean deleteConfig(Long id) {
        // 检查是否存在指定ID的模型配置
        LLMConfigDO existingConfig = llmConfigMapper.selectById(id);
        ThrowUtil.throwIf(existingConfig == null, LLMErrorCode.CONFIG_NOT_FOUND, "模型配置不存在");

        // 从数据库中删除模型配置
        boolean removed = llmConfigMapper.deleteById(id) > 0;
        ThrowUtil.throwIf(!removed, LLMErrorCode.CONFIG_OPERATION_ERROR, "删除模型配置失败");

        return true;
    }

    @Override
    public List<LLMConfigVO> listConfigsByUserId(Long userId) {
        // 根据用户ID查询模型配置列表
        QueryWrapper<LLMConfigDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<LLMConfigDO> configList = llmConfigMapper.selectList(queryWrapper);

        // 转换为VO列表并返回
        return configList.stream()
                .map(LLMConfigConverter.INSTANCE::toLLMConfigVO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean updateStatus(Long id, Boolean isEnabled) {
        // 检查是否存在指定ID的模型配置
        LLMConfigDO existingConfig = llmConfigMapper.selectById(id);
        ThrowUtil.throwIf(Objects.isNull(existingConfig), LLMErrorCode.CONFIG_NOT_FOUND, "模型配置不存在");

        // 更新模型的启用状态
        LLMConfigDO configDO = new LLMConfigDO();
        configDO.setId(id);
        configDO.setIsEnabled(isEnabled);
        boolean updated = llmConfigMapper.updateById(configDO) > 0;
        ThrowUtil.throwIf(!updated, LLMErrorCode.CONFIG_OPERATION_ERROR, "更新模型状态失败");

        return true;
    }

    @Override
    public LLMConfigVO getSystemDefaultConfig() {
        LLMConfigVO systemDefault = new LLMConfigVO();
        systemDefault.setId(0L);
        systemDefault.setModelName(llmConfig.getModelName());
        systemDefault.setModelId(llmConfig.getModelId());
        systemDefault.setApiUrl(llmConfig.getApiUrl());
        systemDefault.setApiKey(llmConfig.getApiKey());
        systemDefault.setIsEnabled(true);
        return systemDefault;
    }

    @Override
    public LLMConfigDO getConfigById(Long id) {
        return llmConfigMapper.selectById(id);
    }

    @Override
    public LLMConfigVO getCurrentConfig(Long userId) {
        // 查询用户启用的模型配置
        LambdaQueryWrapper<LLMConfigDO> queryWrapper = new LambdaQueryWrapper<LLMConfigDO>()
                .eq(LLMConfigDO::getId, userId)
                .eq(LLMConfigDO::getIsEnabled, true);
        List<LLMConfigDO> userConfigs = llmConfigMapper.selectList(queryWrapper);

        // 如果用户有自定义的启用模型，返回第一个
        if (!userConfigs.isEmpty()) {
            return LLMConfigConverter.INSTANCE.toLLMConfigVO(userConfigs.get(0));
        }

        // 否则返回系统默认模型
        return getSystemDefaultConfig();
    }
}