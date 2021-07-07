import request from '@/utils/request'
const modualName = 'base'
const controllerName = 'label'

export function getList(params) {
  return request({
    url: `/${modualName}/${controllerName}`,
    method: 'get',
    params,
  })
}

export function doEdit(data) {
  console.log(data);
  return request({
    url: `/${modualName}/${controllerName}/${data.id}`,
    method: 'put',
    data,
  })
}

export function doInsert(data) {
  return request({
    url: `/${modualName}/${controllerName}`,
    method: 'post',
    data,
  })
}

export function doDelete(labelId) {
  return request({
    url: `/${modualName}/${controllerName}/${labelId}`,
    method: 'delete',
    labelId
  })
}

export function doDeleteBatch(ids) {
  return request({
    url: `/${modualName}/${controllerName}/batch`,
    method: 'delete',
    params: ids
  })
}
