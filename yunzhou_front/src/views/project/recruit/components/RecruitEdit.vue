<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="60%"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="标题" prop="jobname">
        <el-input v-model="form.jobname"></el-input>
      </el-form-item>
      <el-form-item label="薪资范围">
        <el-row>
          <el-col :span="8">
            <el-col :span="11"><el-input v-model="min"></el-input></el-col>
            <el-col :span="2">
              <span style="text-align: center; margin-left: 7px">-</span>
            </el-col>
            <el-col :span="11"><el-input v-model="max"></el-input></el-col>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item label="经验要求" prop="conditions">
        <el-input v-model="form.conditions"></el-input>
      </el-form-item>
      <el-form-item label="学历要求" prop="education">
        <el-input v-model="form.education"></el-input>
      </el-form-item>
      <el-form-item label="任职方式" prop="type">
        <el-input v-model="form.type"></el-input>
      </el-form-item>
      <el-form-item label="办公地址" prop="address">
        <el-input v-model="form.address"></el-input>
      </el-form-item>
      <el-form-item label="企业ID" prop="eid">
        <el-input v-model="form.eid"></el-input>
      </el-form-item>
      <el-form-item label="网址" prop="url">
        <el-input v-model="form.url"></el-input>
      </el-form-item>
      <el-form-item label="标签" prop="label">
        <el-input v-model="form.label"></el-input>
      </el-form-item>
      <el-form-item label="职位描述" prop="content1">
        <el-input type="textarea" v-model="form.content1"></el-input>
      </el-form-item>
      <el-form-item label="职位要求" prop="content2">
        <el-input type="textarea" v-model="form.content2"></el-input>
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
import { doEdit, doInsert } from '@/api/recruit'

export default {
  name: 'RecruitEdit',
  data() {
    return {
      min: '',
      max: '',
      form: {
        jobname: '',
        salary: '',
        conditions: '',
        education: '',
        type: '',
        address: '',
        eid: '',
        state: '1',
        url: '',
        label: '',
        content1: '',
        content2: '',
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
        this.min = null
        this.max = null
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
        let s = this.form.salary.split('-')
        this.min = s[0]
        this.max = s[1]
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
          this.form.salary = this.min + '-' + this.max
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
