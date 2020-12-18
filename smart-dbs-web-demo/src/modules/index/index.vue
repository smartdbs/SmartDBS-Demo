<template>
  <div class="index-cla">
    <img class="welcome-img-cla" src="@/assets/welcome.png" />
    <p class="tip-cla">{{ $t('common.welcome') }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {}
  },
  mounted() {},
  methods: {}
}
</script>

<style scoped lang="less">
.index-cla {
  text-align: center;
  min-height: calc(100vh - @layout-header-height - @breadcrumb-height - 24px);
}
.welcome-img-cla {
  margin-top: 190px;
  width: 30%;
  max-width: 400px;
}
.tip-cla {
  font-size: 16px;
  color: rgba(0, 0, 0, 0.4);
  letter-spacing: 0px;
  margin-top: 30px;
  font-weight: 600;
}
</style>
