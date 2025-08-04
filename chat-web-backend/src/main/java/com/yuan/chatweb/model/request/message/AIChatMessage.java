package com.yuan.chatweb.model.request.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI对话消息请求类
 *
 * @author BraumAce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIChatMessage {

    private String role;

    private String content;
}
