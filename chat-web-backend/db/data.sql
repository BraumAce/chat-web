-- 插入测试消息数据
INSERT INTO `messages` (`sender_id`, `receiver_id`, `content`, `sent_time`, `is_read`) VALUES
('user1', 'user2', '你好，这是第一条消息', NOW() - INTERVAL 2 DAY, 1),
('user2', 'user1', '你好，很高兴收到你的消息', NOW() - INTERVAL 1 DAY, 1),
('user1', 'user2', '我们来测试一下聊天功能', NOW() - INTERVAL 12 HOUR, 1),
('user2', 'user1', '好的，测试看起来不错', NOW() - INTERVAL 10 HOUR, 1),
('user1', 'user2', '这是最新的一条消息', NOW() - INTERVAL 5 HOUR, 0),
('user1', 'user3', '你好user3，这是发给你的消息', NOW() - INTERVAL 1 HOUR, 0);