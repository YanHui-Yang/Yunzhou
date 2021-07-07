<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="标签名" prop="labelname">
        <el-input v-model="form.labelname"></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item label="状态">
            <el-switch
              v-model="form.state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"
            ></el-switch>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否推荐">
            <el-switch
              v-model="form.recommend"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"
            ></el-switch>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="标签数" prop="count" hidden>
        <el-input v-model="form.count"></el-input>
      </el-form-item>

      <el-form-item label="粉丝数" prop="fans" hidden>
        <el-input v-model="form.fans"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { doEdit, doInsert } from '@/api/label'

export default {
  name: 'LabelEdit',
  data() {
    return {
      form: {
        labelname: '',
        count: 0,
        state: '1',
        recommend: '0',
        fans: 0,
      },
      rules: {
        labelname: [
          { required: true, trigger: 'blur', message: '请输入标签名' },
        ],
        state: [{ required: true, trigger: 'blur', message: '' }],
        recommend: [{ required: true, trigger: 'blur', message: '' }],
        count: [{ required: true, trigger: 'blur', message: '请输入标签数' }],
        fans: [{ required: true, trigger: 'blur', message: '请输入粉丝数' }],
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
