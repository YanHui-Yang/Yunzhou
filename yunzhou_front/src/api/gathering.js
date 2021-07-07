import request from '@/utils/request'
const modualName = 'gathering'
const controllerName = 'gathering'

export function getList(params) {
  return request({
    url: `/${modualName}/${controllerName}`,
    method: 'get',
    params,
  })
}

export function doInsert(data) {
  return request({
    url: `/${modualName}/${controllerName}`,
    method: 'post',
    data,
  })
}

export function doEdit(data) {
  return request({
    url: `/${modualName}/${controllerName}/${data.id}`,
    method: 'put',
    data,
  })
}

export function doDelete(gatheringId) {
  return request({
    url: `/${modualName}/${controllerName}/${gatheringId}`,
    method: 'delete',
    gatheringId,
  })
}

export function doDeleteBatch(ids) {
  return request({
    url: `/${modualName}/${controllerName}/batch`,
    method: 'delete',
    params: ids
  })
}
