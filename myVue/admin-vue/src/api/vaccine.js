import request from "../utils/request";
export default {
  add(vaccine){
    return request({
      url: `/admin/vaccine/add`,
      method: 'post',
      data: vaccine
    })
  },

  select(limit, page, vaccine){
    return request({
      url: `/admin/vaccine/select/${limit}/${page}`,
      method: 'get',
      params: vaccine
    })
  },

  delete(id) {
    return request({
      url: `/admin/vaccine/delete/${id}`,
      method: 'get'
    });
  },

  cancel(id){
    return request({
      url: `/admin/vaccine/cancel/${id}`,
      method: 'get'
    })
  },

  update(vaccine){
    return request({
      url: `/admin/vaccine/update`,
      method: 'post',
      data: vaccine
    })
  },

  getById(id){
    return request({
      url: `/admin/vaccine/getById/${id}`,
      method: 'get'
    })
  }
}
