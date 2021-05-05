<template>
  <div class="app-container">
    <el-card shadow="hover" class="zl-card">
      <div slot="header" class="clearfix">
        <span>{{ isEdit?'修改教师':'添加教师' }}</span>
      </div>
      <el-form
        ref="adminForm"
        :model="teacher"
        label-width="150px"
        size="small">
        <el-form-item label="名称：">
          <el-input v-model="teacher.name" style="width: 250px"/>
        </el-form-item>
        <el-form-item label="教师经历：">
          <el-input
            v-model="teacher.description"
            :rows="5"
            type="textarea"
            style="width: 250px"/>
        </el-form-item>
        <el-form-item label="教师职称">
          <!-- TODO 下拉框 -->
          <el-select v-model="teacher.rank" placeholder="请选择" style="width: 250px">
            <el-option
              v-for="item in rankData"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div class="zl-button">
        <el-button type="warning" size="small" @click="handleReset()">取 消</el-button>
        <el-button type="success" size="small" @click="handleConfirm()">确 定</el-button>
      </div>
    </el-card>

  </div>
</template>

<script>
import teacher from '@/api/teacher/teacher'

const defaultTeacher = {
  name: null,
  description: null,
  rank: 1
}

const defalutRank = [
  { id: 1, name: '讲师' },
  { id: 2, name: '副教授' },
  { id: 3, name: '教授' }
]

export default {
  data() {
    return {
      isEdit: false,
      teacher: Object.assign({}, defaultTeacher),
      rankData: Object.assign([], defalutRank)
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
        this.handleTeacherById(this.$route.params.id)
      }
    },
    handleTeacherById() {
      teacher.getTeacherById(this.$route.params.id).then(response => {
        this.teacher = response.data.teacher
      })
    },
    handleConfirm() {
      if (this.$route.params && this.$route.params.id) {
        this.updateTeacher()
      } else {
        this.saveTeacher()
      }
    },
    updateTeacher() {
      teacher.updateTeacher(this.teacher).then(response => {

      })
    },
    saveTeacher() {
      teacher.saveTeacher(this.teacher).then(response => {

      })
    },
    handleChange() {

    },
    handleReset() {
      this.$router.push({ path: '/teacher/list' })
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
