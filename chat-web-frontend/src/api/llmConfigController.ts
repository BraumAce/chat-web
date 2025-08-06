// @ts-ignore
/* eslint-disable */
import request from "@/libs/request";

/** 添加大模型配置 POST /api/llm/config */
export async function addConfigUsingPost(
  body: API.LLMConfigRequest,
  options?: { [key: string]: any }
) {
  return request<API.ResultLLMConfigVO_>("/api/llm/config", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新大模型配置 PUT /api/llm/config/${param0} */
export async function updateConfigUsingPut(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateConfigUsingPUTParams,
  body: API.LLMConfigRequest,
  options?: { [key: string]: any }
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResultLLMConfigVO_>(`/api/llm/config/${param0}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除大模型配置 DELETE /api/llm/config/${param0} */
export async function deleteConfigUsingDelete(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteConfigUsingDELETEParams,
  options?: { [key: string]: any }
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResultBoolean_>(`/api/llm/config/${param0}`, {
    method: "DELETE",
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 启用/禁用模型 PUT /api/llm/config/${param0}/status */
export async function updateStatusUsingPut(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateStatusUsingPUTParams,
  body: API.LLMConfigRequest,
  options?: { [key: string]: any }
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResultBoolean_>(`/api/llm/config/${param0}/status`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 获取当前用户使用的模型 GET /api/llm/config/current */
export async function getCurrentConfigUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getCurrentConfigUsingGETParams,
  options?: { [key: string]: any }
) {
  return request<API.ResultLLMConfigVO_>("/api/llm/config/current", {
    method: "GET",
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 获取当前用户的大模型配置列表 GET /api/llm/config/list */
export async function listConfigsUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listConfigsUsingGETParams,
  options?: { [key: string]: any }
) {
  return request<API.ResultListLLMConfigVO_>("/api/llm/config/list", {
    method: "GET",
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 获取系统默认模型 GET /api/llm/config/systemDefault */
export async function getSystemDefaultConfigUsingGet(options?: {
  [key: string]: any;
}) {
  return request<API.ResultLLMConfigVO_>("/api/llm/config/systemDefault", {
    method: "GET",
    ...(options || {}),
  });
}
