import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import './plugins'
import '@/layouts/export'

import VueAMap from "vue-amap";
Vue.use(VueAMap);
VueAMap.initAMapApiLoader({
  key: "99a2bcb6f2744d4ed4e551cb28470b67",
  // plugin: [
  //   "AMap.Autocomplete", //输入提示插件
  //   "AMap.PlaceSearch", //POI搜索插件
  //   "AMap.Scale", //右下角缩略图插件 比例尺
  //   "AMap.OverView", //地图鹰眼插件
  //   "AMap.ToolBar", //地图工具条
  //   "AMap.MapType", //类别切换控件，实现默认图层与卫星图、实施交通图层之间切换的控制
  //   "AMap.PolyEditor", //编辑 折线多，边形
  //   "AMap.CircleEditor", //圆形编辑器插件
  //   "AMap.Geolocation" //定位控件，用来获取和展示用户主机所在的经纬度位置
  // ],
  // uiVersion: "1.0"
  plugin: [
    'AMap.Autocomplete', 
    'AMap.PlaceSearch', 
    'AMap.Scale', 
    'AMap.OverView', 
    'AMap.ToolBar', 
    'AMap.MapType', 
    'AMap.PolyEditor', 
    'AMap.CircleEditor', 
    'AMap.Geolocation'
  ],
  // 默认高德 sdk 版本为 1.4.4
  v: '1.4.4'
});
/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description 生产环境默认都使用mock，如果正式用于生产环境时，记得去掉
 */

if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('@/utils/static')
  mockXHR()
}

Vue.config.productionTip = false

new Vue({
  el: '#vue-admin-beautiful',
  router,
  store,
  render: (h) => h(App),
})
