<template>
  <a-breadcrumb>
    <template v-for="item of breadcrumbList">
      <a-breadcrumb-item v-if="isShow(item)" :key="item.id">
        {{ getMenuName(item) }}
      </a-breadcrumb-item>
    </template>
  </a-breadcrumb>
</template>

<script>
const lang = localStorage.getItem('locale')
const enMenu = require('@/i18n/language/en_US.js')
const cnMenu = require('@/i18n/language/zh_CN.js')
export default {
  data() {
    return {
      breadcrumbList: []
    }
  },
  mounted() {},
  watch: {
    $route: {
      handler: function() {
        this.getBreadcrumb()
      },
      immediate: true
    }
  },
  methods: {
    isShow(item) {
      if (item.meta && item.meta.breadcrumbShow !== undefined) {
        return item.meta.breadcrumbShow
      }
      if (item.meta && item.meta.hidden) {
        return false
      }
      return true
    },
    getMenuName(item) {
      if (lang === 'en_US') {
        let enBreadcrumb = enMenu.default.menu
        return item.meta
          ? enBreadcrumb[item.meta.title]
          : enBreadcrumb[item.name]
      } else {
        let cnBreadcrumb = cnMenu.default.menu
        return item.meta
          ? cnBreadcrumb[item.meta.title]
          : cnBreadcrumb[item.name]
      }
      // return item.meta ? item.meta.title : item.name
    },
    getBreadcrumb() {
      this.breadcrumbList = this.$route.matched
    }
  }
}
</script>

<style scoped lang="less"></style>
