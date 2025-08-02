import { useState } from 'react'
import { Form, Input, Button, Card, message } from 'antd'
import { UserOutlined, LockOutlined } from '@ant-design/icons'
import { useNavigate } from 'react-router-dom'

/**
 * 登录页面组件
 * 
 * @param {Object} props 组件属性
 * @param {Function} props.onLogin 登录成功回调函数
 * @returns {JSX.Element} 登录页面组件
 */
const LoginPage = ({ onLogin }) => {
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  /**
   * 处理表单提交
   * 
   * @param {Object} values 表单值
   */
  const handleSubmit = (values) => {
    setLoading(true)
    
    // 模拟登录请求
    setTimeout(() => {
      setLoading(false)
      // 这里应该调用实际的登录API
      // 目前使用模拟数据，假设用户名为admin，密码为123456时登录成功
      if (values.username === 'admin' && values.password === '123456') {
        message.success('登录成功')
        // 存储用户信息到本地存储
        localStorage.setItem('user', JSON.stringify({ id: 'user1', username: values.username }))
        // 调用登录成功回调
        onLogin()
        // 导航到聊天页面
        navigate('/chat')
      } else {
        message.error('用户名或密码错误')
      }
    }, 1000)
  }

  return (
    <div style={{ width: '100%', maxWidth: '400px' }}>
      <Card title="用户登录" bordered={false}>
        <Form
          name="login"
          initialValues={{ remember: true }}
          onFinish={handleSubmit}
          autoComplete="off"
          layout="vertical"
        >
          <Form.Item
            name="username"
            rules={[{ required: true, message: '请输入用户名!' }]}
          >
            <Input prefix={<UserOutlined />} placeholder="用户名" />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[{ required: true, message: '请输入密码!' }]}
          >
            <Input.Password prefix={<LockOutlined />} placeholder="密码" />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" loading={loading} block>
              登录
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  )
}

export default LoginPage