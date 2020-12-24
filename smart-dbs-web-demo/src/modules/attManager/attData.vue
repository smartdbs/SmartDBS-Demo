<template>
  <topDownLayout>
    <div class="device-top-bar" slot="top">
      <a-form-model layout="inline" :model="searchForm">
        <a-form-model-item>
          <a-range-picker @change="handleChange" />
        </a-form-model-item>

        <a-form-model-item>
          <a-select
            style="width: 120px"
            v-model="searchForm.valueType"
            :placeholder="$t('common.searchKey')"
          >
            <a-select-option value="deviceSn">
              {{ $t('device.deviceSn') }}
            </a-select-option>
            <a-select-option value="deviceName">
              {{ $t('device.deviceName') }}
            </a-select-option>

            <a-select-option value="employeeNo">
              {{ $t('employee.employeeNo') }}
            </a-select-option>

            <a-select-option value="formattedName">
              {{ $t('employee.formattedName') }}
            </a-select-option>
          </a-select>
        </a-form-model-item>

        <a-form-model-item>
          <a-input-search
            v-model="searchForm.value"
            :placeholder="$t('common.searchKey')"
            enter-button
            @search="onSearch"
          />
        </a-form-model-item>
      </a-form-model>

      <a-button @click.stop="exportFn" icon="arrow-down" type="primary">
        {{ $t('att.export') }}
      </a-button>
    </div>
    <div slot="down">
      <a-table
        :pagination="false"
        class="table-cla"
        :rowKey="rendKey"
        :columns="columns"
        :data-source="tableData"
        tableLayout="fixed"
      >
      </a-table>
      <div class="pagination-box-cla">
        <a-pagination
          v-model="pager.curPage"
          show-size-changer
          show-quick-jumper
          :page-size.sync="pager.pageSize"
          :total="pager.total"
          @change="pageChange(arguments[0], arguments[1], getAttRecordList)"
          @showSizeChange="
            pageChange(arguments[0], arguments[1], getAttRecordList)
          "
        ></a-pagination>
      </div>
    </div>
  </topDownLayout>
</template>

<script>
import httpClient from '@/service/httpClient.js'
export default {
  data() {
    return {
      tableData: [],
      columns: [
        {
          title: this.$t('employee.employeeNo'),
          dataIndex: 'employeeNo'
        },
        {
          title: this.$t('employee.formattedName'),
          dataIndex: 'formattedName'
        },
        {
          title: this.$t('device.deviceSn'),
          dataIndex: 'sn'
        },
        {
          title: this.$t('device.deviceName'),
          dataIndex: 'alias'
        },
        {
          title: this.$t('att.checkInTime'),
          dataIndex: 'isoCheckInTime'
        }
      ],
      searchForm: {
        time: {},
        valueType: 'deviceSn',
        value: ''
      }
    }
  },
  mounted() {
    this.getAttRecordList()
  },
  methods: {
    handleChange(value) {
      this.searchForm.time = value
    },
    rendKey(recod) {
      return recod.employeeNo + recod.checkInTime
    },
    exportFn() {
      let params = {}
      if (this.searchForm.valueType === 'deviceSn') {
        params.sn = this.searchForm.value
      }
      if (this.searchForm.valueType === 'deviceName') {
        params.alias = this.searchForm.value
      }
      if (this.searchForm.valueType === 'employeeNo') {
        params.employeeNo = this.searchForm.value
      }
      if (this.searchForm.valueType === 'formattedName') {
        params.formattedName = this.searchForm.value
      }
      if (this.searchForm.time && this.searchForm.time.length === 2) {
        let startTime = this.searchForm.time[0].startOf('day').valueOf()
        let endTime = this.searchForm.time[0].endOf('day').valueOf()
        params.startTime = startTime
        params.endTime = endTime
      }
      httpClient.downloadUrl('/attRecord/exportExcel', {
        lang: 'zh-cn',
        payload: {
          ...params
        }
      })
    },
    onSearch() {
      this.getAttRecordList()
    },
    getAttRecordList() {
      //获取设备列表
      let params = {
        sn: '',
        employeeNo: '',
        alias: '',
        formattedName: ''
      }
      if (this.searchForm.time && this.searchForm.time.length === 2) {
        let startTime = this.searchForm.time[0].startOf('day').valueOf()
        let endTime = this.searchForm.time[0].endOf('day').valueOf()
        params.startTime = startTime
        params.endTime = endTime
      }
      if (this.searchForm.valueType === 'deviceSn') {
        params.sn = this.searchForm.value
      }
      if (this.searchForm.valueType === 'deviceName') {
        params.alias = this.searchForm.value
      }
      if (this.searchForm.valueType === 'employeeNo') {
        params.employeeNo = this.searchForm.value
      }
      if (this.searchForm.valueType === 'formattedName') {
        params.formattedName = this.searchForm.value
      }
      this.request('attRecordList', params, this.pager)
        .then(data => {
          if (data.code === '00') {
            this.tableData = data.data
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
  padding: 24px;
}
.table-cla {
  i {
    font-size: 25px;
    margin-right: 12px;
    cursor: pointer;
  }
  .disable-btn-cla {
    color: @error-color;
  }
  .enable-btn-cla {
    color: @primary-color;
    font-size: 28px;
  }
  .restart-cla {
    color: @primary-color;
  }
}
</style>
