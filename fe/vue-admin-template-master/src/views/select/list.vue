<template>
  <div class="app-container">
    <el-steps :active="active" align-center style="margin-bottom:20px;">
      <el-step title="添加选课课程" description="勾选表格前面的复选框，点击下一步添加选课信息"/>
      <el-step title="选课时间" description="选择本次选课开始时间"/>
    </el-steps>

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
      <el-table
        v-loading="listLoading"
        :data="tableData"
        :default-sort = "{prop: 'startTime', order: 'descending'}"
        border
        element-loading-text="Loading"
        style="width: 100%;margin-top: 20px;"
        size="medium"
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          type="selection"
          width="55"/>
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
      <div class="zl-picker">
        <el-card shadow="hover">
          <el-date-picker
            v-model="times"
            value-format="timestamp"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"/>
        </el-card>
      </div>
    </template>
    <div class="zl-button">
      <el-button v-if="active > 0" type="primary" style="margin:12px;width:100px;" size="small" @click="pre">上一步</el-button>
      <el-button type="success" style="margin:12px; width:100px;" size="small" @click="next" >{{ active === 1 ? '保存' : '下一步' }}</el-button>
    </div>
  </div>
</template>

<script>
import course from '@/api/course/course'
export default {
  filters: {
    handleType(type) {
      if (type === 1) {
        return '智慧树'
      } else if (type === 2) {
        return '慕课'
      } else {
        return '线下'
      }
    },
    handleRank(rank) {
      if (rank === 1) {
        return '讲师'
      } else if (rank === 2) {
        return '副教授'
      } else {
        return '教授'
      }
    }
  },
  data() {
    return {
      tableData: [],
      multipleSelection: [],
      listQuery: {
        keyword: undefined
      },
      listLoading: false,
      limit: 5,
      types: ['success', 'warning', 'info', ''],
      total: 0,
      active: 0,
      times: []
    }
  },
  created() {
    this.init()
  },

  methods: {

    next() {
      const active = this.active
      if (active === 0) {
        this.active = 1
      } else if (active === 1) {
        const { multipleSelection, times } = this
        const var1 = multipleSelection.map(select => select.id).join(',')
        const var2 = times.join(',')
        course.addSelectCourse(var1, var2).then(response => {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.active = 0
          this.multipleSelection = []
        })
      }
    },
    pre() {
      const active = this.active
      if (active === 1) {
        this.active = 0
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 当页码发生改变的时候
    changeSize(size) {
      this.limit = size
      this.fetchData(1)
    },
    // 加载讲师列表数据
    fetchData(page = 1) {
      this.listLoading = true
      this.page = page
      course.getPageList(this.page, this.limit).then(
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
      course.getPageListByName(this.page, this.limit, this.listQuery.keyword).then(response => {
        this.tableData = response.data.items
        this.total = response.data.total
      })
    },
    handleResetSearch() {
      this.listQuery.keyword = undefined
      this.fetchData()
    }
  }
}
</script>

<style scope>
  .zl-button{
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .zl-picker{
    margin: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
