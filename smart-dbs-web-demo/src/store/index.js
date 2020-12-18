import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    loadding: false,
    locale: 'zh_CN'
  },
  mutations: {
    showloadding(state, load) {
      state.loadding = load
    },
    setLocale(state, locale) {
      state.locale = locale
    }
  },
  actions: {
    setloadding(context, load) {
      context.commit('showloadding', load)
    },
    setLocale(context, locale) {
      context.commit('setLocale', locale)
    }
  },
  getters: {
    isloading: state => {
      return state.loadding
    },
    getLocale(state) {
      return state.locale
    }
  }
})
