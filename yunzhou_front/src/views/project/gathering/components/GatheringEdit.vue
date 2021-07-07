<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="60%"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="活动名称" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="大会简介" prop="summary">
        <el-input v-model="form.summary" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="详细说明" prop="detail">
        <el-input
          v-model="form.detail"
          type="textarea"
          @focus="dialogVisibleDetail = true"
        ></el-input>
      </el-form-item>
      <el-form-item label="活动日期">
        <el-date-picker
          v-model="date"
          type="datetimerange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          :default-time="['12:00:00']"
        ></el-date-picker>
      </el-form-item>
      <el-row>
        <el-col :span="8">
          <el-form-item label="主办方" prop="sponsor">
            <el-input v-model="form.sponsor"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="举办地点" prop="address">
            <el-input
              v-model="form.address"
              @focus="dialogVisibleMap = true"
            ></el-input>
            <!-- <el-button type="text" @click="dialogVisibleMap = true">
              点击打开 Dialog
            </el-button> -->
          </el-form-item>
        </el-col>
      </el-row>
      <el-dialog
        width="70%"
        title="选择地点"
        :visible.sync="dialogVisibleMap"
        append-to-body
        @closed="handleClose"
      >
        <amap ref="amapProps"></amap>
        <span slot="footer" class="dialog-footer">
          <el-button
            type="primary"
            size="mini"
            @click="dialogVisibleMap = false"
          >
            确 定
          </el-button>
        </span>
      </el-dialog>
      <el-dialog
        fullscreen
        title="详细说明"
        :visible.sync="dialogVisibleDetail"
        append-to-body
        @closed="handleClose"
      >
        <vditor ref="detailProps" :vditor-value="form.detail"></vditor>
        <span slot="footer" class="dialog-footer">
          <el-button
            type="primary"
            size="mini"
            @click="dialogVisibleDetail = false"
          >
            确 定
          </el-button>
        </span>
      </el-dialog>
      <!-- http://127.0.0.1:9012/gathering/gathering/image -->
      <el-form-item label="活动封面">
        <el-upload
          class="avatar-uploader"
          action="http://127.0.0.1:9012/gathering/gathering/image"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <el-image
            v-if="form.image"
            :src="form.image"
            class="avatar"
          ></el-image>
          <i
            v-else
            class="el-icon-plus avatar-uploader-icon"
            style="color: #f34d37"
          >
            点我添加封面
          </i>
        </el-upload>
      </el-form-item>
      <el-form-item label="是否可见">
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
  import { doEdit, doInsert } from '@/api/gathering'
  import Amap from '@/layouts/components/Amap'

  export default {
    name: 'GatheringEdit',
    components: {
      Amap,
      vditor: (resolve) => require(['@/layouts/components/Vditor'], resolve),
    },
    data() {
      return {
        date: '',
        form: {
          name: '',
          summary: '',
          detail: '',
          starttime: '',
          endtime: '',
          sponsor: '',
          image: '',
          address: '',
          state: '1',
        },
        rules: {
          title: [{ required: true, trigger: 'blur', message: '请输入标题' }],
        },
        title: '',
        dialogFormVisible: false,
        dialogVisibleMap: false,
        dialogVisibleDetail: false,
      }
    },
    created() {},
    methods: {
      //  handleSuccess(file) {
      // console.log(file);
      // this.$store.dispatch('person/setAvatar', '头像地址');
      // },
      handleAvatarSuccess(res, file) {
        // this.headUrl = URL.createObjectURL(file.raw)
        this.form.image = res
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/png'
        const isLt3M = file.size / 1024 / 1024 < 3

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt3M) {
          this.$message.error('上传头像图片大小不能超过 3MB!')
        }
        return isJPG && isLt3M
      },
      handleClose() {
        if (this.$refs.amapProps)
          this.form.address = this.$refs.amapProps.address
        if (this.$refs.detailProps)
          this.form.detail = this.$refs.detailProps.contentEditor.getValue()
      },
      showEdit(row) {
        if (!row) {
          this.title = '添加'
          this.date = null
        } else {
          this.title = '编辑'
          let t = new Array(0)
          this.form = Object.assign({}, row)
          t.push(this.form.starttime)
          t.push(this.form.endtime)
          this.date = t
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
            this.form.starttime = this.date[0]
            this.form.endtime = this.date[1]
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
