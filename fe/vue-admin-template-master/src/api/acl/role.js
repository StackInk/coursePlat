import request from '@/utils/request'

const api_name = '/aclservice/role'

export default {

  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj // url查询字符串或表单键值对
    })
  },
  getById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  },

  save(role) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: role
    })
  },

  updateById(role) {
    return request({
      url: `${api_name}`,
      method: 'put',
      data: role
    })
  },
  getAssign(roleId) {
    return request({
      url: `${api_name}/role/${roleId}`,
      method: 'get'
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'delete'
    })
  },
  removeRows(idList) {
    return request({
      url: `${api_name}`,
      method: 'delete',
      data: idList
    })
  }

}
