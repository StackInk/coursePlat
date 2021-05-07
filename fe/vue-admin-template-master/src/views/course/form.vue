<template>
  <div class="app-container">
    <el-card shadow="hover" class="zl-card">
      <div slot="header" class="clearfix">
        <span>{{ isEdit?'修改课程':'添加课程' }}</span>
      </div>
      <el-form
        ref="adminForm"
        :model="course"
        label-width="150px"
        size="small">
        <el-form-item label="名称：">
          <el-input v-model="course.name" style="width: 250px"/>
        </el-form-item>
        <el-form-item label="课程类型：">
          <!-- TODO 下拉框 -->
          <el-select v-model="course.type" placeholder="请选择" style="width: 250px">
            <el-option
              v-for="item in types"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="上课地点：">
          <el-input v-model="course.place" style="width: 250px"/>
        </el-form-item>
        <el-form-item label="库存：">
          <!-- TODO 数字框 -->
          <el-input-number v-model="course.stock" :min="30" :max="60" label="课程库存" style="width: 250px" @change="handleChange"/>
        </el-form-item>
        <el-form-item label="授课教师：">
          <!-- TODO 下拉框 -->
          <el-select
            v-model="course.teachers"
            multiple
            filterable
            allow-create
            default-first-option
            style="width: 250px"
            placeholder="请选择授课教师">
            <el-option
              v-for="item in teachers"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="开始上课时间：">
          <!-- TODO 时间框 -->
          <el-date-picker
            v-model="course.startTime"
            type="datetime"
            style="width: 250px"
            placeholder="选择日期时间"/>
        </el-form-item>
        <el-form-item label="课程上课时间：">
          <el-input
            v-model="course.time"
            style="width: 250px"/>
        </el-form-item>
      </el-form>
      <div class="zl-button">
        <el-button type="warning" size="small" @click="handleReset()">取 消</el-button>
        <el-button v-loading.fullscreen.lock="fullscreenLoading" type="success" size="small" @click="handleConfirm()">确 定</el-button>
      </div>
    </el-card>

  </div>
</template>

<script>
import teacher from '@/api/teacher/teacher'
import course from '@/api/course/course'

const defaultCourse = {
  name: null,
  type: 1,
  place: null,
  stock: 0,
  teachers: null,
  startTime: null,
  time: null
}

const defaultType = [
  { id: 1, name: '智慧树' },
  { id: 2, name: '慕课' },
  { id: 3, name: '线下' }
]

export default {
  data() {
    return {
      isEdit: false,
      course: Object.assign({}, defaultCourse),
      teachers: [],
      types: Object.assign({}, defaultType),
      fullscreenLoading: false
    }
  },
  watch: {
    $route(to, from) {
      this.init()
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.isEdit = true
        this.handleCourseById(this.$route.params.id)
      }
      this.listTeachers()
    },
    listTeachers() {
      teacher.listTeacher().then(response => {
        this.teachers = response.data.teachers
      })
    },
    handleCourseById() {
      course.getCourseById(this.$route.params.id).then(response => {
        this.course = response.data.course
      })
    },
    handleConfirm() {
      if (this.$route.params && this.$route.params.id) {
        this.updateCourse()
      } else {
        this.saveCourse()
      }
    },
    updateCourse() {
      this.fullscreenLoading = true
      course.updateCourse(this.course).then(response => {
        this.fullscreenLoading = false
      }).catch(response => {
        this.fullscreenLoading = false
      })
    },
    saveCourse() {
      this.fullscreenLoading = true
      course.saveCourse(this.course).then(response => {
        this.fullscreenLoading = false
      }).catch(response => {
        this.fullscreenLoading = false
      })
    },
    handleChange() {

    },
    handleReset() {
      this.$router.push({ path: '/course/list' })
    }

  }
}
</script>

<style  scoped>
	.zl-button {
		display: flex;
		justify-content: center;
		align-items: center;
	}
</style>
