import request from '@/utils/request'
const modualName = 'recruit'
const apiName = 'recruit'

export function getList(params) {
  return request({
    url: `/${modualName}/${apiName}`,
    method: 'get',
    params,
  })
}

export function doInsert(data) {
  return request({
    url: `/${modualName}/${apiName}`,
    method: 'post',
    data,
  })
}

export function doEdit(data) {
  return request({
    url: `/${modualName}/${apiName}/${data.id}`,
    method: 'put',
    data,
  })
}

export function doDelete(recruitId) {
  return request({
    url: `/${modualName}/${apiName}/${recruitId}`,
    method: 'delete',
    recruitId,
  })
}

export function doDeleteBatch(ids) {
  return request({
    url: `/${modualName}/${apiName}/batch`,
    method: 'delete',
    params: ids
  })
}
