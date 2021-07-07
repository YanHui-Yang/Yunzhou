<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="60%"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="企业名称" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="企业简介" prop="summary">
        <el-input v-model="form.summary"></el-input>
      </el-form-item>
      <el-form-item label="企业地址" prop="address">
        <el-input
          v-model="form.address"
          @focus="dialogVisibleAddress = true"
        ></el-input>
      </el-form-item>
      <el-dialog
        width="70%"
        title="选择地点"
        :visible.sync="dialogVisibleAddress"
        append-to-body
        @closed="handleClose"
      >
        <amap ref="amapProps"></amap>
        <span slot="footer" class="dialog-footer">
          <el-button
            type="primary"
            @click="dialogVisibleAddress = false"
            size="mini"
          >
            确 定
          </el-button>
        </span>
      </el-dialog>
      <el-form-item label="标签列表" prop="labels">
        <el-input v-model="form.labels"></el-input>
      </el-form-item>
      <el-form-item label="标签列表" prop="labels">
        <el-select
          v-model="value"
          multiple
          filterable
          allow-create
          default-first-option
          placeholder="请选择文章标签"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="坐标" prop="coordinate">
        <el-input v-model="form.coordinate"></el-input>
      </el-form-item> -->
      <el-form-item label="LOGO">
        <el-upload
          accept=".jpg, .jpeg, .png, .gif"
          class="avatar-uploader"
          action="http://127.0.0.1:9012/recruit/enterprise/image"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <el-image
            v-if="form.logo"
            :src="form.logo"
            class="avatar"
            style="width: 100px; heigth: 100px"
          ></el-image>
          <i
            v-else
            class="el-icon-plus avatar-uploader-icon"
            style="color: #f34d37"
          >
            点我添加logo
          </i>
        </el-upload>
      </el-form-item>
      <el-form-item label="职位数" prop="jobcount">
        <el-input v-model="form.jobcount"></el-input>
      </el-form-item>
      <el-form-item label="URL" prop="url">
        <el-input v-model="form.url"></el-input>
      </el-form-item>
      <el-form-item label="是否热门">
        <el-switch
          v-model="form.ishot"
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
import { doEdit, doInsert } from '@/api/enterprise'

export default {
  name: 'EnterpriseEdit',
  data() {
    return {
      options: [
        {
          value: '电子商务',
          label: '电子商务',
        },
        {
          value: '游戏',
          label: '游戏',
        },
        {
          value: '媒体',
          label: '媒体',
        },
        {
          value: '互联网',
          label: '互联网',
        },
      ],
      value: [],
      form: {
        name: '',
        summary: '',
        address: '',
        labels: '',
        coordinate: '',
        ishot: '1',
        logo: '',
        jobcount: '',
        url: '',
      },
      rules: {
        title: [{ required: true, trigger: 'blur', message: '请输入标题' }],
      },
      title: '',
      dialogFormVisible: false,
      dialogVisibleAddress: false,
    }
  },
  created() {},
  methods: {
    handleAvatarSuccess(res, file) {
      // this.headUrl = URL.createObjectURL(file.raw)
      this.form.logo = res
    },
    beforeAvatarUpload(file) {
      // const isJPG = file.type === 'image/png'
      const isLt3M = file.size / 1024 / 1024 < 3

      // if (!isJPG) {
      //   this.$message.error('上传头像图片只能是 JPG 格式!')
      // }
      if (!isLt3M) {
        this.$message.error('上传头像图片大小不能超过 3MB!')
      }
      // return isJPG && isLt3M
      return isLt3M
    },
    handleClose() {
      if (this.$refs.amapProps) this.form.address = this.$refs.amapProps.address
    },
    showEdit(row) {
      if (!row) {
        this.title = '添加'
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
        this.value = row.labels.split(',')
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
        this.form.labels = this.value.toString()
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
