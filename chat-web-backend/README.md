# Chat Web 后端项目

## 项目概述

这是一个基于Spring Boot的AI聊天应用后端项目，提供用户登录、与大模型对话、查看历史消息和配置自定义模型等功能。

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

- 用户注册、登录和认证
- 忘记密码重置
- 用户信息更新（昵称、邮箱、头像、密码）
- 与大模型进行对话
- 查看历史对话消息
- 配置和管理自定义模型

## 数据库设计

项目使用MySQL 8.0作为主要数据存储，包含以下核心表：

1. `user` - 用户表，存储用户基本信息
2. `model_config` - 模型配置表，存储不同AI模型的配置信息
3. `conversation` - 对话表，存储用户与AI的对话记录
4. `message` - 消息表，存储具体的对话消息

数据库表结构定义在 `db/chat_web_schema.sql` 文件中。

## 数据库初始化

1. 创建数据库：
   ```sql
   CREATE DATABASE chat_web DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. 执行表结构创建脚本：
   ```bash
   mysql -u your_username -p chat_web < db/chat_web_schema.sql
   ```

3. 执行初始化数据脚本：
   ```bash
   mysql -u your_username -p chat_web < db/chat_web_data.sql
   ```

   注意：在执行初始化数据脚本前，请根据实际情况修改 `db/chat_web_data.sql` 中的API密钥和其他配置信息。

## 如何运行

1. 确保已安装Java 8和Maven 3.9.10
2. 克隆项目到本地
3. 进入项目目录
4. 初始化数据库（参考上面的数据库初始化步骤）
5. 配置 `src/main/resources/application.yml` 中的数据库连接信息
6. 执行以下命令构建并运行项目：

```bash
mvn clean install
mvn spring-boot:run
```

5. 应用将在 http://localhost:8080/api 上运行


## API文档

详情请查看 [API接口文档](doc/API.md)

## 开发环境

- IDE: 任意Java IDE（如IntelliJ IDEA、Eclipse等）
- 数据库: MySQL 8.0

## 数据库配置

1. 创建名为`chat_web`的MySQL数据库
2. 数据库配置在`application.yml`文件中，可根据需要修改
3. 项目启动时会自动执行`schema.sql`和`data.sql`脚本初始化数据库