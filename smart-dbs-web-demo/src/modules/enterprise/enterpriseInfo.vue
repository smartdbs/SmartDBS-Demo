<template>
  <div class="enterprise-info-cla">
    <a-form-model
      :model="companyInfo"
      layout="horizontal"
      :label-col="{ span: 3 }"
      :wrapper-col="{ span: 8 }"
      labelAlign="left"
      :rules="rules"
      ref="companyForm"
    >
      <div class="base-info-cla">{{ $t('company.baseInfo') }}</div>

      <a-form-model-item :label="$t('company.companyNo')" prop="companyCode">
        <!-- <a-input v-model="companyInfo.companyCode" /> -->
        {{ companyInfo.companyCode }}
      </a-form-model-item>

      <a-form-model-item :label="$t('company.name')" prop="companyName">
        <a-input v-model="companyInfo.companyName" />
      </a-form-model-item>

      <a-form-model-item :label="$t('company.webSite')" prop="website">
        <a-input v-model="companyInfo.website" />
      </a-form-model-item>

      <a-divider type="vertical" />
      <div class="verify-info-cla">{{ $t('company.contactInfo') }}</div>

      <a-form-model-item
        :label="$t('company.contactsUserName')"
        prop="formattedName"
      >
        <a-input v-model="companyInfo.formattedName" />
      </a-form-model-item>

      <a-form-model-item :label="$t('company.telephone')" prop="phone">
        <a-input v-model="companyInfo.phone" />
      </a-form-model-item>

      <a-form-model-item ref="email" :label="$t('company.email')" prop="email">
        <a-input v-model="companyInfo.email" />
      </a-form-model-item>
      <div style="text-align:right">
        <a-button type="primary" v-debounce="updateCompanyInfo">{{
          $t('common.save')
        }}</a-button>
      </div>
    </a-form-model>
  </div>
</template>

<script>
export default {
  data() {
    return {
      companyInfo: {},
      rules: {
        companyName: [{ required: true, message: '请输入内容', tigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.getCompanyInfo()
  },
  methods: {
    getCompanyInfo() {
      this.request('getCompanyInfo', {})
        .then(data => {
          if (data.code === '00') {
            this.companyInfo = data.data
          }
        })
        .catch(e => {
          this.errorMessage(e)
        })
    },
    updateCompanyInfo() {
      this.$refs.companyForm.validate(valid => {
        if (valid) {
          this.request('updateCompanyInfo', this.companyInfo)
            .then(data => {
              if (data.code === '00') {
                this.successMessage()
                this.getCompanyInfo()
              } else {
                this.errorMessage(data.message)
              }
            })
            .catch(e => this.errorMessage(e))
        }
      })
    }
  }
}
</script>

<style scoped lang="less">
.enterprise-info-cla {
  padding: 20px;
}
.verify-info-cla {
  height: 20px;
  line-height: 20px;
  border-left: 5px solid @error-color;
  padding-left: 10px;
  margin-bottom: 24px;
}
.base-info-cla {
  height: 20px;
  line-height: 20px;
  border-left: 5px solid @primary-color;
  padding-left: 10px;
  margin-bottom: 24px;
}
</style>
