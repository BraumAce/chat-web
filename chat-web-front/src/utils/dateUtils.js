/**
 * 日期工具函数
 */

/**
 * 格式化日期为YYYY-MM-DD格式
 * 
 * @param {Date|string} date 日期对象或日期字符串
 * @returns {string} 格式化后的日期字符串
 */
export const formatDate = (date) => {
  const d = new Date(date)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  
  return `${year}-${month}-${day}`
}

/**
 * 格式化日期时间为YYYY-MM-DD HH:MM:SS格式
 * 
 * @param {Date|string} date 日期对象或日期字符串
 * @returns {string} 格式化后的日期时间字符串
 */
export const formatDateTime = (date) => {
  const d = new Date(date)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const hours = String(d.getHours()).padStart(2, '0')
  const minutes = String(d.getMinutes()).padStart(2, '0')
  const seconds = String(d.getSeconds()).padStart(2, '0')
  
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

/**
 * 格式化相对时间（如：刚刚、5分钟前、1小时前等）
 * 
 * @param {Date|string} date 日期对象或日期字符串
 * @returns {string} 格式化后的相对时间字符串
 */
export const formatRelativeTime = (date) => {
  const now = new Date()
  const d = new Date(date)
  const diff = now - d
  
  // 转换为秒
  const seconds = Math.floor(diff / 1000)
  
  if (seconds < 60) {
    return '刚刚'
  }
  
  // 转换为分钟
  const minutes = Math.floor(seconds / 60)
  
  if (minutes < 60) {
    return `${minutes}分钟前`
  }
  
  // 转换为小时
  const hours = Math.floor(minutes / 60)
  
  if (hours < 24) {
    return `${hours}小时前`
  }
  
  // 转换为天
  const days = Math.floor(hours / 24)
  
  if (days < 30) {
    return `${days}天前`
  }
  
  // 如果超过30天，则返回具体日期
  return formatDate(date)
}