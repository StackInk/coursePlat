import request from '@/utils/request'

const api_name = '/aclservice/user'

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

  save(user) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: user
    })
  },

  updateById(user) {
    return request({
      url: `${api_name}`,
      method: 'put',
      data: user
    })
  },
  getAssign(userId) {
    return request({
      url: `${api_name}/role/${userId}`,
      method: 'get'
    })
  },
  saveAssign(userId, roleId) {
    return request({
      url: `${api_name}/role`,
      method: 'post',
      params: { userId, roleId }
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
      url: `${api_name}/batchRemove`,
      method: 'delete',
      data: idList
    })
  }
}
