-- 初始化用户数据
INSERT INTO `user` (`id`, `username`, `password`, `email`) VALUES
(1, 'admin', 'admin123', 'admin@example.com'),
(2, 'test', 'test123', 'test@example.com');

-- 初始化系统默认模型配置（实际不存在于数据库中，仅作为配置文件提供）
-- 系统默认模型ID为0，不存储在数据库中