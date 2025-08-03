# Chat Web API 接口文档

## 基础URL

所有API接口的基础URL为: `http://localhost:8080/api`

---

## 用户相关接口

### 1. 用户注册

- **接口地址**: `/user/register`
- **请求方法**: `POST`
- **接口描述**: 用户注册新账户

#### 请求参数

| 参数名             | 类型     | 描述   | 是否必填 |
|-----------------|--------|------|------|
| username        | string | 用户名  | 是    |
| email           | string | 邮箱地址 | 是    |
| password        | string | 密码   | 是    |
| confirmPassword | string | 确认密码 | 是    |

#### 响应示例

```json
{
  "success": true,
  "code": 200,
  "data": {
    "id": 1,
    "username": "testuser",
    "email": "test@example.com",
    "nickname": "testuser",
    "avatar": null,
    "createdAt": "2023-05-01T10:00:00",
    "updatedAt": "2023-05-01T10:00:00"
  },
  "message": "success"
}
```

---

### 2. 用户登录

- **接口地址**: `/user/login`
- **请求方法**: `POST`
- **接口描述**: 用户登录系统

#### 请求参数

| 参数名      | 类型     | 描述  | 是否必填 |
|----------|--------|-----|------|
| username | string | 用户名 | 是    |
| password | string | 密码  | 是    |

#### 响应示例

```json
{
  "success": true,
  "code": 200,
  "data": {
    "id": 1,
    "username": "testuser",
    "email": "test@example.com",
    "nickname": "testuser",
    "avatar": null,
    "createdAt": "2023-05-01T10:00:00",
    "updatedAt": "2023-05-01T10:00:00"
  },
  "message": "success"
}
```

---

### 3. 用户编辑个人信息

- **接口地址**: `/user/{id}/edit`
- **请求方法**: `PUT`
- **接口描述**: 用户编辑个人信息，包括昵称、邮箱、头像和密码

#### 请求参数

| 参数名         | 类型     | 描述           | 是否必填 |
|-------------|--------|--------------|------|
| id          | long   | 用户ID（路径参数）   | 是    |
| nickname    | string | 用户昵称         | 否    |
| email       | string | 邮箱地址         | 否    |
| avatar      | string | 头像URL        | 否    |
| oldPassword | string | 原密码（修改密码时必填） | 否    |
| newPassword | string | 新密码（修改密码时必填） | 否    |

#### 响应示例

```json
{
  "success": true,
  "code": 200,
  "data": {
    "id": 1,
    "username": "testuser",
    "email": "newemail@example.com",
    "nickname": "New Nickname",
    "avatar": "http://example.com/avatar.jpg",
    "createdAt": "2023-05-01T10:00:00",
    "updatedAt": "2023-05-02T10:00:00"
  },
  "message": "success"
}
```

---

### 4. 忘记密码

- **接口地址**: `/user/forgotPassword`
- **请求方法**: `PUT`
- **接口描述**: 用户忘记密码时，通过用户名和邮箱验证后重置密码

#### 请求参数

请求体参数 (UserEditRequest):
| 参数名 | 类型 | 描述 | 是否必填 |
|-------|------|------|-------|
| username | string | 用户名 | 是 |
| email | string | 邮箱 | 是 |
| newPassword | string | 新密码 | 是 |

#### 响应示例

成功响应:

```json
{
  "success": true,
  "code": 200,
  "data": true,
  "message": "密码重置成功"
}
```

错误响应:

```json
{
  "success": false,
  "code": 500,
  "data": null,
  "message": "用户名不正确或不存在"
}
```

或

```json
{
  "success": false,
  "code": 500,
  "data": null,
  "message": "邮箱不正确或不存在"
}
```

#### 错误码说明

| 错误码 | 说明                     |
|-----|------------------------|
| 500 | 用户名不正确或不存在 / 邮箱不正确或不存在 |

---

### 5. 获取用户信息

- **接口地址**: `/user/{id}`
- **请求方法**: `GET`
- **接口描述**: 根据用户ID获取用户信息

#### 请求参数

| 参数名 | 类型   | 描述         | 是否必填 |
|-----|------|------------|------|
| id  | long | 用户ID（路径参数） | 是    |

#### 响应示例

```json
{
  "success": true,
  "code": 200,
  "data": {
    "id": 1,
    "username": "testuser",
    "email": "test@example.com",
    "nickname": "testuser",
    "avatar": null,
    "createdAt": "2023-05-01T10:00:00",
    "updatedAt": "2023-05-01T10:00:00"
  },
  "message": "success"
}
```

---

## 消息相关接口

### 6. 发送消息

- **接口地址**: `/messages`
- **请求方法**: `POST`
- **接口描述**: 发送消息

> 注：该接口在当前版本中尚未实现

---

### 7. 获取用户收到的所有消息

- **接口地址**: `/messages/received/{userId}`
- **请求方法**: `GET`
- **接口描述**: 获取指定用户收到的所有消息

> 注：该接口在当前版本中尚未实现

#### 请求参数

| 参数名    | 类型   | 描述         | 是否必填 |
|--------|------|------------|------|
| userId | long | 用户ID（路径参数） | 是    |

---

### 8. 获取用户发送的所有消息

- **接口地址**: `/messages/sent/{userId}`
- **请求方法**: `GET`
- **接口描述**: 获取指定用户发送的所有消息

> 注：该接口在当前版本中尚未实现

#### 请求参数

| 参数名    | 类型   | 描述         | 是否必填 |
|--------|------|------------|------|
| userId | long | 用户ID（路径参数） | 是    |

---

### 9. 获取两个用户之间的对话

- **接口地址**: `/messages/conversation`
- **请求方法**: `GET`
- **接口描述**: 获取两个用户之间的对话记录

> 注：该接口在当前版本中尚未实现

#### 请求参数

| 参数名     | 类型   | 描述           | 是否必填 |
|---------|------|--------------|------|
| user1Id | long | 用户1的ID（查询参数） | 是    |
| user2Id | long | 用户2的ID（查询参数） | 是    |

---

### 10. 将消息标记为已读

- **接口地址**: `/messages/{messageId}/read`
- **请求方法**: `PUT`
- **接口描述**: 将指定消息标记为已读

> 注：该接口在当前版本中尚未实现

#### 请求参数

| 参数名       | 类型   | 描述         | 是否必填 |
|-----------|------|------------|------|
| messageId | long | 消息ID（路径参数） | 是    |