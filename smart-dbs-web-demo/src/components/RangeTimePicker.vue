<template>
  <div class="ranger-time-cla">
    <div
      :class="{ 'ranger-input-cla': true, 'focus-cla': showPopper }"
      @click.stop="focusFn"
    >
      <input v-model="startValue" />
      <span>{{ $t('common.to') }}</span>
      <input v-model="endValue" />
    </div>

    <zkPopper ref="showPanel" class="zk-popper-cla" :visible="showPopper">
      <div class="popper-content-cla">
        <div class="time-pick-cla">
          <div></div>
          <div>{{ $t('common.startTime') }}</div>
          <div class="time-box-cla">
            <ul ref="startHour" @scroll="scrollFn(arguments[0], 'startHour')">
              <li :key="item + 'before'" v-for="item of 5"></li>
              <li
                :class="{ 'disable-cla': false }"
                :key="item"
                v-for="item of 24"
              >
                {{ showValue(item - 1) }}
              </li>
              <li :key="item + 'after'" v-for="item of 5"></li>
            </ul>
            <ul
              ref="startMinute"
              @scroll="scrollFn(arguments[0], 'startMinute')"
            >
              <li :key="item + 'before'" v-for="item of 5"></li>
              <li
                :class="{ 'disable-cla': false }"
                :key="item"
                v-for="item of 60"
              >
                {{ showValue(item - 1) }}
              </li>
              <li :key="item + 'after'" v-for="item of 5"></li>
            </ul>
          </div>
        </div>

        <div class="time-pick-cla">
          <div>{{ $t('common.endTime') }}</div>
          <div class="time-box-cla">
            <ul ref="endHour" @scroll="scrollFn(arguments[0], 'endHour')">
              <li :key="item + 'before'" v-for="item of 5"></li>
              <li
                :class="{ 'disable-cla': false }"
                :key="item"
                v-for="item of 24"
              >
                {{ showValue(item - 1) }}
              </li>
              <li :key="item + 'after'" v-for="item of 5"></li>
            </ul>
            <ul ref="endMinute" @scroll="scrollFn(arguments[0], 'endMinute')">
              <li :key="item + 'before'" v-for="item of 5"></li>
              <li
                :class="{ 'disable-cla': false }"
                :key="item"
                v-for="item of 60"
              >
                {{ showValue(item - 1) }}
              </li>
              <li :key="item + 'after'" v-for="item of 5"></li>
            </ul>
          </div>
        </div>
      </div>
    </zkPopper>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showPopper: false,
      startHour: '',
      startMinute: '',
      endHour: '',
      endMinute: ''
    }
  },
  methods: {
    scrollFn(e, type) {
      setTimeout(() => {
        let value = Math.round(e.target.scrollTop / 20)
        let strValue = value < 10 ? '0' + value : '' + value
        if (type == 'startHour') {
          if (isNaN(parseInt(this.startMinute))) {
            this.startMinute = '00'
          }
          this.startHour = strValue
        }

        if (type == 'startMinute') {
          if (isNaN(parseInt(this.startHour))) {
            this.startHour = '00'
          }
          this.startMinute = strValue
        }

        if (type == 'endHour') {
          if (isNaN(parseInt(this.endMinute))) {
            this.endMinute = '00'
          }
          this.endHour = strValue
        }

        if (type == 'endMinute') {
          if (isNaN(parseInt(this.endHour))) {
            this.endHour = '00'
          }
          this.endMinute = strValue
        }

        this.$emit('input', [this.startValue, this.endValue])
      }, 100)
    },

    getStartTime() {
      if (isNaN(parseInt(this.startHour))) {
        return 0
      }
      let endMinute = 0
      if (parseInt(this.endMinute)) {
        endMinute = parseInt(this.endMinute)
      }
      return parseInt(this.startHour) + endMinute
    },

    getEndTime() {
      if (isNaN(parseInt(this.endHour))) {
        return 0
      }
      let startMinute = 0
      if (parseInt(this.startMinute)) {
        startMinute = parseInt(this.startMinute)
      }
      return parseInt(this.endHour) + startMinute
    },

    hidenPopper(e) {
      //隐藏时间选择弹框
      if (
        !this.isClickOutsideEl(e, this.$refs.showPanel.$el) &&
        this.showPopper
      ) {
        this.$emit('input', [this.startValue, this.endValue])
        this.showPopper = false
      }
    },
    isClickOutsideEl(evt, domEl) {
      //是否在点击的区域外
      let rect = domEl.getBoundingClientRect()
      let clientX = evt.clientX
      let clientY = evt.clientY
      return (
        clientX >= rect.left &&
        clientX <= rect.left + rect.width &&
        clientY >= rect.top &&
        clientY <= rect.top + rect.height
      )
    },
    focusFn() {
      this.showPopper = true
      setTimeout(() => {
        this.scrollTop('startHour', this.startHour)
        this.scrollTop('startMinute', this.startMinute)
        this.scrollTop('endHour', this.endHour)
        this.scrollTop('endMinute', this.endMinute)
      }, 100)
    },

    scrollTop(tag, value) {
      if (value) {
        value = parseInt(value)
      }
      this.$refs[tag].scrollTop = value * 20
    },
    showValue(item) {
      return item < 10 ? '0' + item : '' + item
    }
  },
  computed: {
    startHourIsable() {
      if (this.endHour === '') {
        return false
      }
      return false
    },
    startMinuteIsable() {
      if (this.endHour === '') {
        return false
      }
      return true
    },
    endHourIsable() {
      return false
    },
    endMinuteIsable() {
      return false
    },
    startValue() {
      if (this.startHour + this.startMinute) {
        return this.startHour + ':' + this.startMinute
      } else {
        return ''
      }
    },
    endValue() {
      if (this.endHour + this.endMinute) {
        return this.endHour + ':' + this.endMinute
      } else {
        return ''
      }
    }
  },
  watch: {
    value: {
      handler: function(val) {
        if (val instanceof Array && val.length === 2) {
          let startValue = val[0]
          let endValue = val[1]
          if (startValue) {
            let arry = startValue.split(':')
            this.startHour = arry[0]
            this.startMinute = arry[1]
          }
          if (endValue) {
            let arry = endValue.split(':')
            this.endHour = arry[0]
            this.endMinute = arry[1]
          }
        } else {
          this.startHour = ''
          this.startMinute = ''
          this.endHour = ''
          this.endMinute = ''
        }
      },
      immediate: true,
      deep: true
    }
  },
  props: ['value'],
  destroyed() {
    document.body.removeEventListener('click', this.hidenPopper)
  },
  mounted() {
    document.body.addEventListener('click', this.hidenPopper)
  }
}
</script>

