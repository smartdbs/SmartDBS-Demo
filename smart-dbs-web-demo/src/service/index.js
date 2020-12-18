/**
 * 请求后端服务插件及相关方法
 * @param {*} Vue
 */
import httpClient from './httpClient'
import serviceApi from './serviceApi.json'

/**
 * 拼装请求参数
 * @param {*} params
 */
let mergeParams = params => {
  const copyMessage = {
    lang: localStorage.getItem('locale') === 'en_US' ? 'en' : 'zh-CN',
    payload: {
      ...params
    }
  }
  return copyMessage
}

/**
 * 根据接口名称，请求后端接口服务
 * @param {*} apiName 接口名称
 * @param {*} params 请求参数
 * @param {*} options axios 配置参数
 */
let requestFn = (apiName, params, pager, options) => {
  if (!params) {
    params = {}
  }
  if (!pager) {
    pager = {}
  }
  params = JSON.parse(JSON.stringify(Object.assign(params, pager)))

  let apiObject = serviceApi[apiName]
  if (!apiObject) {
    //如果接口配置不存在抛出异常提示
    return Promise.reject({
      code: 'errorCode',
      message: '没有匹配的接口,请确认是否配置？'
    })
  } else {
    let method = ''
    if (apiObject.method === undefined || apiObject.method === '') {
      method = 'post'
    } else {
      method = apiObject.method.toLowerCase()
    }
    if (httpClient[method]) {
      return httpClient[method](
        apiObject.url,
        mergeParams(params),
        options
      ).catch(error => {
        console.error('统一处理请求错误')
        return Promise.reject(error)
      })
    } else {
      return Promise.reject({
        code: 'notMethod',
        message: '没有匹配的Ajax方法，请确认配置是否正确？'
      })
    }
  }
}

/**
 * 定义请求后端服务插件
 * @param {*} Vue
 */
let requestPlugin = Vue => {
  //添加全局方法
  Vue.request = function(apiName, params, pager, options) {
    return requestFn(apiName, params, pager, options)
  }
  // 添加实例方法
  Vue.prototype.$request = function(apiName, params, pager, options) {
    return requestFn(apiName, params, pager, options)
  }
  Vue.prototype.request = function(apiName, params, pager, options) {
    return requestFn(apiName, params, pager, options)
  }
}
export default requestPlugin
