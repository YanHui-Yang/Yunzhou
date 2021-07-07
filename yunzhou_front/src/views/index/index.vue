<template>
  <div class="index-container">
    <el-row :gutter="20">
      <el-col
        v-for="(item, index) in iconList"
        :key="index"
        :xs="12"
        :sm="6"
        :md="3"
        :lg="3"
        :xl="3"
      >
        <router-link :to="item.link" target="_blank">
          <el-card class="icon-panel" shadow="never">
            <vab-icon
              :style="{ color: item.color }"
              :icon="['fas', item.icon]"
            ></vab-icon>
            <p>{{ item.title }}</p>
          </el-card>
        </router-link>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-alert
          v-if="noticeList[1]"
          :title="noticeList[1].title"
          :closable="noticeList[1].closable"
        ></el-alert>

        <el-alert
          v-if="noticeList[2]"
          :title="noticeList[2].title"
          :closable="noticeList[2].closable"
        ></el-alert>
      </el-col>

      <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span>前端 - 版本</span>
          </div>
          <table class="table">
            <tr>
              <td style="width: 25%">@vue/cli版本</td>
              <td style="width: 25%">
                {{ devDependencies['@vue/cli-service'] }}
              </td>
              <td style="width: 25%">vue版本</td>
              <td style="width: 25%">{{ dependencies['vue'] }}</td>
            </tr>
            <tr>
              <td>vuex版本</td>
              <td>{{ dependencies['vuex'] }}</td>
              <td>vue-router版本</td>
              <td>{{ dependencies['vue-router'] }}</td>
            </tr>
            <tr>
              <td>element-ui版本</td>
              <td>{{ dependencies['element-ui'] }}</td>
              <td>axios版本</td>
              <td>{{ dependencies['axios'] }}</td>
            </tr>
            <tr>
              <td>eslint版本</td>
              <td>{{ devDependencies['eslint'] }}</td>
              <td>prettier版本</td>
              <td>{{ devDependencies['prettier'] }}</td>
            </tr>
            <tr>
              <td>sass版本</td>
              <td>{{ devDependencies['sass'] }}</td>
              <td>mockjs版本</td>
              <td>{{ devDependencies['mockjs'] }}</td>
            </tr>
            <tr>
              <td>zx-layouts版本</td>
              <td>{{ dependencies['zx-layouts'] }}</td>
              <td>lodash版本</td>
              <td>{{ dependencies['lodash'] }}</td>
            </tr>
          </table>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span>后端 - 版本</span>
          </div>
          <table class="table">
            <tr>
              <td style="width: 25%">jdk版本</td>
              <td style="width: 15%">1.8</td>
              <td style="width: 35%">Spring Boot版本</td>
              <td style="width: 25%">2.3.7.RELEASE</td>
            </tr>
            <tr>
              <td style="width: 25%">Redis版本</td>
              <td style="width: 15%">6.2.1</td>
              <td style="width: 35%">Mongo DB版本</td>
              <td style="width: 25%">4.4.4</td>
            </tr>
            <tr>
              <td style="width: 25%">Elasticsearch版本</td>
              <td style="width: 15%">7.8.0</td>
              <td style="width: 35%">Rabbit MQ版本</td>
              <td style="width: 25%">3.6.8</td>
            </tr>
            <tr>
              <td style="width: 25%">Spring Security版本</td>
              <td style="width: 15%">2.3.7.RELEASE</td>
              <td style="width: 25%">JJWT版本</td>
              <td style="width: 15%">0.9.1</td>
            </tr>
            <tr>
              <td style="width: 25%">Linux版本</td>
              <td style="width: 15%">Cent OS 7</td>
              <td style="width: 35%">Spring Cloud版本</td>
              <td style="width: 25%">Hoxton.SR8</td>
            </tr>
          </table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { dependencies, devDependencies } from '../../../package.json'
