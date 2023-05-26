import request from "../utils/request";
export default {
  select(limit, page, orderVo){
    return request({
      url: `/admin/order/selectPage/${limit}/${page}`,
      method: 'post',
      data: orderVo
    })
  },
  delete(id){
    return request({
      url: `/admin/order/delete/${id}`,
      method: 'get'
    })
  },
  vastatusById(id) {
    return request({
      url: `/admin/order/vacstatus/${id}`,
      method: 'get'
    })
  },
  getById(id){
    return request({
      url: `/admin/order/getById/${id}`,
      method: 'get'
    })
  }
}
