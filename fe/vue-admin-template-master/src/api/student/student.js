import request from '@/utils/request'

const api_name = '/courseservice/student'

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
  getStudentById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  },
  saveStudent(student) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: student
    })
  },
  updateStudent(student) {
    return request({
      url: `${api_name}`,
      method: 'put',
      data: student
    })
  },
  getStudentsByCourseId(courseId) {
    return request({
      url: `${api_name}/course/${courseId}`,
      method: 'get'
    })
  },
  getPageListByName(page, limit, name) {
    return request({
      url: `${api_name}/name/${page}/${limit}/?name=${name}`,
      method: 'get'
    })
  }
}
