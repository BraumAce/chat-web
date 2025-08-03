package com.yuan.chatweb.controller;

import com.yuan.chatweb.common.Result;
import com.yuan.chatweb.model.request.llm.LLMConfigRequest;
import com.yuan.chatweb.model.vo.LLMConfigVO;
import com.yuan.chatweb.service.LLMConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
    public Result<LLMConfigVO> addConfig(@Valid @RequestBody LLMConfigRequest request) {
        LLMConfigVO configVO = llmConfigService.addConfig(request);
        return Result.success(configVO);
    }

    @GetMapping("/list")
    @ApiOperation("获取当前用户的大模型配置列表")
    public Result<List<LLMConfigVO>> listConfigs(@RequestParam Long userId) {
        List<LLMConfigVO> configList = llmConfigService.listConfigsByUserId(userId);
        return Result.success(configList);
    }

    @PutMapping("/{id}")
    @ApiOperation("更新大模型配置")
    public Result<LLMConfigVO> updateConfig(
            @ApiParam("配置ID") @PathVariable Long id,
            @Valid @RequestBody LLMConfigRequest request) {
        LLMConfigVO configVO = llmConfigService.updateConfig(id, request);
        return Result.success(configVO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除大模型配置")
    public Result<Boolean> deleteConfig(@ApiParam("配置ID") @PathVariable Long id) {
        Boolean result = llmConfigService.deleteConfig(id);
        return Result.success(result);
    }

    @PutMapping("/{id}/status")
    @ApiOperation("启用/禁用模型")
    public Result<Boolean> updateStatus(
            @ApiParam("配置ID") @PathVariable Long id,
            @RequestBody LLMConfigRequest request) {
        Boolean result = llmConfigService.updateStatus(id, request.getIsEnabled());
        return Result.success(result);
    }

    @GetMapping("/systemDefault")
    @ApiOperation("获取系统默认模型")
    public Result<LLMConfigVO> getSystemDefaultConfig() {
        LLMConfigVO systemDefault = llmConfigService.getSystemDefaultConfig();
        return Result.success(systemDefault);
    }

    @GetMapping("/current")
    @ApiOperation("获取当前用户使用的模型")
    public Result<LLMConfigVO> getCurrentConfig(@RequestParam Long userId) {
        LLMConfigVO currentConfig = llmConfigService.getCurrentConfig(userId);
        return Result.success(currentConfig);
    }
}