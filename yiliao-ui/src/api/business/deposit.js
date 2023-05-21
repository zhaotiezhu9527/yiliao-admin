import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listDeposit(query) {
  return request({
    url: '/business/deposit/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getDeposit(id) {
  return request({
    url: '/business/deposit/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addDeposit(data) {
  return request({
    url: '/business/deposit',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateDeposit(data) {
  return request({
    url: '/business/deposit',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delDeposit(id) {
  return request({
    url: '/business/deposit/' + id,
    method: 'delete'
  })
}
