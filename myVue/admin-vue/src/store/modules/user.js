import { login, logout, getInfo } from "@/api/login";
import { getToken, setToken, removeToken } from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    phone: '',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    roles: [
      'admin'
    ]
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_PHONE: (state, phone) => {
      state.phone = phone
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
  },

  actions: {
    // 登录
    login({ commit }, loginPwVo){
      const { phone, password } = loginPwVo
      return new Promise((resolve, reject) => {
        login({phone: phone, password: password}).then(response => {
          const { data } = response
          commit('SET_TOKEN', data.token)
          setToken(data.token)
          console.log("user.js中setToken了")
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit }){
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(response => {
          const data = response
          if(data.roles && data.roles.length > 0){
            commit('SET_ROLES',data.roles)
            console.log("user.js里data中的rolescommitle")
          }
          commit('SET_PHONE',data.phone)
        })
      })
    },

    // 登出
    LogOut({ commit,state }){
      return new Promise((resolve,reject) => {
        commit('SET_TOKEN','')
        commit('SET_ROLES',[])
        removeToken()
        resolve()
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
