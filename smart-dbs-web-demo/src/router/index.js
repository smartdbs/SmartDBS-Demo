import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)
import enterprise from './modules/enterprise.js'
import device from './modules/device.js'
import index from './modules/index.js'
import att from './modules/att.js'
import acc from './modules/acc.js'
import sysInfo from './modules/sysInfo.js'
const routes = [index, enterprise, device, att, acc, sysInfo]

const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export const getRouterConfig = () => {
  return routes
}

const router = new VueRouter({
  routes
})
export default router
