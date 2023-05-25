import request from '@/utils/request'

// 查询用户日报表列表
export function listUserReport(query) {
  return request({
    url: '/business/userReport/list',
    method: 'get',
    params: query
  })
}

// 查询用户日报表详细
export function getUserReport(id) {
  return request({
    url: '/business/userReport/' + id,
    method: 'get'
  })
}

// 新增用户日报表
export function addUserReport(data) {
  return request({
    url: '/business/userReport',
    method: 'post',
    data: data
  })
}

// 修改用户日报表
export function updateUserReport(data) {
  return request({
    url: '/business/userReport',
    method: 'put',
    data: data
  })
}

// 删除用户日报表
export function delUserReport(id) {
  return request({
    url: '/business/userReport/' + id,
    method: 'delete'
  })
}
