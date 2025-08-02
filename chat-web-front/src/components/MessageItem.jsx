import { Avatar, Typography } from 'antd'
import { UserOutlined } from '@ant-design/icons'

const { Text } = Typography

/**
 * 消息列表项组件
 * 
 * @param {Object} props 组件属性
 * @param {Object} props.message 消息对象
 * @param {boolean} props.isSelf 是否为自己发送的消息
 * @param {Function} props.formatTime 时间格式化函数
 * @returns {JSX.Element} 消息列表项组件
 */
const MessageItem = ({ message, isSelf, formatTime }) => {
  return (
    <div style={{
      display: 'flex',
      flexDirection: isSelf ? 'row-reverse' : 'row',
      width: '100%',
      marginBottom: '16px'
    }}>
      <Avatar 
        icon={<UserOutlined />} 
        style={{ 
          background: isSelf ? '#1890ff' : '#f56a00',
          flexShrink: 0
        }} 
      />
      <div style={{
        maxWidth: '70%',
        margin: isSelf ? '0 16px 0 0' : '0 0 0 16px',
        padding: '8px 12px',
        background: isSelf ? '#e6f7ff' : '#f5f5f5',
        borderRadius: '4px',
        position: 'relative',
        wordBreak: 'break-word'
      }}>
        <Text>{message.content}</Text>
        <div style={{ 
          fontSize: '12px', 
          color: '#999', 
          marginTop: '4px', 
          textAlign: isSelf ? 'right' : 'left' 
        }}>
          {formatTime(message.sentTime)}
        </div>
      </div>
    </div>
  )
}

export default MessageItem