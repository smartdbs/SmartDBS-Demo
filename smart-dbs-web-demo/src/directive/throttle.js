/**
 * 节流：规定在一定时间范围内，事件被触发多次，只有一次是生效
 * 背景： 短信验证
 * 场景： 鼠标连续不断触发函数、监听滚动事件，是否滑到底部自动加载更多
 */
const throttle = {
  inserted: function(el, binding) {
    let valid = true
    el.addEventListener('click', () => {
      if (!valid) {
        return
      }
      valid = false
      setTimeout(() => {
        binding.value()
        valid = true
      }, 500)
    })
  }
}

export default throttle
