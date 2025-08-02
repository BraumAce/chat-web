# Chat Web 后端项目

## 项目概述

这是一个基于Spring Boot的聊天应用后端项目，提供消息发送、接收和管理功能。

## 技术栈

- Java 8
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.3
- MySQL 8.0
- Redis 7.0
- Maven 3.9.10

## 项目结构

```
src/main/java/com/yuan/chatweb/
├── controller/        # 控制器层，处理HTTP请求
├── service/           # 服务层，处理业务逻辑
│   └── impl/          # 服务实现类
├── mapper/            # 数据访问层，与数据库交互
├── model/             # 实体类，表示数据模型
└── ChatWebApplication.java  # 应用程序入口

src/main/resources/
└── application.yml  # 应用程序配置文件
```

## 功能特性

- 消息发送和接收
- 查看用户收到的消息
- 查看用户发送的消息
- 查看两个用户之间的对话
- 将消息标记为已读

## 如何运行

1. 确保已安装Java 8和Maven 3.9.10
2. 克隆项目到本地
3. 进入项目目录
4. 执行以下命令构建并运行项目：

```bash
mvn clean install
mvn spring-boot:run
```

5. 应用将在 http://localhost:8080/api 上运行


## API接口

### 发送消息
- POST /api/messages

### 获取用户收到的所有消息
- GET /api/messages/received/{userId}

### 获取用户发送的所有消息
- GET /api/messages/sent/{userId}

### 获取两个用户之间的对话
- GET /api/messages/conversation?user1Id={user1Id}&user2Id={user2Id}

### 将消息标记为已读
- PUT /api/messages/{messageId}/read

## 开发环境

- IDE: 任意Java IDE（如IntelliJ IDEA、Eclipse等）
- 数据库: MySQL 8.0

## 数据库配置

1. 创建名为`chat_web`的MySQL数据库
2. 数据库配置在`application.yml`文件中，可根据需要修改
3. 项目启动时会自动执行`schema.sql`和`data.sql`脚本初始化数据库