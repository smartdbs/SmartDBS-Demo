import debounce from './debounce'
import throttle from './throttle'

// 自定义指令
const directives = {
  debounce,
  throttle
}

// 批量注册
export default {
  install(Vue) {
    Object.keys(directives).forEach(key => {
      Vue.directive(key, directives[key])
    })
  }
}
