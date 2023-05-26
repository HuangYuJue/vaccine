import request from "../utils/request";
export default {
  add(address){
    return request({
      url: `/admin/address/add`,
      method: 'post',
      data: address
    })
  },
  delete(id){
    return request({
      url: `/admin/address/delete/${id}`,
      method: 'get'
    })
  },
  select(limit,page,address){
    return request({
      url: `/admin/address/find/${limit}/${page}`,
      method: 'get',
      params: address
    })
  },
  update(address){
    return request({
      url: `/admin/address/update`,
      method: 'post',
      data: address
    })
  },
  cancel(id){
    return request({
      url: `/admin/address/cancel/${id}`,
      method: 'get'
    })
  },
  getById(id){
    return request({
      url: `/admin/address/getById/${id}`,
      method: 'get'
    })
  }
}
