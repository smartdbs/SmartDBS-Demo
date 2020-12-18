import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import './registerComponent'
import router from './router'
import store from './store'
import i18n from '@/i18n'
import service from '@/service'
import mixins from '@/mixins'
require('./styles/index.less')

Vue.config.productionTip = false
// Vue.use(Antd)
Vue.use(service)
Vue.use(mixins)
window.vm = new Vue({
  router,
  i18n,
  store,
  render: h => h(App)
}).$mount('#app')
