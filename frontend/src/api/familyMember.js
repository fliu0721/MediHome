import request from '@/utils/request'

// 获取家庭成员列表
export const getFamilyMemberList = () => {
  return request.get('/family-member/list')
}

// 添加家庭成员
export const addFamilyMember = (data) => {
  return request.post('/family-member', data)
}

// 修改家庭成员
export const updateFamilyMember = (id, data) => {
  return request.put(`/family-member/${id}`, data)
}

// 删除家庭成员
export const deleteFamilyMember = (id) => {
  return request.delete(`/family-member/${id}`)
}

// 设置默认成员
export const setDefaultMember = (id) => {
  return request.put(`/family-member/${id}/default`)
}
