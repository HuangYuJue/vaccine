// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import 'normalize.css/normalize.css'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN'//修改语言为中文语言环境
import '@/styles/index.scss'
import Vuex from 'vuex'
import App from './App'
import store from './store'
import router from './router'//引入路由模板
import '@/icons'
Vue.use(ElementUI, { locale })
Vue.use(Vuex)
import '@/styles/index.scss'
import '@/permission'
Vue.config.productionTip = false
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
