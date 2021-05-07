<template>
  <div class="app-container">
    <el-card shadow="hover" class="zl-card">
      <div slot="header" class="clearfix">
        <span>{{ isEdit ?'修改角色信息':'添加角色信息' }}</span>
      </div>
      <el-form
        ref="role"
        :model="role"
        :rules="validateRules"
        label-width="120px"
        size="small"
      >
        <el-form-item label="角色名称" prop="name" >
          <el-input v-model="role.name" style="width:250px" />
        </el-form-item>
        <el-form-item label="角色编码" prop="code" >
          <el-input v-model="role.code" style="width:250px"/>
        </el-form-item>
        <el-form-item label="角色描述" prop="remark" >
          <el-input v-model="role.remark" style="width:250px" />
        </el-form-item>
      </el-form>
      <div class="zl-button">
        <el-button type="warning" size="small" @click="handleReset()">取 消</el-button>
        <el-button v-loading.fullscreen.lock="fullscreenLoading" type="success" size="small" @click="saveOrUpdate()">确 定</el-button>
      </div>
    </el-card>

  </div>
</template>

<script>
import roleApi from '@/api/acl/role'

const defaultForm = {
  name: null,
  code: 0,
  remark: null
}

export default {
  data() {
    return {
      role: Object.assign({}, defaultForm),
      saveBtnDisabled: false, // 保存按钮是否禁用,
      validateRules: {
        name: [
          { required: true, trigger: 'blur', message: '角色名必须输入' }
        ]
      },
      fullscreenLoading: false,
      isEdit: false
    }
  },

  // 监听器
  watch: {
    $route(to, from) {
      this.init()
    }
  },

  // 生命周期方法（在路由切换，组件不变的情况下不会被调用）
  created() {
    this.init()
  },

  methods: {
    // 表单初始化
    init() {
      console.log(this.$route.params)
      if (this.$route.params && this.$route.params.id) {
        debugger
        this.isEdit = true
        const id = this.$route.params.id
        this.fetchDataById(id)
      } else {
        // 对象拓展运算符：拷贝对象，而不是赋值对象的引用
        this.role = { ...defaultForm }
      }
    },

    saveOrUpdate() {
      this.$refs.role.validate(valid => {
        if (valid) {
          this.saveBtnDisabled = true // 防止表单重复提交
          if (!this.role.id) {
            this.saveData()
          } else {
            this.updateData()
          }
        }
      })
    },

    // 新增讲师
    saveData() {
      roleApi.save(this.role).then(response => {
        // debugger
        if (response.success) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/acl/role/list' })
        }
      })
    },

    // 根据id更新记录
    updateData() {
      // teacher数据的获取
      roleApi.updateById(this.role).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/acl/role/list' })
        }
      })
    },

    // 根据id查询记录
    fetchDataById(id) {
      roleApi.getById(id).then(response => {
        this.role = response.data.role
      })
    },
    handleReset() {
      this.$router.push({ path: '/acl/role/list' })
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
