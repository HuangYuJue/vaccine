import request from "../utils/request";
import classify from "../views/vaccine/classify";
export default {
  select(limit, page, classify){
    return request({
      url: `/admin/classify/select/${limit}/${page}`,
      method: 'get',
      params: classify
    })
  },
  getById(id){
    return request({
      url: `/admin/classify/getById/${id}`,
      method: 'get'
    })
  },
  add(classify){
    return request({
      url: `/admin/classify/add`,
      method: 'post',
      data: classify
    })
  },
  update(classify){
    return request({
      url: `/admin/classify/update`,
      method: 'post',
      data: classify
    })
  },
  delete(id){
    return request({
      url: `/admin/classify/delete/${id}`,
      method: 'get'
    })
  },
  cancel(id){
    return request({
      url: `/admin/classify/cancel/${id}`,
      method: 'get'
    })
  }
}
