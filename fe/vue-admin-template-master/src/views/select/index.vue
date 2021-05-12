<template>
  <div class="app-container">
    <template v-if="active === 0">
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
              <el-input v-model="listQuery.keyword" class="input-width" placeholder="课程名称" clearable/>
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
          prop="type"
          label="课程类型"
        >
          <template slot-scope="scope">
            <el-tag :type="types[scope.row.type % 4]" size="medium">{{ scope.row.type | handleType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="place"
          label="上课地点"
        />
        <el-table-column
          align="center"
          prop="stock"
          label="库存" >
          <template slot-scope="scope">
            <el-tag size="medium">{{ scope.row.stock }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="teachers"
          label="授课教师" >
          <template slot-scope="scope">
            <el-tag v-for="(teacher,index) in scope.row.teachers" :key="index" size="medium">{{ teacher }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          sortable
          prop="startTime"
          label="开始上课时间"
        />
        <el-table-column
          align="center"
          prop="time"
          label="课程上课时间"
        />
        <el-table-column
          align="center"
          fixed="right"
          label="操作"
        >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleSelectCourse(scope.row)">选课</el-button>
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
    </template>
    <template v-if="active === 1">
      选课将于{{ times[0]| handleTime }}开始，请等待选课开始
    </template>
  </div>
</template>

<script>
import course from '@/api/course/course'
import { mapGetters } from 'vuex'
export default {
  filters: {
    handleType(type) {
      if (type === 0) {
        return '智慧树'
      } else if (type === 1) {
        return '慕课'
      } else {
        return '线下'
      }
    },
    handleTime(time) {
      return new Date(time)
    }
  },
  data() {
    return {
      tableData: [

      ],
      listQuery: {

      },
      listLoading: false,
      limit: 5,
      types: ['success', 'warning', '', 'info', 'error'],
      active: 1,
      times: {}
    }
  },
  computed: {
    ...mapGetters([
      'userId'
    ])
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
      course.getPageListBySelect(this.page, this.limit, this.listQuery).then(
        response => {
          this.tableData = response.data.items
          this.total = response.data.total
          // 数据加载并绑定成功
          this.listLoading = false
        }
      )
    },
    init() {
      course.judgeTime().then(response => {
        const { time, code } = response.data
        console.log(response)
        if (code === 30000) {
          // 当前可以进行选课
          this.active = 0
          this.fetchData()
        } else {
          this.active = 1
          this.times = time
        }
      })
    }
  },
  // 处理查询请求
  handleSearchList() {
    this.fetchData()
  },
  handleResetSearch() {
    this.listQuery.keyword = undefined
    this.fetchData()
  },
  handleSelectCourse(courseId) {
    const userId = this.$store.userId
    course.selectCourse(courseId, userId).then(response => {

    })
  }
}
</script>

<style>

</style>
