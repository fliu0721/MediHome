import request from '@/utils/request'

// 获取分类列表
export const getCategoryList = () => {
  return request.get('/drug-category/list')
}

// 添加分类
export const addCategory = (data) => {
  return request.post('/drug-category', data)
}

// 修改分类
export const updateCategory = (id, data) => {
  return request.put(`/drug-category/${id}`, data)
}

// 删除分类
export const deleteCategory = (id) => {
  return request.delete(`/drug-category/${id}`)
}
