-- 创建消息表
CREATE TABLE IF NOT EXISTS `messages` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` VARCHAR(50) NOT NULL COMMENT '发送者ID',
  `receiver_id` VARCHAR(50) NOT NULL COMMENT '接收者ID',
  `content` VARCHAR(1000) NOT NULL COMMENT '消息内容',
  `sent_time` DATETIME NOT NULL COMMENT '发送时间',
  `is_read` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已读',
  PRIMARY KEY (`id`),
  INDEX `idx_sender` (`sender_id`),
  INDEX `idx_receiver` (`receiver_id`),
  INDEX `idx_conversation` (`sender_id`, `receiver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';