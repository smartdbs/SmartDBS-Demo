<template>
  <a-layout-sider
    class="layout-sider-cla"
    theme="light"
    v-model="collapsed"
    :trigger="null"
    collapsible
  >
    <div class="logo" />

    <!-- :default-selected-keys="selectedKeys" -->
    <!-- @openChange="menuChange"
        :default-selected-keys="selectedKeys"
        :openKeys="openKeys" 
           @select="selectMenu"-->
    <div class="menu-cla">
      <a-menu
        :inline-collapsed="collapsed"
        @click="menuClick"
        theme="light"
        mode="inline"
        @select="selectMenu"
        @openChange="menuChange"
        :default-selected-keys="selectedKeys"
        :default-open-keys="defaultOpenKeys"
      >
        <template v-for="item of menuData">
          <a-sub-menu
            v-if="item.children && item.children.length > 0"
            :key="item.path"
          >
            <span slot="title">
              <icon-font
                class="menu-icon-cla"
                v-if="item.meta.icon"
                :type="item.meta.icon"
              />
              <span>{{ getMenuName(item) }}</span>
            </span>
            <a-menu-item
              :key="childredItem.path"
              v-for="childredItem of item.children"
            >
              <icon-font
                class="menu-icon-cla"
                v-if="childredItem.meta.icon"
                :type="childredItem.meta.icon"
              />
              <span>{{ getMenuName(childredItem) }}</span>
            </a-menu-item>
          </a-sub-menu>
          <a-menu-item v-else :key="item.path">
            <icon-font
              class="menu-icon-cla"
              v-if="item.meta.icon"
              :type="item.meta.icon"
            />
            <span>{{ getMenuName(item) }}</span>
          </a-menu-item>
        </template>
      </a-menu>
    </div>
  </a-layout-sider>
</template>

<script>
const lang = localStorage.getItem('locale')
const enMenu = require('@/i18n/language/en_US.js')
const cnMenu = require('@/i18n/language/zh_CN.js')
import { getRouterConfig } from '@/router'
import { filterMenuDate } from '@/utils/index.js'
import path from 'path'
export default {
  data() {
    return {
      menuData: [],
      openKeys: [],
      selectedKeys: [],
      defaultOpenKeys: []
    }
  },
  mounted() {
    this.menuData = filterMenuDate(getRouterConfig())
  },
  created() {
    this.selectedKeys = sessionStorage.getItem('keys')
      ? [sessionStorage.getItem('keys')]
      : ['/']
    this.defaultOpenKeys = sessionStorage.getItem('menu')
      ? [sessionStorage.getItem('menu')]
      : ['/']
  },
  methods: {
    selectMenu({ selectedKeys }) {
      this.selectedKeys = selectedKeys
    },
    menuChange(openKeys) {
      if (openKeys.length === 1) {
        this.openKeys = openKeys
      } else {
        this.openKeys = openKeys[openKeys.length - 1]
      }
      sessionStorage.setItem('menu', this.openKeys)
    },
    getMenuName(item) {
      if (lang === 'en_US') {
        let menuList = enMenu.default.menu
        return menuList[item.meta.title]
      } else {
        let menuList = cnMenu.default.menu
        return menuList[item.meta.title]
      }
      // return item.meta.title
    },
    menuClick({ key, keyPath }) {
      if (key.startsWith('/')) {
        this.$router.push(key)
      } else {
        var basePath =
          keyPath && keyPath.length ? keyPath[keyPath.length - 1] : '/'
        this.$router.push(path.resolve(basePath, key))
      }
      sessionStorage.setItem('keys', key)
      this.selectedKeys = [key]
    }
  },
  props: {
    collapsed: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped lang="less">
/deep/ .ant-menu-item {
  height: 50px;
  line-height: 50px;
  font-weight: 500;
  &:hover {
    background-image: linear-gradient(
      270deg,
      rgba(50, 184, 76, 0.5) 0%,
      rgba(50, 184, 76, 0) 100%
    );
  }
}
/deep/ .ant-menu-inline,
.ant-menu-vertical {
  border-right: none;
}
.ant-menu-item-selected {
  background-image: linear-gradient(
    270deg,
    rgba(50, 184, 76, 0.5) 0%,
    rgba(50, 184, 76, 0) 100%
  );
}
.logo {
  box-shadow: 0 3px 8px 0 rgba(101, 178, 47, 0.2),
    0 3px 8px 0 rgba(101, 178, 47, 0.2);
  height: 60px;
  padding: 16px;
  background-image: url('~~@/assets/logo.png');
  background-size: 60% 60%;
  background-repeat: no-repeat;
  background-position: center;
  position: absolute;
  width: 100%;
  top: 0px;
}

.menu-cla {
  height: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  &::-webkit-scrollbar {
    width: 3px; /*高宽分别对应横竖滚动条的尺寸*/
    height: 1px;
  }
  &::-webkit-scrollbar-thumb {
    border-radius: 3px;
    background: rgba(0, 0, 0, 0.2);
  }
  &::-webkit-scrollbar-track {
    border-radius: 35px;
    background: transparent;
  }
}

.menu-icon-cla {
  margin-right: 10px;
  font-size: 20px;
}
/deep/.ant-layout-sider-children {
  padding-top: 60px;
  height: 100vh;
  position: relative;
  overflow: hidden;
}
</style>
