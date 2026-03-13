import request from '@/utils/request'

// 用户注册
export const register = (data) => {
  return request.post('/user/register', data)
}

// 用户登录
export const login = (data) => {
  return request.post('/user/login', data)
}

// 获取验证码
export const getVerifyCode = (phone) => {
  return request.get('/user/verify-code', { params: { phone } })
}

// 获取当前用户信息
export const getUserInfo = () => {
  return request.get('/user/info')
}

// 更新用户信息
export const updateUserInfo = (data) => {
  return request.put('/user/info', data)
}

// 修改密码
export const updatePassword = (data) => {
  return request.put('/user/password', data)
}
