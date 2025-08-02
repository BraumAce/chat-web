import { useState, useEffect, useRef } from 'react'
import { Layout, List, Input, Button, Avatar, Typography, Divider, Select, Badge } from 'antd'
import { SendOutlined, UserOutlined } from '@ant-design/icons'
import { useNavigate } from 'react-router-dom'

const { Content, Sider } = Layout
const { Text } = Typography
const { Option } = Select

/**
 * 聊天页面组件
 * 
 * @returns {JSX.Element} 聊天页面组件
 */
const ChatPage = () => {
  const [messages, setMessages] = useState([])
  const [inputValue, setInputValue] = useState('')
  const [contacts, setContacts] = useState([
    { id: 'user2', name: '用户2', unread: 1 },
    { id: 'user3', name: '用户3', unread: 0 }
  ])
  const [selectedContact, setSelectedContact] = useState(null)
  const messagesEndRef = useRef(null)
  const navigate = useNavigate()
  
  // 获取当前登录用户
  const currentUser = JSON.parse(localStorage.getItem('user') || '{}')
  
  useEffect(() => {
    // 检查用户是否已登录
    if (!currentUser.id) {
      navigate('/login')
      return
    }
    
    // 如果有选中的联系人，加载对话
    if (selectedContact) {
      loadConversation(selectedContact.id)
    }
  }, [selectedContact, navigate, currentUser.id])
  
  // 滚动到最新消息
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages])
  
  /**
   * 加载与指定用户的对话
   * 
   * @param {string} userId 用户ID
   */
  const loadConversation = (userId) => {
    // 这里应该调用实际的API获取对话
    // 目前使用模拟数据
    const mockMessages = [
      {
        id: 1,
        senderId: currentUser.id,
        receiverId: userId,
        content: '你好，这是一条测试消息',
        sentTime: '2023-07-01T10:00:00',
        read: true
      },
      {
        id: 2,
        senderId: userId,
        receiverId: currentUser.id,
        content: '你好，我收到了你的消息',
        sentTime: '2023-07-01T10:05:00',
        read: true
      },
      {
        id: 3,
        senderId: currentUser.id,
        receiverId: userId,
        content: '我们来测试一下聊天功能',
        sentTime: '2023-07-01T10:10:00',
        read: true
      },
      {
        id: 4,
        senderId: userId,
        receiverId: currentUser.id,
        content: '好的，测试看起来不错',
        sentTime: '2023-07-01T10:15:00',
        read: true
      }
    ]
    
    setMessages(mockMessages)
    
    // 将未读消息标记为已读
    const updatedContacts = contacts.map(contact => {
      if (contact.id === userId) {
        return { ...contact, unread: 0 }
      }
      return contact
    })
    setContacts(updatedContacts)
  }
  
  /**
   * 处理消息发送
   */
  const handleSendMessage = () => {
    if (!inputValue.trim() || !selectedContact) return
    
    // 创建新消息
    const newMessage = {
      id: Date.now(),
      senderId: currentUser.id,
      receiverId: selectedContact.id,
      content: inputValue,
      sentTime: new Date().toISOString(),
      read: false
    }
    
    // 更新消息列表
    setMessages([...messages, newMessage])
    setInputValue('')
    
    // 这里应该调用实际的API发送消息
  }
  
  /**
   * 处理联系人选择
   * 
   * @param {string} contactId 联系人ID
   */
  const handleContactSelect = (contactId) => {
    const contact = contacts.find(c => c.id === contactId)
    setSelectedContact(contact)
  }
  
  /**
   * 格式化消息时间
   * 
   * @param {string} isoString ISO格式的时间字符串
   * @returns {string} 格式化后的时间字符串
   */
  const formatMessageTime = (isoString) => {
    const date = new Date(isoString)
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
  }
  
  return (
    <Layout style={{ height: '100%', width: '100%', maxWidth: '1200px', margin: '0 auto' }}>
      <Sider width={250} style={{ background: '#fff', overflowY: 'auto' }}>
        <div style={{ padding: '16px' }}>
          <div style={{ marginBottom: '16px', display: 'flex', alignItems: 'center' }}>
            <Avatar icon={<UserOutlined />} />
            <Text strong style={{ marginLeft: '8px' }}>{currentUser.username || '未登录'}</Text>
          </div>
          <Divider style={{ margin: '8px 0' }} />
          <Select
            style={{ width: '100%' }}
            placeholder="选择联系人"
            onChange={handleContactSelect}
            value={selectedContact?.id}
          >
            {contacts.map(contact => (
              <Option key={contact.id} value={contact.id}>
                <Badge count={contact.unread} size="small">
                  <span style={{ marginRight: '24px' }}>{contact.name}</span>
                </Badge>
              </Option>
            ))}
          </Select>
        </div>
      </Sider>
      <Content style={{ background: '#fff', padding: '0' }}>
        <div style={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
          <div style={{ padding: '16px', borderBottom: '1px solid #f0f0f0' }}>
            <Text strong>{selectedContact ? selectedContact.name : '请选择联系人'}</Text>
          </div>
          
          <div style={{ flex: 1, padding: '16px', overflowY: 'auto' }}>
            {selectedContact ? (
              <List
                dataSource={messages}
                renderItem={item => {
                  const isSelf = item.senderId === currentUser.id
                  return (
                    <List.Item style={{ padding: '8px 0' }}>
                      <div style={{
                        display: 'flex',
                        flexDirection: isSelf ? 'row-reverse' : 'row',
                        width: '100%'
                      }}>
                        <Avatar icon={<UserOutlined />} style={{ background: isSelf ? '#1890ff' : '#f56a00' }} />
                        <div style={{
                          maxWidth: '70%',
                          margin: isSelf ? '0 16px 0 0' : '0 0 0 16px',
                          padding: '8px 12px',
                          background: isSelf ? '#e6f7ff' : '#f5f5f5',
                          borderRadius: '4px',
                          position: 'relative'
                        }}>
                          <Text>{item.content}</Text>
                          <div style={{ fontSize: '12px', color: '#999', marginTop: '4px', textAlign: isSelf ? 'right' : 'left' }}>
                            {formatMessageTime(item.sentTime)}
                          </div>
                        </div>
                      </div>
                    </List.Item>
                  )
                }}
              />
            ) : (
              <div style={{ textAlign: 'center', marginTop: '100px' }}>
                <Text type="secondary">请选择一个联系人开始聊天</Text>
              </div>
            )}
            <div ref={messagesEndRef} />
          </div>
          
          <div style={{ padding: '16px', borderTop: '1px solid #f0f0f0', display: 'flex' }}>
            <Input
              placeholder={selectedContact ? "输入消息..." : "请先选择联系人"}
              value={inputValue}
              onChange={e => setInputValue(e.target.value)}
              onPressEnter={handleSendMessage}
              disabled={!selectedContact}
              style={{ flex: 1, marginRight: '8px' }}
            />
            <Button
              type="primary"
              icon={<SendOutlined />}
              onClick={handleSendMessage}
              disabled={!selectedContact || !inputValue.trim()}
            >
              发送
            </Button>
          </div>
        </div>
      </Content>
    </Layout>
  )
}

export default ChatPage