package com.yuan.chatweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 聊天Web应用程序启动类
 * 
 * @author yuan
 */
@SpringBootApplication
@MapperScan("com.yuan.chatweb.repository")
public class ChatWebApplication {

    /**
     * 应用程序入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ChatWebApplication.class, args);
    }
}