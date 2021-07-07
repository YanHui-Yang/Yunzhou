<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="文章正文" prop="content">
        <el-input v-model="form.content"></el-input>
      </el-form-item>
      <el-form-item label="文章封面" prop="image">
        <el-input v-model="form.image"></el-input>
      </el-form-item>
      <el-form-item label="是否公开" prop="ispublic">
        <el-input v-model="form.ispublic"></el-input>
      </el-form-item>
      <el-form-item label="是否置顶" prop="istop">
        <el-input v-model="form.istop"></el-input>
      </el-form-item>
      <el-form-item label="浏览量" prop="visits">
        <el-input v-model="form.visits"></el-input>
      </el-form-item>
      <el-form-item label="点赞数" prop="thumbup">
        <el-input v-model="form.thumbup"></el-input>
      </el-form-item>
      <el-form-item label="评论数" prop="comment">
        <el-input v-model="form.comment"></el-input>
      </el-form-item>
      <el-form-item label="审核状态" prop="state">
        <el-input v-model="form.state"></el-input>
      </el-form-item>
      <el-form-item label="URL" prop="url">
        <el-input v-model="form.url"></el-input>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-input v-model="form.type"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { doEdit } from '@/api/article'

export default {
  name: 'ArticleEdit',
  data() {
    return {
      form: {
        title: '',
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
          const { msg } = await doEdit(this.form)
          this.$baseMessage(msg, 'success')
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
