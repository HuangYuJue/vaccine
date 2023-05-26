import request from '../utils/request'
//api：接口，提供被调用的接口
export function login(loginPwVo){
  return request({
    url: `/admin/user/login`,
    method: 'post',
    data: loginPwVo
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
