import request from "@/utils/request";
export default {
  sendCode(phone){
    return request({
      url: `/admin/msm/send/${phone}`,
      method: 'get'
    })
  }
}
