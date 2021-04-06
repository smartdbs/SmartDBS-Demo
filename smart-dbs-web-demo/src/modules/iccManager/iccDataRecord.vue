<template>
  <topDownLayout>
    <div class="top-bar-cla" slot="top">
      <a-tabs v-model="activeKey">
        <a-tab-pane :key="1" tab="核验记录"></a-tab-pane>
        <a-tab-pane :key="2" tab="人证记录"> </a-tab-pane>
      </a-tabs>
    </div>
    <div slot="down">
      <transition name="component-fade" mode="out-in">
        <checkRecord ref="timeSlot" v-if="activeKey === 1"></checkRecord>
        <winessRecord
          ref="permissionGroup"
          v-if="activeKey === 2"
        ></winessRecord>
      </transition>
    </div>
  </topDownLayout>
</template>
<script>
import checkRecord from './iccSetting/checkRecord'
import winessRecord from './iccSetting/winessRecord'
export default {
  data() {
    return {
      activeKey: 1,
      deviceVerifyMode: {
        0: '指静脉/人脸/指纹/卡/密码 (自动识别)',
        1: '仅指纹',
        4: '仅卡',
        10: '卡+指纹',
        15: '人脸',
        16: '人脸+指纹',
        18: '人脸+卡',
        19: '人脸+指纹+卡',
        200: '其他',
        30: '仅身份证',
        31: '身份证加人脸',
        32: '身份证加人脸加可信',
        33: '身份证加指纹加人脸',
        34: '仅可信认证'
      },
      statusList: [
        {
          key: 0,
          label: '核验失败'
        },
        {
          key: 1,
          label: '核验成功'
        },
        {
          key: 2,
          label: '禁止人员'
        }
      ]
    }
  },
  provide() {
    return {
      deviceVerifyModeList: this.deviceVerifyMode,
      statusList: this.statusList
    }
  },
  components: {
    checkRecord,
    winessRecord
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
