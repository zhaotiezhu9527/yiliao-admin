import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listWithdraw(query) {
  return request({
    url: '/business/withdraw/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getWithdraw(id) {
  return request({
    url: '/business/withdraw/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addWithdraw(data) {
  return request({
    url: '/business/withdraw',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateWithdraw(data) {
  return request({
    url: '/business/withdraw',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delWithdraw(id) {
  return request({
    url: '/business/withdraw/' + id,
    method: 'delete'
  })
}
