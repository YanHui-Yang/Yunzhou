<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="专栏名称" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="专栏简介" prop="summary">
        <el-input v-model="form.summary"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-switch
          v-model="form.state"
          active-color="#13ce66"
          inactive-color="#ff4949"
          active-value="1"
          inactive-value="0"
        ></el-switch>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { doEdit, doInsert } from '@/api/column'

export default {
  name: 'ColumnEdit',
  data() {
    return {
      form: {
        title: '',
        state: '1',
      },
      rules: {
        title: [{ required: true, trigger: 'blur', message: '请输入标题' }],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {},
  methods: {
    showEdit(row) {
      if (!row) {
        this.title = '添加'
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
      }
      this.dialogFormVisible = true
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          let { data } = { msg: '' }
          if (this.form.id != null) {
            data = await doEdit(this.form)
          } else if (this.form.id == null) {
            data = await doInsert(this.form)
          }
          this.$baseMessage(data.msg, 'success')
          this.$emit('fetch-data')
          this.close()
        } else {
          return false
        }
      })
    },
  },
}
</script>
