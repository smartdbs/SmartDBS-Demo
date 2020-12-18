<template>
  <a-config-provider :locale="locale">
    <a-spin
      size="large"
      :delay="delayTime"
      :spinning="this.$store.state.loadding"
      tip="加载中..."
    >
      <div id="app">
        <router-view />
      </div>
    </a-spin>
  </a-config-provider>
</template>
<script>
import zh_CN from 'ant-design-vue/lib/locale-provider/zh_CN'
import en_US from 'ant-design-vue/lib/locale-provider/en_US'

export default {
  data() {
    return {
      delayTime: 500,
      locale: zh_CN
    }
  },
  mounted() {
    let locale = localStorage.getItem('locale')
    if (locale) {
      this.$store.dispatch('setLocale', locale)
    }
  },
  watch: {
    '$store.state.locale': {
      handler: function(newValue) {
        if (newValue === 'zh_CN') {
          this.locale = zh_CN
        }
        if (newValue === 'en_US') {
          this.locale = en_US
        }
        this.$i18n.locale = newValue
      },
      immediate: true //第一次刷新页面时就会执行
    }
  },
  methods: {}
}
</script>
<style lang="less">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  min-height: 100vh;
  display: flex;
}

html,
body {
  height: 100%;
}
#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
