<template>
  <div>
    <div class="device-top-bar">
      <a-form-model layout="inline" :model="searchForm">
        <a-form-model-item>
          <a-select
            style="width: 130px"
            v-model="searchForm.valueType"
            :placeholder="$t('common.searchKey')"
          >
            <a-select-option value="deviceSn">
              {{ $t('device.deviceSn') }}
            </a-select-option>
            <a-select-option value="deviceName">
              {{ $t('device.deviceName') }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item>
          <a-input-search
            style="width: 200px"
            v-model="searchForm.value"
            :placeholder="$t('common.searchKey')"
            enter-button
            @search="onSearch"
            allow-clear
          />
        </a-form-model-item>
      </a-form-model>
    </div>
    <template v-if="deviceList.length !== 0">
      <ul class="device-list-cla">
        <li
          @click.stop="selectItemFn(item)"
          v-for="item of deviceList"
          :key="item.id"
          :class="{ 'active-cla': item.id == selectItem.id }"
        >
          <div>
            <div style="font-size: 16px">{{ item.alias }}</div>
            <div>{{ item.sn }}</div>
          </div>
          <div class="online-cla" v-if="item.status === 1">
            {{ $t('device.onLine') }}
          </div>
          <div class="offline-cla" v-else>{{ $t('device.offLine') }}</div>
        </li>
      </ul>

      <div class="pagination-box-cla">
        <a-pagination
          v-model="pager.curPage"
          show-quick-jumper
          size="small"
          :page-size.sync="pager.pageSize"
          :total="pager.total"
          @change="pageChange(arguments[0], arguments[1], getDeviceList)"
          @showSizeChange="
            pageChange(arguments[0], arguments[1], getDeviceList)
          "
        ></a-pagination>
      </div>
    </template>
    <template v-else>
      <div class="search-no-data">
        <img src="@/assets/search-no-data.png" style="width:250px" />
        <div>{{ $t('common.noData') }}</div>
      </div>
    </template>
  </div>
</template>

<script>
export default {
  data() {
    return {
      deviceList: [],
      selectItem: {},
      searchForm: {
        valueType: 'deviceSn',
        value: ''
      }
    }
  },
  watch: {
    'searchForm.value': {
      handler(val) {
        if (val === '') {
          this.getDeviceList()
        }
      }
    }
  },
  mounted() {
    this.getDeviceList()
  },
  methods: {
    onSearch() {
      //查询设备列表
      this.pager.curPage = 1
      this.getDeviceList()
    },
    selectItemFn(item) {
      this.selectItem = item
      this.$emit('selectItem', item)
    },
    getDeviceList() {
      //获取设备列表
      let params = {
        type: 0
      }
      if (this.searchForm.valueType === 'deviceSn') {
        params.sn = this.searchForm.value
      } else {
        params.alias = this.searchForm.value
      }
      this.request('getDeviceList', params, this.pager)
        .then(data => {
          if (data.code === '00') {
            this.deviceList = data.data
            if (this.deviceList && this.deviceList.length > 0) {
              let item = this.deviceList[0]
              this.selectItem = item
              this.$emit('selectItem', item)
            } else {
              this.$emit('selectItem', null)
            }
            this.setPager(data)
          }
        })
        .catch(e => {
          this.errorMessage(e)
          console.log(e)
        })
    }
  }
}
</script>

<style scoped lang="less">
.device-top-bar {
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.pagination-box-cla {
  padding: 0px;
}
.device-list-cla {
  padding: 0px;
  margin: 20px 0px;
  min-height: 400px;
  .active-cla {
    background-color: #f6f7f8;
  }
  li {
    cursor: pointer;
    list-style: none;
    height: 60px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
    &:hover {
      background-color: #f6f7f8;
    }
    .offline-cla {
      color: @error-color;
    }
    .online-cla {
      color: @primary-color;
    }
  }
}
.search-no-data {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 50px;
}
</style>
