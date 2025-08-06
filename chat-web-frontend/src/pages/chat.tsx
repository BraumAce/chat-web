import React, { useState, useEffect, useRef } from 'react';
import { 
  Layout, 
  Card, 
  Typography, 
  Button, 
  Select, 
  Avatar, 
  Input,
  List,
  Dropdown,
  type MenuProps,
  message
} from 'antd';
import { 
  MessageOutlined, 
  PlusOutlined, 
  SendOutlined, 
  UserOutlined,
  DeleteOutlined,
  LogoutOutlined
} from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { 
  createChatUsingPost,
  deleteChatUsingDelete,
  listChatsUsingGet,
  sendMessageUsingPost,
  saveMessageUsingPost,
  listMessagesUsingGet
} from '../api/aiChatController';

const { Header, Sider, Content } = Layout;
const { Title } = Typography;

const ChatPage: React.FC = () => {
  const navigate = useNavigate();
  const [chats, setChats] = useState<API.ChatVO[]>([]);
  const [currentChat, setCurrentChat] = useState<API.ChatVO | null>(null);
  const [messages, setMessages] = useState<API.MessageVO[]>([]);
  const [inputValue, setInputValue] = useState('');
  const [loading, setLoading] = useState(false);
  const messagesEndRef = useRef<HTMLDivElement>(null);

  // 获取对话列表
  const fetchChats = async () => {
    try {
      const res = await listChatsUsingGet({ userId: 1 }); // 临时使用用户ID 1
      if (res.success) {
        setChats(res.data || []);
        // 如果没有当前对话且有对话列表，则设置第一个为当前对话
        if (!currentChat && res.data && res.data.length > 0) {
          setCurrentChat(res.data[0]);
        }
      }
    } catch (error) {
      console.error('获取对话列表失败:', error);
    }
  };

  // 获取对话消息列表
  const fetchMessages = async (chatId: number) => {
    try {
      const res = await listMessagesUsingGet({ chatId });
      if (res.success) {
        setMessages(res.data || []);
      }
    } catch (error) {
      console.error('获取消息列表失败:', error);
    }
  };

  // 新建对话
  const handleCreateChat = async () => {
    try {
      const res = await createChatUsingPost(
        { userId: 1 }, // 临时使用用户ID 1
        { title: '新对话' }
      );
      
      if (res.success) {
        message.success('创建对话成功');
        setCurrentChat(res.data!);
        setMessages([]);
        await fetchChats();
      } else {
        message.error(res.message || '创建对话失败');
      }
    } catch (error) {
      console.error('创建对话失败:', error);
      message.error('创建对话失败');
    }
  };

  // 删除对话
  const handleDeleteChat = async (chatId: number) => {
    try {
      const res = await deleteChatUsingDelete({ 
        chatId, 
        userId: 1 // 临时使用用户ID 1
      });
      
      if (res.success) {
        message.success('删除对话成功');
        // 如果删除的是当前对话，则清空当前对话
        if (currentChat?.id === chatId) {
          setCurrentChat(null);
          setMessages([]);
        }
        await fetchChats();
      } else {
        message.error(res.message || '删除对话失败');
      }
    } catch (error) {
      console.error('删除对话失败:', error);
      message.error('删除对话失败');
    }
  };

  // 切换对话
  const handleSwitchChat = async (chat: API.ChatVO) => {
    setCurrentChat(chat);
    await fetchMessages(chat.id!);
  };

  // 发送消息
  const handleSend = async () => {
    if (!inputValue.trim() || !currentChat) return;
    
    // 添加用户消息到界面
    const userMessage: API.MessageVO = {
      content: inputValue,
      senderType: 'user',
      chatId: currentChat.id
    };
    
    const newMessages = [...messages, userMessage];
    setMessages(newMessages);
    setInputValue('');
    setLoading(true);
    
    try {
      // 保存用户消息
      const saveRes = await saveMessageUsingPost(
        { userId: 1 },
        {
          chatId: currentChat.id,
          content: inputValue,
          role: 'user'
        }
      );
      
      if (!saveRes.success) {
        message.error(saveRes.message || '保存消息失败');
        setLoading(false);
        return;
      }
      
      // 发送消息给AI
      const aiResponseMessage: API.MessageVO = {
        content: '',
        senderType: 'assistant',
        chatId: currentChat.id
      };
      
      // 添加AI消息占位
      setMessages([...newMessages, aiResponseMessage]);
      
      // 发起AI对话请求
      const aiRes = await sendMessageUsingPost(
        { userId: 1 },
        {
          messages: [
            ...newMessages.map(msg => ({
              content: msg.content,
              role: msg.senderType === 'user' ? 'user' : 'assistant'
            })),
            { content: inputValue, role: 'user' }
          ],
          model: 'gpt-4', // 默认模型
          stream: true
        }
      );
      
      // 处理流式响应（简化处理）
      // 实际项目中需要处理SSE流式数据
      const aiMessage: API.MessageVO = {
        content: '这是AI的回复内容',
        senderType: 'assistant',
        chatId: currentChat.id
      };
      
      // 保存AI消息
      await saveMessageUsingPost(
        { userId: 1 },
        {
          chatId: currentChat.id,
          content: aiMessage.content,
          role: 'assistant'
        }
      );
      
      // 更新消息列表
      setMessages([...newMessages, aiMessage]);
      setLoading(false);
    } catch (error) {
      console.error('发送消息失败:', error);
      message.error('发送消息失败');
      setLoading(false);
    }
  };

  // 退出登录
  const handleLogout = () => {
    navigate('/login');
  };

  // 初始化获取对话列表
  useEffect(() => {
    fetchChats();
  }, []);

  // 当前对话变化时获取消息
  useEffect(() => {
    if (currentChat) {
      fetchMessages(currentChat.id!);
    }
  }, [currentChat]);

  // 滚动到底部
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  const userMenuItems: MenuProps['items'] = [
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
      onClick: handleLogout
    }
  ];

  return (
    <Layout style={{ height: '100vh' }}>
      {/* 侧边栏 */}
      <Sider width={250} theme="light" style={{ 
        background: '#fff', 
        borderRight: '1px solid #f0f0f0',
        height: '100vh',
        overflow: 'auto'
      }}>
        <div style={{ padding: 16, borderBottom: '1px solid #f0f0f0' }}>
          <Title level={4} style={{ margin: 0, color: '#10A37F' }}>
            <MessageOutlined /> AIChat
          </Title>
        </div>
        <div style={{ padding: 16 }}>
          <Button 
            type="primary" 
            style={{ width: '100%', backgroundColor: '#10A37F', marginBottom: 16 }}
            icon={<PlusOutlined />}
            onClick={handleCreateChat}
          >
            新建对话
          </Button>
          
          {/* 历史会话列表 */}
          <List
            dataSource={chats}
            renderItem={chat => (
              <List.Item
                actions={[
                  <Button 
                    type="text" 
                    icon={<DeleteOutlined />} 
                    onClick={() => chat.id && handleDeleteChat(chat.id)}
                    size="small"
                  />
                ]}
                style={{
                  cursor: 'pointer',
                  backgroundColor: currentChat?.id === chat.id ? '#e6f4ff' : 'transparent',
                  borderRadius: 4,
                  padding: '4px 8px'
                }}
                onClick={() => handleSwitchChat(chat)}
              >
                <div style={{ 
                  whiteSpace: 'nowrap', 
                  overflow: 'hidden', 
                  textOverflow: 'ellipsis',
                  width: '100%'
                }}>
                  {chat.title}
                </div>
              </List.Item>
            )}
          />
        </div>
      </Sider>

      {/* 主内容区域 */}
      <Layout>
        {/* 顶部栏 */}
        <Header style={{ 
          background: '#fff', 
          padding: '0 16px', 
          borderBottom: '1px solid #f0f0f0',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between'
        }}>
          <div>
            <Select defaultValue="gpt-4" style={{ width: 180 }}>
              <Select.Option value="gpt-4">GPT-4</Select.Option>
              <Select.Option value="gpt-3.5-turbo">GPT-3.5 Turbo</Select.Option>
              <Select.Option value="claude-2">Claude 2</Select.Option>
            </Select>
          </div>
          <div>
            <Dropdown menu={{ items: userMenuItems }} placement="bottomRight">
              <Avatar icon={<UserOutlined />} style={{ cursor: 'pointer' }} />
            </Dropdown>
          </div>
        </Header>

        {/* 聊天内容区域 */}
        <Content style={{ 
          padding: '16px', 
          background: '#f0f2f5',
          height: 'calc(100vh - 120px)',
          overflow: 'auto'
        }}>
          {currentChat ? (
            <div style={{ 
              background: '#fff', 
              borderRadius: 8, 
              height: '100%',
              display: 'flex',
              flexDirection: 'column'
            }}>
              {/* 消息列表 */}
              <div style={{ 
                flex: 1, 
                overflowY: 'auto', 
                padding: '16px' 
              }}>
                <List
                  dataSource={messages}
                  renderItem={msg => (
                    <div style={{ 
                      textAlign: msg.senderType === 'user' ? 'right' : 'left',
                      marginBottom: 16
                    }}>
                      <div style={{
                        display: 'inline-block',
                        padding: '8px 12px',
                        borderRadius: 8,
                        backgroundColor: msg.senderType === 'user' ? '#10A37F' : '#f0f0f0',
                        color: msg.senderType === 'user' ? '#fff' : '#000',
                        maxWidth: '80%',
                        wordBreak: 'break-word'
                      }}>
                        {msg.content}
                      </div>
                    </div>
                  )}
                />
                {loading && (
                  <div style={{ textAlign: 'left', marginBottom: 16 }}>
                    <div style={{
                      display: 'inline-block',
                      padding: '8px 12px',
                      borderRadius: 8,
                      backgroundColor: '#f0f0f0',
                      color: '#000',
                      maxWidth: '80%',
                      wordBreak: 'break-word'
                    }}>
                      AI正在思考中...
                    </div>
                  </div>
                )}
                <div ref={messagesEndRef} />
              </div>
              
              {/* 输入区域 */}
              <div style={{ 
                padding: '16px', 
                borderTop: '1px solid #f0f0f0'
              }}>
                <Input.TextArea 
                  placeholder="输入消息..." 
                  autoSize={{ minRows: 3, maxRows: 6 }}
                  style={{ resize: 'none', borderRadius: 8 }}
                  value={inputValue}
                  onChange={e => setInputValue(e.target.value)}
                  onPressEnter={(e) => {
                    if (e.shiftKey) return; // Shift+Enter 换行
                    e.preventDefault();
                    handleSend();
                  }}
                />
                <div style={{ 
                  display: 'flex', 
                  justifyContent: 'flex-end', 
                  marginTop: 12 
                }}>
                  <Button 
                    type="primary" 
                    style={{ backgroundColor: '#10A37F' }}
                    icon={<SendOutlined />}
                    onClick={handleSend}
                    loading={loading}
                    disabled={!inputValue.trim() || !currentChat}
                  >
                    发送
                  </Button>
                </div>
              </div>
            </div>
          ) : (
            <Card title="欢迎使用AIChat" style={{ height: '100%' }}>
              <p>请创建一个新的对话或选择一个现有对话开始聊天。</p>
              <Button type="primary" onClick={handleCreateChat}>
                创建新对话
              </Button>
            </Card>
          )}
        </Content>
      </Layout>
    </Layout>
  );
};

export default ChatPage;