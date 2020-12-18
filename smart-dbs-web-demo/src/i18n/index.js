import Vue from 'vue'
import VueI18n from 'vue-i18n'
import zh_CN from './language/zh_CN'
import en_US from './language/en_US'
Vue.use(VueI18n)
let i18n = new VueI18n({
  locale: 'zh_CN', // 定义默认语言为中文
  messages: {
    zh_CN: zh_CN,
    en_US: en_US
  }
})
export default i18n
