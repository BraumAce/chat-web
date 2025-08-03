-- 初始化数据

-- 插入默认的Kimi模型配置
INSERT INTO `model_config` (`model_name`, `model_type`, `api_key`, `api_url`, `is_active`) 
VALUES ('kimi', 'chat', 'YOUR_API_KEY_HERE', 'https://api.moonshot.cn/v1/chat/completions', 1);

-- 可以添加更多默认模型配置
-- INSERT INTO `model_config` (`model_name`, `model_type`, `api_key`, `api_url`, `is_active`) 
-- VALUES ('gpt-3.5-turbo', 'chat', 'YOUR_API_KEY_HERE', 'https://api.openai.com/v1/chat/completions', 1);