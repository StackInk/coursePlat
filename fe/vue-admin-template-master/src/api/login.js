import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/aclservice/user/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/aclservice/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/aclservice/user/logout',
    method: 'post'
  })
}

// 获取菜单权限数据
export function getMenu() {
  return request({
    url: '/aclservice/user/menu',
    method: 'get'
  })
}