<style scoped lang="less">
.ranger-time-cla {
  display: flex;
}
.ranger-input-cla {
  border: 1px solid gray;
  display: flex;
  height: 32px;
  line-height: 32px;
  align-items: center;
  flex-wrap: nowrap;
  input {
    text-align: center;
    border: none;
    height: 30px;
    &:focus {
      padding: 0px;
      outline: none;
    }
  }
}
.focus-cla {
  border: 1px solid @primary-color;
  padding: 0px;
}
.zk-popper-cla {
}
.popper-content-cla {
  display: flex;
}
.time-pick-cla {
  text-align: center;
}
.time-box-cla {
  display: flex;
  border: 1px solid #e5e5e5;
  margin: 10px 10px 10px 20px;
  position: relative;
  padding: 0px 15px;
  &::before {
    content: '';
    height: 1px;
    background-color: #e5e5e5;
    width: 90%;
    top: 100px;
    left: 5%;
    position: absolute;
    z-index: 0px;
  }
  &::after {
    content: '';
    height: 1px;
    background-color: #e5e5e5;
    width: 90%;
    top: 120px;
    left: 5%;
    position: absolute;
    z-index: 0px;
  }
  ul {
    height: 220px;
    overflow-y: scroll;
    overflow-x: hidden;
    padding: 0px;
    margin: 0px;
    .disable-cla {
      color: #766f6f38;
    }
    li {
      list-style: none;
      width: 50px;
      height: 20px;
      line-height: 20px;
    }
  }
}
</style>
