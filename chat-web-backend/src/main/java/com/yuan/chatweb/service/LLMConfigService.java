package com.yuan.chatweb.service;

import com.yuan.chatweb.model.entity.LLMConfigDO;
import com.yuan.chatweb.model.request.llm.LLMConfigRequest;
import com.yuan.chatweb.model.vo.LLMConfigVO;

import java.util.List;

/**
 * 大模型配置服务接口
 *
 * @author BraumAce
 */
public interface LLMConfigService {

    /**
     * 添加大模型配置
     *
     * @param request 请求参数
     * @return 配置信息
     */
    LLMConfigVO addConfig(LLMConfigRequest request);

    /**
     * 更新大模型配置
     *
     * @param id      配置ID
     * @param request 请求参数
     * @return 配置信息
     */
    LLMConfigVO updateConfig(Long id, LLMConfigRequest request);

    /**
     * 删除大模型配置
     *
     * @param id 配置ID
     * @return 是否删除成功
     */
    Boolean deleteConfig(Long id);

    /**
     * 获取当前用户的大模型配置列表
     *
     * @param userId 用户ID
     * @return 配置列表
     */
    List<LLMConfigVO> listConfigsByUserId(Long userId);

    /**
     * 启用/禁用模型
     *
     * @param id        配置ID
     * @param isEnabled 是否启用
     * @return 是否设置成功
     */
    Boolean updateStatus(Long id, Boolean isEnabled);

    /**
     * 获取系统默认模型配置
     *
     * @return 系统默认模型配置
     */
    LLMConfigVO getSystemDefaultConfig();

    /**
     * 根据ID获取模型配置
     *
     * @param id 配置ID
     * @return 模型配置
     */
    LLMConfigDO getConfigById(Long id);

    /**
     * 获取用户当前使用的模型
     *
     * @param userId 用户ID
     * @return 当前使用的模型配置
     */
    LLMConfigVO getCurrentConfig(Long userId);
}