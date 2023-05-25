import request from '@/utils/request'

// 查询日报表列表
export function listDayReport(query) {
  return request({
    url: '/business/dayReport/list',
    method: 'get',
    params: query
  })
}

// 查询日报表详细
export function getDayReport(id) {
  return request({
    url: '/business/dayReport/' + id,
    method: 'get'
  })
}

// 新增日报表
export function addDayReport(data) {
  return request({
    url: '/business/dayReport',
    method: 'post',
    data: data
  })
}

// 修改日报表
export function updateDayReport(data) {
  return request({
    url: '/business/dayReport',
    method: 'put',
    data: data
  })
}

// 删除日报表
export function delDayReport(id) {
  return request({
    url: '/business/dayReport/' + id,
    method: 'delete'
  })
}

// 删除日报表
export function allReport(id) {
  return request({
    url: '/business/dayReport/allReport',
    method: 'get'
  })
}
