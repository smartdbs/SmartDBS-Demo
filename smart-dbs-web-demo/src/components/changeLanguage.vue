<template>
  <a-dropdown>
    <a class="ant-dropdown-link" @click="e => e.preventDefault()"
      >{{ $t('common.changeLanguage') }} <a-icon type="down"
    /></a>
    <a-menu @click="changeLanguage" slot="overlay">
      <a-menu-item key="zh_CN"> 中文 </a-menu-item>
      <a-menu-item key="en_US"> English </a-menu-item>
    </a-menu>
  </a-dropdown>
</template>

<script>
export default {
  data() {
    return {
      lang: 'zh_CN'
    }
  },
  mounted() {
    if (localStorage.getItem('locale')) {
      this.lang = localStorage.getItem('locale')
    } else {
      localStorage.setItem('locale', this.lang)
    }
  },
  methods: {
    changeLanguage({ key }) {
      this.lang = key
      localStorage.setItem('locale', this.lang)
      this.$store.dispatch('setLocale', this.lang)
      window.location.reload()
    }
  }
}
</script>

<style scoped lang="less"></style>
