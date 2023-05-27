import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listUser(query) {
  return request({
    url: '/business/user/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getUser(id) {
  return request({
    url: '/business/user/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addUser(data) {
  return request({
    url: '/business/user',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateUser(data) {
  return request({
    url: '/business/user',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delUser(id) {
  return request({
    url: '/business/user/' + id,
    method: 'delete'
  })
}

// 增减余额
export function balanceUser(data) {
  return request({
    url: '/business/user/optMoney',
    method: 'post',
    data: data
  })
}

// 重置余额
export function resetBalance(data) {
  return request({
    url: '/business/user/resetBalance',
    method: 'post',
    data: data
  })
}

// 获取上级
export function getAgentUser(query) {
  return request({
    url: '/business/user/getAgentUser',
    method: 'get',
    params: query
  })
}
//获取下级
export function getSubUser(query) {
  return request({
    url: '/business/user/getSubUser',
    method: 'get',
    params: query
  })
}