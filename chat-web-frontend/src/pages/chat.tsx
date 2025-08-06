import React from 'react';
import { 
  Layout, 
  Card, 
  Typography, 
  Button, 
  Select, 
  Avatar, 
  Input 
} from 'antd';
import { 
  MessageOutlined, 
  PlusOutlined, 
  SendOutlined, 
  UserOutlined 
} from '@ant-design/icons';

const { Header, Sider, Content } = Layout;
const { Title } = Typography;

const ChatPage: React.FC = () => {
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
          >
            新建对话
          </Button>
          {/* 历史会话列表将在这里实现 */}
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
            <Avatar icon={<UserOutlined />} />
          </div>
        </Header>

        {/* 聊天内容区域 */}
        <Content style={{ 
          padding: '16px', 
          background: '#f0f2f5',
          height: 'calc(100vh - 120px)',
          overflow: 'auto'
        }}>
          <Card title="聊天室" style={{ height: '100%' }}>
            <p>欢迎来到聊天室！这是登录后的主页面。</p>
            <p>后续我们将在这里实现聊天功能。</p>
          </Card>
        </Content>

        {/* 输入区域 */}
        <div style={{ 
          padding: '16px', 
          background: '#fff',
          borderTop: '1px solid #f0f0f0'
        }}>
          <Input.TextArea 
            placeholder="输入消息..." 
            autoSize={{ minRows: 3, maxRows: 6 }}
            style={{ resize: 'none', borderRadius: 8 }}
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
            >
              发送
            </Button>
          </div>
        </div>
      </Layout>
    </Layout>
  );
};

export default ChatPage;