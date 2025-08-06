// @ts-ignore
/* eslint-disable */
import request from "@/libs/request";

/** 获取用户信息 GET /api/api/user/${param0} */
export async function getUserInfoUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserInfoUsingGETParams,
  options?: { [key: string]: any }
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResultUserVO_>(`/api/api/user/${param0}`, {
    method: "GET",
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 编辑用户个人信息 PUT /api/api/user/${param0}/edit */
export async function editUserInfoUsingPut(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.editUserInfoUsingPUTParams,
  body: API.UserEditRequest,
  options?: { [key: string]: any }
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ResultUserVO_>(`/api/api/user/${param0}/edit`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 忘记密码 PUT /api/api/user/forgotPassword */
export async function forgotPasswordUsingPut(
  body: API.UserEditRequest,
  options?: { [key: string]: any }
) {
  return request<API.ResultBoolean_>("/api/user/forgotPassword", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
    ...(options || {}),
  });
}

/** 用户登录 POST /api/api/user/login */
export async function loginUsingPost(
  body: API.UserLoginRequest,
  options?: { [key: string]: any }
) {
  return request<API.ResultUserVO_>("/api/user/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
    ...(options || {}),
  });
}

/** 用户注册 POST /api/api/user/register */
export async function registerUsingPost(
  body: API.UserRegisterRequest,
  options?: { [key: string]: any }
) {
  return request<API.ResultUserVO_>("/api/user/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
    ...(options || {}),
  });
}
