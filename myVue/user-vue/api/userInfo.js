import request from "@/utils/request";
export default {
  loginCode(loginCodeVo){
    return request({
      url: `/api/users/user/loginCode`,
      method: 'post',
      data: loginCodeVo
    })
  },
  loginPw(loginPwVo){
    return request({
      url: `/api/users/user/loginPw`,
      method: 'post',
      data: loginPwVo
    })
  },
  userDetail(){
    return request({
      url: `/api/users/user/userDetail`,
      method: 'get'
    })
  },
  //判断用户是否保存过密码，保存过修改密码是显示修改密码，未保存过则显示设置密码
  getPass(){
    return request({
      url: `/api/users/user/get`,
      method: 'get'
    })
  },
  updatePwd(pwd){
    return request({
      url: `/api/users/user/setPw/${pwd}`,
      method: 'post'
    })
  },
  update(userVo){
    return request({
      url: `/api/users/user/update`,
      method: 'post',
      data: userVo
    })
  }
}
