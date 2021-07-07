<template>
  <div class="column-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="12">
        <el-button icon="el-icon-plus" type="primary" @click="handleEdit">
          添加
        </el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
          批量删除
        </el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="12">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.title"
              placeholder="请输入标题"
              clearable
            />
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table
      v-loading="listLoading"
      :data="list"
      @selection-change="setSelectRows"
    >
      <el-table-column
        align="center"
        show-overflow-tooltip
        type="selection"
      ></el-table-column>
      <el-table-column label="序号" width="70px" align="center">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="name" label="专栏名称" />
      <el-table-column align="center" prop="summary" label="专栏简介" />
      <el-table-column align="center" prop="createtime" label="申请日期" />
      <el-table-column align="center" prop="checktime" label="审核日期" />
      <el-table-column align="center" label="状态">
        <template slot-scope="{ row }">
          <el-switch
            @change="updateState(row)"
            v-model="row.state"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :inactive-value="stateData.inactiveValue"
            :active-value="stateData.activeValue"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="100" fixed="right">
        <template #default="{ row }">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            circle
            @click="handleEdit(row)"
          ></el-button>
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            circle
            @click="handleDelete(row)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageNo"
      :page-size="queryForm.pageSize"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    ></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
import { getList, doDelete, doEdit, doDeleteBatch } from '@/api/column'
import Edit from './components/ColumnEdit'

export default {
  name: 'Column',
  components: { Edit },
  data() {
    return {
      list: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        title: '',
      },
      stateData: {
        inactiveValue: '0',
        activeValue: '1',
      },
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 修改状态
    updateState(row) {
      // return
      doEdit(row).then((res) => {
        if (res != null && res.code == 20000 && row.state == '1') {
          this.$message({
            message: '已启动',
            type: 'success',
          })
        } else if (res != null && res.code == 20000 && row.state == '0') {
          this.$message({
            message: '已禁用',
            type: 'success',
          })
        }
      })
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleEdit(row) {
      if (row.id) {
        this.$refs['edit'].showEdit(row)
      } else {
        this.$refs['edit'].showEdit()
      }
    },
    handleDelete(row) {
      if (row.id) {
        this.$baseConfirm('你确定要删除当前项吗', null, async () => {
          const { msg } = await doDelete(row.id)
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        if (this.selectRows.length > 0) {
          const ids = this.selectRows.map((item) => item.id).join()
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const { msg } = await doDeleteBatch({ ids })
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      }
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    async fetchData() {
      this.listLoading = true
      const { data, totalCount } = await getList(this.queryForm)
      this.list = data
      this.total = totalCount
      this.listLoading = false
    },
  },
}
</script>
