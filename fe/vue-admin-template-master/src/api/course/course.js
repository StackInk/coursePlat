import request from '@/utils/request'

const api_name = '/courseservice/course'

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
  getCourseById(courseId) {
    return request({
      url: `${api_name}/${courseId}`,
      method: 'get'
    })
  },
  saveCourse(course) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: course
    })
  },
  updateCourse(course) {
    return request({
      url: `${api_name}`,
      method: 'put',
      data: course
    })
  }
}
