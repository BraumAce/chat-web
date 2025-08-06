import axios, {type AxiosRequestConfig, type AxiosResponse, AxiosError } from 'axios';

// 创建 axios 实例
const request = axios.create({
  baseURL: '/api', // 使用相对路径，配合Vite代理
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器
request.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    // 可以在这里添加认证 token 等
    // const token = localStorage.getItem('token');
    // if (token && config.headers) {
    //   config.headers.Authorization = `Bearer ${token}`;
    // }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse) => {
    // 对响应数据做点什么
    return response.data;
  },
  (error: AxiosError) => {
    // 对响应错误做点什么
    if (error.response?.status === 401) {
      // 处理未授权错误，如重定向到登录页
      // window.location.href = '/login';
    }

    return Promise.reject(error);
  }
);

export default request;