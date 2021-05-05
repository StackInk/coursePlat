<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"/>
        <span>筛选搜索</span>
        <el-button
          style="float:right"
          type="primary"
          size="small"
          @click="handleSearchList()">
          查询搜索
        </el-button>
        <el-button
          style="float:right;margin-right: 15px"
          size="small"
          @click="handleResetSearch()">
          重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input v-model="listQuery.keyword" class="input-width" placeholder="学生名称" clearable/>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card shadow="never" style="margin-top: 20px">
      <i class="el-icon-tickets"/>
      <span>数据列表</span>
      <el-button size="mini" class="btn-add" style="margin-left: 20px" @click="handleAdd()">添加</el-button>
    </el-card>
    <el-table
      v-loading="listLoading"
      :data="tableData"
      :default-sort = "{prop: 'startTime', order: 'descending'}"
      border
      element-loading-text="Loading"
      style="width: 100%;margin-top: 20px;"
      size="medium"
    >
      <el-table-column
        align="center"
        prop="id"
        label="序号"
        width="50px"
      />
      <el-table-column
        align="center"
        prop="name"
        label="姓名"
      />
      <el-table-column
        align="center"
        prop="department"
        label="学生院系"
      />
      <el-table-column
        align="center"
        prop="major"
        label="学生专业"
      />
      <el-table-column
        align="center"
        prop="credits"
        label="已修学分" >
        <template slot-scope="scope">
          <el-tag :type="types[scope.$index % 5]" size="medium">{{ scope.row.credits }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        fixed="right"
        label="操作"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="handleCourse(scope.row)">已选课程</el-button>
          <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="handleRemove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: right;"
      layout="sizes, prev, pager, next, jumper, ->, total, slot"
      background
      @current-change="fetchData"
      @size-change="changeSize"
    />

    <el-dialog :visible.sync="dialogFormVisible" title="已选课程'">
      <el-table
        :data="gridData"
        style="width: 100%;margin-top: 20px;">
        <el-table-column property="name" label="姓名" width="200"/>
        <el-table-column property="rank" label="职称">
          <template slot-scope="scope">
            {{ scope.row.rank | handleRank }}
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import course from '@/api/course/course'
import student from '@/api/student/student'
export default {
  filters: {

  },
  data() {
    return {
      tableData: [

      ],
      listQuery: {
        keyword: undefined
      },
      listLoading: false,
      limit: 5,
      types: ['success', 'warning', '', 'info', 'danger'],
      dialogFormVisible: false,
      gridData: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    // 当页码发生改变的时候
    changeSize(size) {
      this.limit = size
      this.fetchData(1)
    },
    // 加载讲师列表数据
    fetchData(page = 1) {
      this.listLoading = true
      this.page = page
      student.getPageList(this.page, this.limit).then(
        response => {
          console.log(response)
          this.tableData = response.data.items
          this.total = response.data.total

          // 数据加载并绑定成功
          this.listLoading = false
        }
      )
    },
    init() {
      this.fetchData()
    },
    // 处理查询请求
    handleSearchList() {
      student.getStudentByName(this.listQuery.keyword).then(response => {
        this.tableData = response.data.student
      })
    },
    handleResetSearch() {
      this.listQuery.keyword = undefined
      this.fetchData()
    },
    // 处理添加请求
    handleAdd() {
      this.$router.push({ path: '/student/create' })
    },
    handleCourse(studentId) {
      this.dialogFormVisibleStudent = true
      course.getStudentsByCourseId(studentId).then(response => {
        this.studentData = response.data.students
      })
    },
    handleEdit(studentId) {
      this.$router.push({ path: `/student/edit/${studentId}` })
    },
    handleRemove(courseId) {
      // debugger
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        return student.removeById(courseId)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.success) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style>

</style>
