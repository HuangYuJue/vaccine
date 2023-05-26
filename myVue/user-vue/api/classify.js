import request from "@/utils/request";
export default {
  findByName(claname){
    return request({
      url: `/api/users/classify/findByName/${claname}`,
      method: 'get'
    })
  },
  getAddress(){
    return request({
      url: `/api/users/address/findAddress`,
      method: 'get'
    })
  },
  getWorkers(addressId){
    return request({
      url: `/api/users/worker/findWorkers/${addressId}`,
      method: 'get'
    })
  },
  getClassify(classifyId){
    return request({
      url: `/api/users/classify/getClassify/${classifyId}`,
      method: 'get'
    })
  }
}