export default {
  name: 'Index',
  components: {},
  data() {
    return {
      timer: 0,
      nodeEnv: process.env.NODE_ENV,
      dependencies: dependencies,
      devDependencies: devDependencies,
      //卡片图标
      iconList: [
        {
          icon: 'video',
          title: '视频播放器',
          link: '/vab/player',
          color: '#ffc069',
        },
        {
          icon: 'table',
          title: '表格',
          link: '/vab/table/comprehensiveTable',
          color: '#5cdbd3',
        },
        {
          icon: 'laptop-code',
          title: '源码',
          link: 'https://github.com/hiparker/opsli-boot',
          color: '#b37feb',
        },
        {
          icon: 'book',
          title: '书籍',
          link: '',
          color: '#69c0ff',
        },
        {
          icon: 'bullhorn',
          title: '公告',
          link: '',
          color: '#ff85c0',
        },
        {
          icon: 'gift',
          title: '礼物',
          link: '',
          color: '#ffd666',
        },
        {
          icon: 'balance-scale-left',
          title: '公平的世界',
          link: '',
          color: '#ff9c6e',
        },
        {
          icon: 'coffee',
          title: '休息一下',
          link: '',
          color: '#95de64',
        },
      ],
    }
  },
  created() {
    this.fetchData()
  },
  beforeDestroy() {
    clearInterval(this.timer)
  },
  mounted() {},
  methods: {
    async fetchData() {
      const res = [
        {
          title: '究竟是什么样的终点，才配得上这一路的颠沛流离。。。',
          closable: false,
          type: 'success',
        },
        {
          title:
            '作者寄语：感恩相遇，愿世间美好与我们环环相扣，加油！屏幕前的我们，打破桎梏，坚守初心。其实人生改变命运的机会并没有太多，我们并不是不优秀，我们也并不是一无是处，我们也希望驻足山巅被众人仰望，也许我们缺少的只是一个机会，缺少的只是生命中的导师，我希望这个框架帮助到更多的人，希望有一天，我们面试的时候不再胆怯，希望有一天别人看到的不仅仅是你的努力，还有你的功成名就，出人头地。',
          closable: false,
          type: 'warning',
        },
        {
          title: '鸣谢：element-ui、vue-admin-beautiful。',
          closable: false,
          type: 'success',
        },
      ]
      this.noticeList = res
    },
  },
}
</script>
<style lang="scss" scoped>
.index-container {
  padding: 0 !important;
  margin: 0 !important;
  background: #f5f7f8 !important;
  .table-title {
    padding: 10px;
    padding-left: 0;
    font-size: 16px;
    font-weight: bold;
    text-align: left;
  }
  ::v-deep {
    .el-alert {
      padding: $base-padding;
      &--info.is-light {
        min-height: 82px;
        padding: $base-padding;
        margin-bottom: 15px;
        color: #909399;
        background-color: $base-color-white;
        border: 1px solid #ebeef5;
      }
    }
    .el-card__body {
      .echarts {
        width: 100%;
        height: 125px;
      }
    }
  }
  .card {
    min-height: 400px;
    ::v-deep {
      .el-card__body {
        .echarts {
          width: 100%;
          height: 305px;
        }
      }
    }
  }
  .bottom {
    padding-top: 20px;
    margin-top: 5px;
    color: #595959;
    text-align: left;
    border-top: 1px solid $base-border-color;
  }
  .table {
    width: 100%;
    color: #666;
    border-collapse: collapse;
    background-color: #fff;
    td {
      position: relative;
      min-height: 20px;
      padding: 9px 15px;
      font-size: 14px;
      line-height: 20px;
      border: 1px solid #e6e6e6;
      &:nth-child(odd) {
        width: 20%;
        text-align: right;
        background-color: #f7f7f7;
      }
    }
  }
  .icon-panel {
    height: 100px;
    text-align: center;
    cursor: pointer;
    svg {
      font-size: 40px;
    }
    p {
      margin-top: 10px;
    }
  }
  .bottom-btn {
    button {
      margin: 5px 10px 15px 0;
    }
  }
}
</style>