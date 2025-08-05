-- MySQL database schema for ChatWeb application
-- 创建库
CREATE DATABASE IF NOT EXISTS chat_web;

-- 切换库
USE chat_web;

-- 用户表
CREATE TABLE `user`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`   VARCHAR(50)     NOT NULL COMMENT '用户名',
    `password`   VARCHAR(100)    NOT NULL COMMENT '密码',
    `email`      VARCHAR(100)    NOT NULL COMMENT '邮箱',
    `nickname`   VARCHAR(50)     NOT NULL COMMENT '昵称',
    `avatar`     VARCHAR(255)    DEFAULT NULL COMMENT '头像URL',
    `extra_info` VARCHAR(1000)   DEFAULT NULL COMMENT '额外信息',
    `created_at` TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 大模型配置表
CREATE TABLE `llm_config`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`    BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    `model_name` VARCHAR(100)    NOT NULL COMMENT '模型名称',
    `model_id`   VARCHAR(100)    NOT NULL COMMENT '模型ID',
    `api_url`    VARCHAR(500)    NOT NULL COMMENT 'API地址',
    `api_key`    VARCHAR(500)    NOT NULL COMMENT 'API密钥',
    `is_enabled` TINYINT(1)      NOT NULL DEFAULT '1' COMMENT '是否启用 0:禁用 1:启用',
    `created_at` TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='大模型配置表';

-- 对话表
CREATE TABLE `chat`
(
    `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '对话ID',
    `user_id`         BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    `title`           VARCHAR(255)    NOT NULL COMMENT '对话标题',
    `model_config_id` BIGINT UNSIGNED NOT NULL COMMENT '模型配置ID',
    `created_at`      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_model_config_id` (`model_config_id`),
    CONSTRAINT `fk_chat_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_chat_model_config_id` FOREIGN KEY (`model_config_id`) REFERENCES `llm_config` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='对话表';

-- 消息表
CREATE TABLE `message`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    `chat_id`     BIGINT UNSIGNED NOT NULL COMMENT '对话ID',
    `sender_type` VARCHAR(100)    NOT NULL COMMENT '发送者类型(system, user, assistant)',
    `content`     TEXT            NOT NULL COMMENT '消息内容',
    `created_at`  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_chat_id` (`chat_id`),
    CONSTRAINT `fk_message_chat_id` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='消息表';