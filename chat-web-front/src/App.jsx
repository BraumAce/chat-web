import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { Layout } from 'antd'
import './App.css'

// 导入页面组件
import ChatPage from './pages/ChatPage'
import LoginPage from './pages/LoginPage'

const { Header, Content, Footer } = Layout

/**
 * 应用程序主组件
 * 
 * @returns {JSX.Element} 应用程序组件
 */
function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false)

  return (
    <Router>
      <Layout className="layout">
        <Header className="header">
          <div className="logo">聊天Web应用</div>
        </Header>
        <Content className="content">
          <Routes>
            <Route path="/" element={isLoggedIn ? <ChatPage /> : <LoginPage onLogin={() => setIsLoggedIn(true)} />} />
            <Route path="/login" element={<LoginPage onLogin={() => setIsLoggedIn(true)} />} />
            <Route path="/chat" element={<ChatPage />} />
          </Routes>
        </Content>
        <Footer className="footer">
          聊天Web应用 ©{new Date().getFullYear()} Created by Yuan
        </Footer>
      </Layout>
    </Router>
  )
}

export default App