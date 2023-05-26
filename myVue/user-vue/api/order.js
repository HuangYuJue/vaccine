import request from "@/utils/request";
export default {
  addOrder(order){
    return request({
      url: `/api/users/order/add`,
      method: 'post',
      data: order
    })
  },
  cancel(id){
    return request({
      url: `/api/users/order/cancel/${id}`,
      method: 'get'
    })
  },
  select(limit,page,orderVo){
    return request({
      url: `/api/users/order/selectOld/${limit}/${page}`,
      method: 'post',
      data: orderVo
    })
  },
}
