import axios from "axios";//引入axios
import { MessageBox, Message } from "element-ui";
import store from "../store";
import { getToken } from "./auth";

//创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API,
  timeout: 15000
})
//request拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token){
      config.headers['token'] = getToken() //让每个请求携带自定义token
    }
    return config
  },
  error => {
    console.log(error)
    Promise.reject(error)
  }
)
//response拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      Message({
        message: res.message,
        type: 'error',
        duration: 5 * 1000
      })

      // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014)
        MessageBox.confirm(
          '你已被登成，请重新登录',
          '确认登出',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('FedLogOut').then(() => {
            location.reload()
          })
        })
      return Promise.reject('error')
    } else {
      return response.data
    }
  },
  error => {
    console.log('err' + error)
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)
export default service
