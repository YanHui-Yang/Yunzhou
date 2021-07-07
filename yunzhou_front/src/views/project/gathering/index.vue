<template>
  <div class="gathering-container">
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
              v-model.trim="queryForm.name"
              placeholder="请输入活动名称"
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
      @sort-change="tableSortChange"
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
      <el-table-column align="center" prop="name" label="活动名称" />
      <el-table-column
        align="center"
        width="170px"
        prop="summary"
        label="大会简介"
      />
      <el-table-column
        align="center"
        width="120px"
        prop="detail"
        label="详细说明"
      />
      <el-table-column
        align="center"
        width="170px"
        prop="sponsor"
        label="主办方"
      />
      <el-table-column align="center" label="活动图片">
        <template #default="{ row }">
          <el-image
            v-if="imgShow"
            :preview-src-list="imageList"
            :src="row.image"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        width="170px"
        prop="starttime"
        label="开始时间"
      />
      <el-table-column
        align="center"
        width="170px"
        prop="endtime"
        label="截止时间"
      />
      <el-table-column
        align="center"
        show-overflow-tooltip
        width="220px"
        prop="address"
        label="举办地点"
      />
      <el-table-column align="center" label="是否可见">
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
import { getList, doDelete, doEdit, doDeleteBatch } from '@/api/gathering'
import Edit from './components/GatheringEdit'

export default {
  name: 'Gathering',
  components: { Edit },
  data() {
    return {
      imgShow: true,
      imageList: [],
      list: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      queryForm: {
        name: '',
        pageNo: 1,
        pageSize: 10,
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
    tableSortChange() {
      const imageList = []
      this.$refs.tableSort.tableData.forEach((item, index) => {
        imageList.push(item.image)
      })
      this.imageList = imageList
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
      // this.total = totalCount
      // this.listLoading = false
      const imageList = []
      data.forEach((item, index) => {
        imageList.push(item.image)
      })
      this.imageList = imageList
      this.total = totalCount
      setTimeout(() => {
        this.listLoading = false
      }, 500)
    },
  },
}
</script>
