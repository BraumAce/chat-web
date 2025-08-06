import React, { useState } from 'react';
import { LockOutlined, UserOutlined, GoogleOutlined, GithubOutlined } from '@ant-design/icons';
import { Button, Checkbox, Form, Input, Card, message, Layout } from 'antd';
import { useNavigate } from 'react-router-dom';
import { loginUsingPost } from '../api/userController';

const { Content } = Layout;

interface LoginFormValues {
  username: string;
  password: string;
  remember?: boolean;
}

const LoginPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values: LoginFormValues) => {
    console.log('Received values of form: ', values);
    setLoading(true);
    
    try {
      // 调用登录API
      const res = await loginUsingPost({
        username: values.username,
        password: values.password,
      });
      
      if (res.success) {
        message.success('登录成功');
        // 登录成功后跳转到聊天室页面
        navigate('/chat');
      } else {
        message.error(res.message || '登录失败');
      }
    } catch (error) {
      message.error('登录失败，请检查用户名和密码');
      console.error('Login error:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Layout style={{ minHeight: '100vh', background: '#f0f2f5' }}>
      <Content style={{ 
        display: 'flex', 
        justifyContent: 'center', 
        alignItems: 'center',
        padding: 0,
        margin: 0
      }}>
        <Card style={{ 
          width: 400, 
          boxShadow: '0 1px 3px rgba(0,0,0,0.12)',
          borderRadius: 8
        }}>
          <div style={{ textAlign: 'center', marginBottom: 30 }}>
            <h1 style={{ color: '#10A37F', fontSize: 28, fontWeight: 'bold', margin: 0 }}>AIChat</h1>
            <p style={{ color: '#666', margin: 8 }}>登录您的账户</p>
          </div>
          
          <Form
            name="normal_login"
            initialValues={{ remember: true }}
            onFinish={onFinish}
          >
            <Form.Item
              name="username"
              rules={[
                { 
                  required: true, 
                  message: '请输入您的用户名!' 
                }
              ]}
            >
              <Input 
                prefix={<UserOutlined className="site-form-item-icon" />} 
                placeholder="用户名" 
                size="large"
                style={{ borderRadius: 6 }}
              />
            </Form.Item>
            
            <Form.Item
              name="password"
              rules={[
                { 
                  required: true, 
                  message: '请输入您的密码!' 
                },
                { 
                  min: 6, 
                  message: '密码至少6位字符!' 
                }
              ]}
            >
              <Input
                prefix={<LockOutlined className="site-form-item-icon" />}
                type="password"
                placeholder="密码"
                size="large"
                style={{ borderRadius: 6 }}
              />
            </Form.Item>
            
            <Form.Item>
              <Form.Item name="remember" valuePropName="checked" noStyle>
                <Checkbox>记住我</Checkbox>
              </Form.Item>

              <a style={{ float: 'right' }} href="/forgot-password">
                忘记密码
              </a>
            </Form.Item>

            <Form.Item>
              <Button 
                type="primary" 
                htmlType="submit" 
                style={{ 
                  width: '100%', 
                  backgroundColor: '#10A37F',
                  borderRadius: 6
                }}
                size="large"
                loading={loading}
              >
                登录
              </Button>
              
              <div style={{ marginTop: 20, textAlign: 'center' }}>
                还没有账户？ <a href="/register">立即注册</a>
              </div>
            </Form.Item>
          </Form>
          
          {/* 第三方登录预留 */}
          <div style={{ marginTop: 20 }}>
            <div style={{ textAlign: 'center', margin: '10px 0' }}>
              <span style={{ color: '#ccc' }}>其他登录方式</span>
            </div>
            <div style={{ display: 'flex', justifyContent: 'center', gap: 20 }}>
              <Button shape="circle" icon={<GoogleOutlined />} style={{ color: '#DB4437', borderColor: '#DB4437' }} />
              <Button shape="circle" icon={<GoogleOutlined />} style={{ color: '#4285F4', borderColor: '#4285F4' }} />
              <Button shape="circle" icon={<GithubOutlined />} style={{ color: '#24292E', borderColor: '#24292E' }} />
            </div>
          </div>
        </Card>
      </Content>
    </Layout>
  );
};

export default LoginPage;