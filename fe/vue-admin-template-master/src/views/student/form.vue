<template>
  <div class="app-container">
    <el-card shadow="hover" class="zl-card">
      <div slot="header" class="clearfix">
        <span>{{ isEdit?'修改学生':'添加学生' }}</span>
      </div>
      <el-form
        ref="adminForm"
        :model="student"
        label-width="150px"
        size="small">
        <el-form-item label="名称：">
          <el-input v-model="student.name" style="width: 250px"/>
        </el-form-item>
        <el-form-item label="绑定登陆账号">
          <!-- TODO 下拉框 -->
          <el-select v-model="student.uid" placeholder="请选择" style="width: 250px">
            <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.username"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="学生院系">
          <el-input v-model="student.department" style="width: 250px"/>
        </el-form-item>
        <el-form-item label="学生专业">
          <el-input v-model="student.major" style="width: 250px"/>
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
import student from '@/api/student/student'
import user from '@/api/acl/user'

const defaultStudent = {
  name: null,
  department: null,
  major: null
}

export default {
  data() {
    return {
      isEdit: false,
      student: Object.assign({}, defaultStudent),
      users: [],
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
      const num = 3
      if (this.$route.params && this.$route.params.id) {
        this.isEdit = true
        this.handleStudentById(this.$route.params.id)
      }
      user.getUserByRoleId(num).then(response => {
        this.users = response.data.users
      })
    },
    handleStudentById() {
      student.getStudentById(this.$route.params.id).then(response => {
        this.student = response.data.student
      })
    },
    handleConfirm() {
      if (this.$route.params && this.$route.params.id) {
        this.updateStudent()
      } else {
        this.saveStudent()
      }
    },
    updateStudent() {
      this.fullscreenLoading = true
      student.updateStudent(this.student).then(response => {
        this.fullscreenLoading = false
        this.$router.push({ 'path': '/student/list' })
      }).catch(response => {
        this.fullscreenLoading = false
        this.$router.push({ 'path': '/student/list' })
      })
    },
    saveStudent() {
      this.fullscreenLoading = true
      const newStudent = this.student
      newStudent.credits = 0
      student.saveStudent(newStudent).then(response => {
        this.fullscreenLoading = false
        this.$router.push({ 'path': '/student/list' })
      }).catch(response => {
        this.fullscreenLoading = false
        this.$router.push({ 'path': '/student/list' })
      })
    },
    handleChange() {

    },
    handleReset() {
      this.$router.push({ path: '/student/list' })
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
