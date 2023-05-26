import request from "@/utils/request";
export default {
  findByName(vacname){
    return request({
      url: `/api/users/vaccine/findByName/${vacname}`,
      method: 'get'
    })
  },
  select(limit,page,classifyId,vacname){
    return request({
      url: `/api/users/vaccine/findVaccines/${limit}/${page}/${classifyId}`,
      method: 'get',
      params: vacname
    })
  },
  getDetail(id){
    return request({
      url: `/api/users/vaccine/findVaccine/${id}`,
      method: 'get'
    })
  }
}
