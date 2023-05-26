import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _86dc4540 = () => interopDefault(import('..\\pages\\order\\index.vue' /* webpackChunkName: "pages/order/index" */))
const _0c14b98a = () => interopDefault(import('..\\pages\\user\\index.vue' /* webpackChunkName: "pages/user/index" */))
const _226f5e41 = () => interopDefault(import('..\\pages\\vaccine\\_id.vue' /* webpackChunkName: "pages/vaccine/_id" */))
const _4b50d466 = () => interopDefault(import('..\\pages\\index.vue' /* webpackChunkName: "pages/index" */))

const emptyFn = () => {}

Vue.use(Router)

export const routerOptions = {
  mode: 'history',
  base: '/',
  linkActiveClass: 'nuxt-link-active',
  linkExactActiveClass: 'nuxt-link-exact-active',
  scrollBehavior,

  routes: [{
    path: "/order",
    component: _86dc4540,
    name: "order"
  }, {
    path: "/user",
    component: _0c14b98a,
    name: "user"
  }, {
    path: "/vaccine/:id?",
    component: _226f5e41,
    name: "vaccine-id"
  }, {
    path: "/",
    component: _4b50d466,
    name: "index"
  }],

  fallback: false
}

export function createRouter (ssrContext, config) {
  const base = (config._app && config._app.basePath) || routerOptions.base
  const router = new Router({ ...routerOptions, base  })

  // TODO: remove in Nuxt 3
  const originalPush = router.push
  router.push = function push (location, onComplete = emptyFn, onAbort) {
    return originalPush.call(this, location, onComplete, onAbort)
  }

  const resolve = router.resolve.bind(router)
  router.resolve = (to, current, append) => {
    if (typeof to === 'string') {
      to = normalizeURL(to)
    }
    return resolve(to, current, append)
  }

  return router
}
