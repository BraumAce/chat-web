import axios from 'axios'

// 创建axios实例
const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

/**
 * 消息相关API服务
 */
const messageApi = {
  /**
   * 发送消息
   * 
   * @param {Object} message 消息对象
   * @returns {Promise} 请求Promise
   */
  sendMessage: (message) => {
    return api.post('/messages', message)
  },

  /**
   * 获取用户收到的所有消息
   * 
   * @param {string} userId 用户ID
   * @returns {Promise} 请求Promise
   */
  getReceivedMessages: (userId) => {
    return api.get(`/messages/received/${userId}`)
  },

  /**
   * 获取用户发送的所有消息
   * 
   * @param {string} userId 用户ID
   * @returns {Promise} 请求Promise
   */
  getSentMessages: (userId) => {
    return api.get(`/messages/sent/${userId}`)
  },

  /**
   * 获取两个用户之间的对话
   * 
   * @param {string} user1Id 用户1 ID
   * @param {string} user2Id 用户2 ID
   * @returns {Promise} 请求Promise
   */
  getConversation: (user1Id, user2Id) => {
    return api.get(`/messages/conversation?user1Id=${user1Id}&user2Id=${user2Id}`)
  },

  /**
   * 将消息标记为已读
   * 
   * @param {number} messageId 消息ID
   * @returns {Promise} 请求Promise
   */
  markAsRead: (messageId) => {
    return api.put(`/messages/${messageId}/read`)
  }
}

export default messageApi