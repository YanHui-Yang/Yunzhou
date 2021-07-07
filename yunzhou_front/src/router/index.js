/**
 * @copyright chuzhixin 1204505056@qq.com
 * @description router全局配置，如有必要可分文件抽离
 */

import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layouts'
import EmptyLayout from '@/layouts/EmptyLayout'
import { publicPath, routerMode } from '@/config'

Vue.use(VueRouter)

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true,
  },
  {
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true,
  },
  {
    path: '/401',
    name: '401',
    component: () => import('@/views/401'),
    hidden: true,
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/404'),
    hidden: true,
  },
]

/*当settings.js里authentication配置的是intelligence时，views引入交给前端配置*/
export const asyncRoutes = [
  {
    path: '/',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: '/index',
        name: 'Index',
        component: () => import('@/views/index/index'),
        meta: {
          title: '首页',
          icon: 'home',
          affix: true,
          noKeepAlive: true,
        },
      },
    ],
  },
  {
    path: '/example',
    component: Layout,
    redirect: '/example/table',
    name: 'Example',
    meta: { title: '基本信息管理', icon: 'example' },
    children: [
      // {
      //   path: 'table',
      //   name: 'Table',
      //   component: () => import('@/views/project/city'),
      //   meta: { title: '城市管理', icon: 'table' }
      // },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/project/label'),
        meta: { title: '标签管理', icon: 'align-left' },
      },
    ],
  },

  // {
  //   path: '/gathering',
  //   component: Layout,
  //   name: 'gathering',
  //   meta: { title: '活动管理', icon: 'example' },
  //   children: [
  //     {
  //       path: 'gathering',
  //       name: 'gathering',
  //       component: () => import('@/views/table/gathering'),
  //       meta: { title: '活动管理', icon: 'form' }
  //     }
  //   ]
  // },

  {
    path: '/gathering',
    component: Layout,
    redirect: '/gathering',
    children: [
      {
        path: 'gathering',
        name: 'Gathering',
        component: () => import('@/views/project/gathering'),
        meta: { title: '活动管理', icon: 'list' },
      },
    ],
  },

  {
    path: '/recruit',
    component: Layout,
    name: 'recruit',
    meta: { title: '招聘管理', icon: 'table' },
    children: [
      {
        path: 'enterprise',
        name: 'enterprise',
        component: () => import('@/views/project/enterprise'),
        meta: { title: '企业管理' },
      },
      {
        path: 'recruit',
        name: 'recruit',
        component: () => import('@/views/project/recruit'),
        meta: { title: '招聘管理' },
      },
    ],
  },
  {
    path: '/article',
    component: Layout,
    name: 'article',
    meta: { title: '文章管理', icon: 'book' },
    children: [
      {
        path: 'channel',
        name: 'channel',
        component: () => import('@/views/project/channel'),
        meta: { title: '频道管理' },
      },
      {
        path: 'column',
        name: 'column',
        component: () => import('@/views/project/column'),
        meta: { title: '专栏管理' },
      },
      {
        path: 'article',
        name: 'article',
        component: () => import('@/views/project/article'),
        meta: { title: '文章管理' },
      },
    ],
  },
  {
    path: '/actuator',
    component: Layout,
    name: 'actuator',
    meta: { title: '后台监控', icon: 'cog' },
    children: [
      {
        path: 'druid',
        name: 'druid',
        component: () => import('@/views/project/actuator/druid'),
        meta: { title: 'SQL监控' },
      },
      {
        path: 'consul',
        name: 'consul',
        component: () => import('@/views/project/actuator/consul'),
        meta: { title: '注册中心' },
      },
      {
        path: 'boot',
        name: 'boot',
        component: () => import('@/views/project/actuator/bootserver'),
        meta: { title: '服务监控' },
      },
      // {
      //   path: 'monitor',
      //   name: 'monitor',
      //   component: () => import('@/views/project/actuator/monitor'),
      //   meta: { title: '系统监控' }
      // }
    ],
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true,
  },
]

const router = new VueRouter({
  base: routerMode === 'history' ? publicPath : '',
  mode: routerMode,
  scrollBehavior: () => ({
    y: 0,
  }),
  routes: constantRoutes,
})
//注释的地方是允许路由重复点击，如果你觉得框架路由跳转规范太过严格可选择放开
/* const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject);
  return originalPush.call(this, location).catch((err) => err);
}; */

export function resetRouter() {
  router.matcher = new VueRouter({
    base: routerMode === 'history' ? publicPath : '',
    mode: routerMode,
    scrollBehavior: () => ({
      y: 0,
    }),
    routes: constantRoutes,
  }).matcher
}

export default router
