/* eslint-disable */
/**
 * axios 请求后端服务实例封装及相关拦截处理
 */
import axios from 'axios'
import store from '@/store'
let httpClient = {}
// 创建axios实例

axios.defaults.withCredentials = true
let service = axios.create({
  baseURL: '/api/', // api的base_url
  timeout: 50000 // 请求超时时间
})

// request拦截器 axios的一些配置
service.interceptors.request.use(
  config => {
    store.state.loadding = true
    config.headers['X-CSRF-TOKEN'] = 'TOKEN'
    let locale = localStorage.getItem('locale')
    if (locale) {
      config.headers['lang'] = locale
    }
    config.headers['Cache-Control'] = 'no-cache'
    config.headers['Pragma'] = 'no-cache'
    config.headers['Accept'] = 'application/json'
    return config
  },
  error => {
    store.state.loadding = false
    return Promise.reject(error)
  }
)

//响应拦截
service.interceptors.response.use(
  response => {
    store.state.loadding = false
    return response
  },
  error => {
    store.state.loadding = false
    return Promise.reject(error)
  }
)
;['get', 'delete', 'head', 'jsonp'].forEach(method => {
  httpClient[method] = function(url, param = {}, options) {
    return new Promise((resolve, reject) => {
      service[method](url, param, options)
        .then(data => {
          if (data) {
            resolve(data.data)
          } else {
            reject(data)
          }
        })
        .catch(data => {
          reject(data)
        })
    })
  }
})
;['post', 'put', 'patch'].forEach(method => {
  httpClient[method] = function(url, body, options) {
    return new Promise((resolve, reject) => {
      service[method](url, body, options)
        .then(data => {
          if (data) {
            resolve(data.data)
          } else {
            reject(data)
          }
        })
        .catch(data => {
          reject(data)
        })
    })
  }
})

/**
 * form 表单的提交
 * @param {*} options
 */
let submit = (
  options = {
    params: {},
    method: 'POST',
    action: '',
    target: '_blank'
  }
) => {
  var form = document.createElement('form')
  form.method = options.method
  form.action = options.action
  form.target = options.target
  form.style.display = 'none'
  for (let param in options.params) {
    var element = document.createElement('input')
    element.name = param
    element.value = options.params[param]
    form.appendChild(element)
  }
  document.body.appendChild(form)
  form.submit()
  document.body.removeChild(form)
}

/**
 * ajax 下载资源方法封装
 * @param {} url
 * @param {*} params
 */
let downloadUrl = (url, params) => {
  service({
    method: 'post',
    url: url,
    responseType: 'blob',
    data: params
  })
    .then(response => {
      if (!response) {
        return
      }
      var blob = new Blob([response.data], {
        type:
          'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'
      })
      let fileName = response.headers['content-disposition']
      fileName = fileName.split('=')[1]
      if (!fileName) {
        fileName = 'fileName.xlsx'
      }
      var downloadElement = document.createElement('a')
      var href = window.URL.createObjectURL(blob) //创建下载的链接
      downloadElement.href = href
      downloadElement.download = fileName //下载后文件名
      document.body.appendChild(downloadElement)
      downloadElement.click() //点击下载
      if ('msSaveOrOpenBlob' in navigator) {
        window.navigator.msSaveOrOpenBlob(href, fileName) //IE下载
      }

      document.body.removeChild(downloadElement) //下载完成移除元素
      window.URL.revokeObjectURL(href) //释放掉blob对象
    })
    .catch(error => {
      console.log(error)
    })
}

httpClient.submit = submit
httpClient.downloadUrl = downloadUrl

export default httpClient
