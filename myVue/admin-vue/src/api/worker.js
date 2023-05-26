import request from "../utils/request"
export default {
  select(limit,page,worker){
    return request({
      url: `/admin/worker/select/${limit}/${page}`,
      method: 'post',
      data: worker
    })
  },
  add(worker){
    return request({
      url: `/admin/worker/add`,
      method: 'post',
      data: worker
    })
  },
  update(worker){
    return request({
      url: `/admin/worker/update`,
      method: 'post',
      data: worker
    })
  },
  delete(id){
    return request({
      url: `/admin/worker/delete/${id}`,
      method: 'get',
    })
  },
  getById(id){
    return request({
      url: `/admin/worker/getById/${id}`,
      method: 'get'
    })
  }
}
