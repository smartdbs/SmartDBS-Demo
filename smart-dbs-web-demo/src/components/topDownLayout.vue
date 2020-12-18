<template>
  <div class="top-down-layout-cla">
    <div class="top-part-cla">
      <slot name="top"></slot>
    </div>
    <div class="down-part-cla">
      <slot name="down"></slot>
    </div>
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
.top-down-layout-cla {
  min-height: @content-height;
  background-color: @layout-body-background;
  .top-part-cla {
    position: relative;
    height: 64px;
    background-color: #ffffff;
    border-radius: 2px;
    line-height: 64px;
  }
  .down-part-cla {
    position: relative;
    margin-top: 12px;
    min-height: calc(
      100vh - @layout-header-height - @breadcrumb-height - 100px
    );
    background-color: #ffffff;
    border-radius: 2px;
    margin-bottom: 24px;
  }
}
</style>
