import request from '@/utils/request'

const api_name = '/aclservice/teacher'

export default {
  getPageList(page, limit) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get'
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'delete'
    })
  },
  getTeacherById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  },
  saveTeacher(teacher) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: teacher
    })
  },
  updateTeacher(teacher) {
    return request({
      url: `${api_name}`,
      method: 'put',
      data: teacher
    })
  },
  getTeachersByCourseId(courseId) {
    return request({
      url: `${api_name}/course/${courseId}`,
      method: 'get'
    })
  },
  getTeacherByName(name) {
    return request({
      url: `${api_name}`,
      method: 'get',
      param: name
    })
  }
}
