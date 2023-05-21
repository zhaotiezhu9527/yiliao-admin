import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listParamter(query) {
  return request({
    url: '/business/paramter/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getParamter(id) {
  return request({
    url: '/business/paramter/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addParamter(data) {
  return request({
    url: '/business/paramter',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateParamter(data) {
  return request({
    url: '/business/paramter',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delParamter(id) {
  return request({
    url: '/business/paramter/' + id,
    method: 'delete'
  })
}
