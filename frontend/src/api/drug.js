import request from '@/utils/request'

// 分页查询药品
export const getDrugPage = (params) => {
  return request.get('/drug/page', { params })
}

// 获取药品详情
export const getDrugDetail = (id) => {
  return request.get(`/drug/${id}`)
}

// 添加药品
export const addDrug = (data) => {
  return request.post('/drug', data)
}

// 修改药品
export const updateDrug = (id, data) => {
  return request.put(`/drug/${id}`, data)
}

// 删除药品
export const deleteDrug = (id) => {
  return request.delete(`/drug/${id}`)
}

// 收藏/取消收藏
export const toggleFavorite = (id, isFavorite) => {
  return request.put(`/drug/${id}/favorite`, { isFavorite })
}

// 归档药品
export const archiveDrug = (id) => {
  return request.put(`/drug/${id}/archive`)
}

// 调整库存
export const changeStock = (id, data) => {
  return request.put(`/drug/${id}/stock`, data)
}

// 上传图片
export const uploadImage = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/drug/upload', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// 获取即将过期药品
export const getExpiringDrugs = (days = 7) => {
  return request.get('/drug/expiring', { params: { days } })
}

// 获取已过期药品
export const getExpiredDrugs = () => {
  return request.get('/drug/expired')
}

// 批量删除
export const batchDeleteDrugs = (ids) => {
  return request.delete('/drug/batch', { data: { ids } })
}
