<template>
  <a-layout class="components-layout-custom-trigger">
    <zkSiderMenu :collapsed="collapsed"></zkSiderMenu>
    <a-layout class="layout-right-cla">
      <div class="layout-top-cla">
        <a-layout-header :style="headerStyle" class="layout-header-cla">
          <a-icon
            class="trigger"
            :type="collapsed ? 'menu-unfold' : 'menu-fold'"
            @click="() => (collapsed = !collapsed)"
          />
          <changeLanguage
            style="float: right; margin-right: 24px"
          ></changeLanguage>
        </a-layout-header>
      </div>
      <zk-breadcrumb></zk-breadcrumb>
      <div class="content-cla">
        <router-view />
      </div>
    </a-layout>
  </a-layout>
</template>
<script>
import zkSiderMenu from './zkSiderMenu'
import zkBreadcrumb from './zkBreadcrumb'
export default {
  data() {
    return {
      collapsed: false,
      siderWidth: 200
    }
  },
  computed: {
    headerStyle() {
      var width = this.collapsed ? 80 : this.siderWidth
      return {
        'padding-right': width + 'px'
      }
    }
  },
  components: {
    zkSiderMenu,
    zkBreadcrumb
  }
}
</script>
<style scoped lang="less">
.components-layout-custom-trigger {
  .trigger {
    font-size: 18px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color 0.3s;
    &:hover {
      color: #1890ff;
    }
  }
}
.layout-right-cla {
  height: 100vh;
  overflow-y: auto;
  padding-top: @layout-header-height+ @breadcrumb-height;
  position: relative;
  .layout-top-cla {
    position: fixed;
    top: 0px;
    width: 100%;
    z-index: 1000;
    .layout-header-cla {
      background-color: #ffff !important;
      padding-left: 0px;
    }
  }
}
.content-cla {
  margin: 0px 24px 24px 24px;
  background-color: #ffff;
  border-radius: 2px;
  min-height: @content-height;
}
.copyright-cla {
  position: absolute;
  bottom: 0px;
  text-align: center;
  width: 100%;
  font-size: 12px;
  color: rgba(0, 0, 0, 0.25);
}

.ant-breadcrumb {
  position: absolute;
  top: 60px;
  width: 100%;
}
</style>
