/**
 * 根据路由配置信息，处理显示菜单的数据
 * @param {*} menu
 */
export const filterMenuDate = menu => {
  const menus = menu.filter(menuItem => {
    if (menuItem.meta && menuItem.meta.hidden) {
      return false
    } else {
      if (menuItem.children && menuItem.children.length) {
        let children = filterMenuDate(menuItem.children)
        if (children && children.length > 0) {
          menuItem.children = children
        } else {
          delete menuItem.children
        }
      }
      return true
    }
  })
  return menus
}

/**
 * 获取当前时间
 * return  createTime yyyy-mm-dd hh:mm:ss
 */
export const getCurrentTime = () => {
  let date = new Date()
  let hours = date.getHours() < 10 ? +'0' + date.getHours() : date.getHours()
  let minutes =
    date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
  return (
    date.getFullYear() +
    '-' +
    (date.getMonth() + 1) +
    '-' +
    date.getDate() +
    ' ' +
    hours +
    ':' +
    minutes +
    ':' +
    date.getSeconds()
  )
}
