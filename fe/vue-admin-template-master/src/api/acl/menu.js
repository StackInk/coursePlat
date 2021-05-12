import request from '@/utils/request'

const api_name = '/aclservice/permission'

export default {
  getNestedTreeList() {
    return request({
      url: `${api_name}`,
      method: 'get'
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'delete'
    })
  },
  saveLevelOne(menu) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: menu
    })
  },
  update(menu) {
    return request({
      url: `${api_name}`,
      method: 'put',
      data: menu
    })
  },
  toAssign(roleId) {
    return request({
      url: `${api_name}/role/${roleId}`,
      method: 'get'
    })
  },
  doAssign(roleId, permissionIds) {
    return request({
      url: `${api_name}/assign`,
      method: 'post',
      params: { roleId, permissionIds }
    })
  }
}
