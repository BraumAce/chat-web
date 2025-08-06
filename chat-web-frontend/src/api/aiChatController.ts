// @ts-ignore
/* eslint-disable */
import request from "@/libs/request";

/** 创建对话 POST /api/ai/chat */
export async function createChatUsingPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.createChatUsingPOSTParams,
  body: API.ChatCreateRequest,
  options?: { [key: string]: any }
) {
  return request<API.ResultChatVO_>("/api/ai/chat", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    params: {
      ...params,
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除对话 DELETE /api/ai/chat/${param0} */
export async function deleteChatUsingDelete(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteChatUsingDELETEParams,
  options?: { [key: string]: any }
) {
  const { chatId: param0, ...queryParams } = params;
  return request<API.ResultBoolean_>(`/api/ai/chat/${param0}`, {
    method: "DELETE",
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** AI对话 POST /api/ai/chat/completions */
export async function sendMessageUsingPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.sendMessageUsingPOSTParams,
  body: API.AIChatRequest,
  options?: { [key: string]: any }
) {
  return request<API.SseEmitter>("/api/ai/chat/completions", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    params: {
      ...params,
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取对话列表 GET /api/ai/chat/list */
export async function listChatsUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listChatsUsingGETParams,
  options?: { [key: string]: any }
) {
  return request<API.ResultListChatVO_>("/api/ai/chat/list", {
    method: "GET",
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 保存对话消息 POST /api/ai/chat/message */
export async function saveMessageUsingPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.saveMessageUsingPOSTParams,
  body: API.MessageCreateRequest,
  options?: { [key: string]: any }
) {
  return request<API.ResultMessageVO_>("/api/ai/chat/message", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    params: {
      ...params,
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取对话消息列表 GET /api/ai/chat/messages */
export async function listMessagesUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listMessagesUsingGETParams,
  options?: { [key: string]: any }
) {
  return request<API.ResultListMessageVO_>("/api/ai/chat/messages", {
    method: "GET",
    params: {
      ...params,
    },
    ...(options || {}),
  });
}
