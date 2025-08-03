# Chat Web 前端项目

## 项目概述

这是一个基于React的AI聊天应用前端项目，提供用户登录、消息发送和接收等功能。

## 技术栈

- React 18
- Vite 4
- Ant Design 5
- React Router 6
- Axios

## 项目结构

```
src/
├── api/            # API服务
├── assets/         # 静态资源
├── components/     # 公共组件
├── pages/          # 页面组件
├── utils/          # 工具函数
├── App.jsx         # 应用主组件
├── App.css         # 应用样式
├── main.jsx        # 入口文件
└── index.css       # 全局样式
```

## 功能特性

- 用户登录
- 联系人列表
- 消息发送和接收
- 对话历史查看
- 未读消息提醒

## 如何运行

1. 确保已安装Node.js和npm
2. 克隆项目到本地
3. 进入项目目录
4. 执行以下命令安装依赖：

```bash
npm install
```

5. 执行以下命令启动开发服务器：

```bash
npm run dev
```

6. 应用将在 http://localhost:3000 上运行

## 构建生产版本

执行以下命令构建生产版本：

```bash
npm run build
```

构建后的文件将生成在`dist`目录中。

## 与后端集成

前端项目通过API与后端进行通信，API配置在`src/api`目录中。默认情况下，前端会将`/api`开头的请求代理到`http://localhost:8080`，可以在`vite.config.js`中修改代理配置。

## 测试账号

- 用户名：admin
- 密码：123456