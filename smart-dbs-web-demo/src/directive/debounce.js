/**
 * 防抖：事件被触发n秒后再执行回调函数，如果n秒内又被触发，则重新计时
 * 背景：表单提交按钮多次点击，造成多次请求后端。造成数据混乱
 * 场景： input框chang、window.resize
 * 需求： 按钮在短时间内多次点击，使用防抖函数限制规定时间内只能点一次
 */
const debounce = {
  inserted: function(el, binding) {
    let timer
    el.addEventListener('click', () => {
      if (timer) {
        clearTimeout(timer)
      }
      timer = setTimeout(() => {
        binding.value()
      }, 500)
    })
  }
}

export default debounce
