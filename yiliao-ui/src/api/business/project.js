import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listProject(query) {
  return request({
    url: '/business/project/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getProject(id) {
  return request({
    url: '/business/project/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addProject(data) {
  return request({
    url: '/business/project',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateProject(data) {
  return request({
    url: '/business/project',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delProject(id) {
  return request({
    url: '/business/project/' + id,
    method: 'delete'
  })
}
