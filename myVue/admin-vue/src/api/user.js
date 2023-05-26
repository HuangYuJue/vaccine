import request from "../utils/request";
export default {
  findAllUser(limit, page, searchObj){
    return request({
      url: `/admin/user/findAllUser/${limit}/${page}`,
      method: 'post',
      data: searchObj
    })
  },
  getById(id){
    return request({
      url: `/admin/user/getById/${id}`,
      method: 'get'
    })
  },
  updateUser(user){
    return request({
      url: `/admin/user/updateUser`,
      method: 'post',
      data: user
    })
  },
  deleteUser(id){
    return request({
      url: `/admin/user/deleteUser/${id}`,
      method: 'post'
    })
  },
  batchRemove(ids){
    return request({
      url: `/admin/user/batchRemove`,
      method: 'delete',
      data: ids
    })
  }
}
