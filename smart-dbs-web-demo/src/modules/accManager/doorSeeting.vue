<template>
  <topDownLayout>
    <div class="top-bar-cla" slot="top">
      <a-tabs v-model="activeKey">
        <a-tab-pane :key="1" :tab="$t('acc.timeSlot')"> </a-tab-pane>
        <a-tab-pane :key="2" :tab="$t('acc.permissionCroup')"></a-tab-pane>

        <a-button
          @click.stop="$refs['timeSlot'].showCreate()"
          v-show="activeKey === 1"
          slot="tabBarExtraContent"
          type="primary"
        >
          {{ $t('acc.createTimeSlot') }}
        </a-button>
        <a-button
          @click.stop="$refs['permissionGroup'].showCreate()"
          v-show="activeKey === 2"
          slot="tabBarExtraContent"
          type="primary"
        >
          {{ $t('acc.createPermissionCroup') }}
        </a-button>
      </a-tabs>
    </div>
    <div slot="down">
      <transition name="component-fade" mode="out-in">
        <timeSlot ref="timeSlot" v-if="activeKey === 1"></timeSlot>
        <permissionGroup
          ref="permissionGroup"
          v-if="activeKey === 2"
        ></permissionGroup>
      </transition>
    </div>
  </topDownLayout>
</template>
<script>
import permissionGroup from './doorSeeting/permissionGroup'
import timeSlot from './doorSeeting/timeSlot'
export default {
  data() {
    return {
      activeKey: 1
    }
  },
  components: {
    timeSlot,
    permissionGroup
  },
  methods: {}
}
</script>

<style scoped lang="less">
.top-bar-cla {
  padding: 10px 20px 0px 20px;
  height: 64px;
  line-height: 64px;
  /deep/.ant-tabs-bar {
    border-bottom: none;
    margin: 0px;
  }
  /deep/.ant-tabs-nav {
    height: 45px;
  }
  /deep/.ant-tabs-ink-bar {
    height: 3px;
  }
}
.component-fade-enter-active,
.component-fade-leave-active {
  transition: opacity 0.3s ease;
}
.component-fade-enter, .component-fade-leave-to
/* .component-fade-leave-active for below version 2.1.8 */ {
  opacity: 0;
}
</style>
