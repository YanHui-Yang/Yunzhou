import request from '@/utils/request'
import { encryptedData } from '@/utils/encrypt'
import { loginRSA, tokenName } from '@/config'
const modualName = 'user'
const controllerName = 'admin'

export async function login(data) {
  if (loginRSA) {
    data = await encryptedData(data)
  }
  return request({
    url: `/${modualName}/${controllerName}/login`,
    method: 'post',
    data,
  })
}

export function getUserInfo(accessToken) {
  return request({
    url: `/${modualName}/${controllerName}/info`,
    method: 'get',
    data: {
      [tokenName]: accessToken,
    },
  })
}

export function logout() {
  return request({
    url: `/${modualName}/${controllerName}/logout`,
    method: 'post',
  })
}

// export function register() {
//   return request({
//     url: '/register',
//     method: 'post',
//   })
// }
