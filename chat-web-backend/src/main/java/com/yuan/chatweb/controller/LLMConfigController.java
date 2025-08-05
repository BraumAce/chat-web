package com.yuan.chatweb.controller;

import com.yuan.chatweb.common.Result;
import com.yuan.chatweb.model.dto.LLMConfigDTO;
import com.yuan.chatweb.model.request.llm.LLMConfigRequest;
import com.yuan.chatweb.model.vo.LLMConfigVO;
import com.yuan.chatweb.service.LLMConfigService;
import com.yuan.chatweb.utils.convert.LLMConfigConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 大模型配置控制器
 *
 * @author BraumAce
 */
@RestController
@RequestMapping("/llm/config")
@Api(tags = "大模型配置接口")
public class LLMConfigController {

    @Resource
    private LLMConfigService llmConfigService;

    @PostMapping
    @ApiOperation("添加大模型配置")
    public Result<LLMConfigVO> addConfig(@Validated @RequestBody LLMConfigRequest request) {
        LLMConfigDTO configDTO = llmConfigService.addConfig(request);
        return Result.success(LLMConfigConverter.INSTANCE.toLLMConfigVO(configDTO));
    }

    @GetMapping("/list")
    @ApiOperation("获取当前用户的大模型配置列表")
    public Result<List<LLMConfigVO>> listConfigs(@RequestParam Long userId) {
        List<LLMConfigDTO> configList = llmConfigService.listConfigsByUserId(userId);
        List<LLMConfigVO> configVOList = configList.stream()
                .map(LLMConfigConverter.INSTANCE::toLLMConfigVO)
                .collect(Collectors.toList());
        return Result.success(configVOList);
    }

    @PutMapping("/{id}")
    @ApiOperation("更新大模型配置")
    public Result<LLMConfigVO> updateConfig(@PathVariable Long id,
                                            @Validated @RequestBody LLMConfigRequest request) {
        LLMConfigDTO configDTO = llmConfigService.updateConfig(id, request);
        return Result.success(LLMConfigConverter.INSTANCE.toLLMConfigVO(configDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除大模型配置")
    public Result<Boolean> deleteConfig(@PathVariable Long id) {
        Boolean result = llmConfigService.deleteConfig(id);
        return Result.success(result);
    }

    @PutMapping("/{id}/status")
    @ApiOperation("启用/禁用模型")
    public Result<Boolean> updateStatus(@PathVariable Long id,
                                        @Validated @RequestBody LLMConfigRequest request) {
        Boolean result = llmConfigService.updateStatus(id, request.getIsEnabled());
        return Result.success(result);
    }

    @GetMapping("/systemDefault")
    @ApiOperation("获取系统默认模型")
    public Result<LLMConfigVO> getSystemDefaultConfig() {
        LLMConfigDTO systemDefault = llmConfigService.getSystemDefaultConfig();
        return Result.success(LLMConfigConverter.INSTANCE.toLLMConfigVO(systemDefault));
    }

    @GetMapping("/current")
    @ApiOperation("获取当前用户使用的模型")
    public Result<LLMConfigVO> getCurrentConfig(@RequestParam Long userId) {
        LLMConfigDTO currentConfig = llmConfigService.getCurrentConfig(userId);
        return Result.success(LLMConfigConverter.INSTANCE.toLLMConfigVO(currentConfig));
    }
}