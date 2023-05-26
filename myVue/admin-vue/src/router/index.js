import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from "../views/layout/Layout";
import Login from "../views/login/login"
import error404 from "../views/404"

export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/login'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/user',
    component: Layout,
    redirect: '/user/userList',
    name: '用户管理',
    meta: { title: '用户管理'},
    alwaysShow: true,
    children: [
      {
        path: 'userList',
        name: '用户管理列表',
        component: () => import('../views/user/userList'),
        meta: { title: '用户管理列表' }
      },
      {
        path: 'edit/:id',
        name: '修改用户资料',
        component: () => import('../views/user/edit'),
        meta: { title: '修改用户资料' },
        hidden: true
      }
    ]
  },

  {
    path: '/vaccine',
    component: Layout,
    redirect: '/vaccine/classify',
    name: '疫苗管理',
    meta: { title: '疫苗管理'},
    alwaysShow: true,
    children: [
      {
        path: 'classify',
        name: '类型管理列表',
        component: () => import('../views/vaccine/classify'),
        meta: { title: '类型管理列表' }
      },
      {
        path: 'editClassify/:id',
        name: '修改类型',
        component: () => import('../views/vaccine/editClassify'),
        meta: { title: '修改类型' },
        hidden: true
      },
      {
        path: 'editClassify',
        name: '添加类型',
        component: () => import('../views/vaccine/editClassify'),
        meta: { title: '添加类型' },
        hidden: true
      },
      {
        path: 'vaccine',
        name: '疫苗管理列表',
        component: () => import('@/views/vaccine/vaccine'),
        meta: { title: '疫苗管理列表' }
      },
      {
        path: 'vaccine/:id',
        name: '疫苗管理列表',
        component: () => import('@/views/vaccine/vaccine'),
        meta: { title: '疫苗管理列表' },
        hidden: true
      },
      {
        path: 'editVac',
        name: '修改疫苗',
        component: () => import('../views/vaccine/editVac'),
        meta: { title: '修改疫苗' },
        hidden: true
      },
      {
        path: 'editVac/:id',
        name: '修改疫苗',
        component: () => import('../views/vaccine/editVac'),
        meta: { title: '修改疫苗' },
        hidden: true
      },
    ]
  },

  {
    path: '/worker',
    component: Layout,
    redirect: '/worker/address',
    name: '员工管理',
    meta: { title: '员工管理'},
    alwaysShow: true,
    children: [
      {
        path: 'address',
        name: '接种点管理列表',
        component: () => import('../views/worker/address'),
        meta: { title: '接种点管理列表' }
      },
      {
        path: 'editAddr/:id',
        name: '修改接种点',
        component: () => import('../views/worker/editAddr'),
        meta: { title: '修改接种点' },
        hidden: true
      },
      {
        path: 'editAddr',
        name: '添加接种点',
        component: () => import('../views/worker/editAddr'),
        meta: { title: '添加接种点' },
        hidden: true
      },
      {
        path: 'worker',
        name: '工作人员管理列表',
        component: () => import('@/views/worker/worker'),
        meta: { title: '工作人员管理列表' }
      },
      {
        path: 'worker/:id',
        name: '工作人员管理列表',
        component: () => import('@/views/worker/worker'),
        meta: { title: '工作人员管理列表' },
        hidden: true
      },
      {
        path: 'editWorker',
        name: '添加工作人员',
        component: () => import('../views/worker/editWorker'),
        meta: { title: '添加工作人员' },
        hidden: true
      },
      {
        path: 'editWorker/:id',
        name: '修改工作人员',
        component: () => import('../views/worker/editWorker'),
        meta: { title: '修改工作人员' },
        hidden: true
      },
    ]
  },

  {
    path: '/order',
    component: Layout,
    redirect: '/order/order',
    name: '订单管理',
    meta: { title: '订单管理'},
    alwaysShow: true,
    children: [
      {
        path: 'order',
        name: '订单管理列表',
        component: () => import('../views/order/order'),
        meta: { title: '订单管理列表' }
      },
      {
        path: 'detail/:id',
        name: "查看订单详情",
        component: () => import('../views/order/detail'),
        meta: { title: '查看订单详情' },
        hidden: true
      }
    ]
  },
]

export default new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
