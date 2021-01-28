<template>
  <div class="system-cla">
    <div class="application-info-cla">{{ $t('sys.applicationInfo') }}</div>
    <div class="info-detial-cla border_cla">
      <p><label>AppKey ：</label>{{ appInfo.AppKey }}</p>
      <p>
        <label>AppSecret ：</label>{{ AppSecret }}
        <i
          :class="[
            showAppSecret ? 'iconfont zk-icon-eye' : 'iconfont zk-icon-biyan',
            'pwd-cla'
          ]"
          @click="showAttrs('AppSecret')"
        />
      </p>

      <p>
        <label>{{ $t('sys.account') }} ：</label>{{ appInfo.Account }}
      </p>
      <p>
        <label>{{ $t('sys.password') }}：</label>{{ Password }}
        <i
          :class="[
            showPassword ? 'iconfont zk-icon-eye' : 'iconfont zk-icon-biyan',
            'pwd-cla'
          ]"
          @click="showAttrs('Password')"
        />
      </p>
    </div>
    <div class="node-info-cla">{{ $t('sys.nodeInfo') }}</div>
    <div class="info-detial-cla">
      <p>
        <label>{{ $t('sys.nodeAddress') }} ：</label>{{ nodeInfo.node }}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      appInfo: {
        AppKey: 'v11vf9e1b73b478gaag15ched32dy861',
        AppSecret: 'v48696wc18g6hff2es8601',
        Account: 'test',
        Password: '123456'
      },
      nodeInfo: {
        node: 'api.dbs.zkclouds.com'
      },
      showAppSecret: false,
      showPassword: false,
      AppSecret: '*********************',
      Password: '*********'
    }
  },

  methods: {
    showAttrs(type) {
      if (type === 'AppSecret') {
        if (!this.showAppSecret) {
          this.showAppSecret = true
          this.AppSecret = this.appInfo.AppSecret
        } else {
          this.showAppSecret = false
          this.AppSecret = '*********************'
        }
      }
      if (type === 'Password') {
        if (!this.showPassword) {
          this.showPassword = true
          this.Password = this.appInfo.Password
        } else {
          this.showPassword = false
          this.Password = '*********'
        }
      }
    }
  }
}
</script>

<style scoped lang="less">
.system-cla {
  padding: 24px;
  .application-info-cla {
    border-left: 4px solid @primary-color;
    padding-left: 16px;
  }
  .node-info-cla {
    border-left: 4px solid @warning-color;
    padding-left: 16px;
    margin-bottom: 20px;
  }
  .info-detial-cla {
    padding-left: 20px;
    margin: 15px 0;
  }
  .border_cla {
    border-bottom: 1px solid @border-color-base;
  }
  p {
    label {
      width: 135px;
      display: inline-block;
      text-align: right;
    }
  }
}
.pwd-cla {
  cursor: pointer;
}
</style>
