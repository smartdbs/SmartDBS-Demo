<template>
  <div class="acc-data-cla">
    <div class="acc-top-cla">
      <a-form-model layout="inline" :model="searchForm">
        <a-form-model-item>
          <a-range-picker
            @change="dateChange"
            :placeholder="[$t('common.startTime'), $t('common.endTime')]"
            :allowClear="true"
            v-model="date"
            dropdownClassName="owner-dropdown-cla"
            renderExtraFooter
          >
            <!-- <div slot="renderExtraFooter" class="date-footer-cla">
              <a-button @click.stop="clearDate">取消</a-button>
              <a-button
                type="primary"
                style="margin:0 10px"
                @click.stop="sumbitDate"
                >确定</a-button
              >
            </div> -->
          </a-range-picker>
        </a-form-model-item>
        <a-form-model-item>
          <a-select
            style="width: 120px"
            v-model="searchForm.type"
            :placeholder="$t('device.selectType')"
          >
            <a-select-option
              v-for="item in optionList"
              :value="item.key"
              :key="item.key"
              >{{ item.label }}</a-select-option
            >
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
      <div class="export-data-cla">
        <a-button @click.stop="exportFn" icon="arrow-down" type="primary">
          {{ $t('att.export') }}
        </a-button>
      </div>
    </div>
    <div class="acc-bottom-cla">
      <a-table
        :pagination="false"
        :columns="columns"
        :data-source="tableData"
        rowKey="id"
        tableLayout="fixed"
      >
        <div slot="inOutState" slot-scope="record">
          <span v-if="record.inOutType === 0">{{ $t('door.in') }}</span>
          <span v-if="record.inOutType === 1">{{ $t('door.out') }}</span>
          <span v-if="record.inOutType === 2">{{ $t('door.unkonw') }}</span>
        </div>
      </a-table>
    </div>
    <div class="pagination-box-cla">
      <a-pagination
        v-model="pager.curPage"
        show-size-changer
        show-quick-jumper
        :page-size.sync="pager.pageSize"
        :total="pager.total"
        @change="pageChange(arguments[0], arguments[1], getAccDataList)"
        @showSizeChange="pageChange(arguments[0], arguments[1], getAccDataList)"
      ></a-pagination>
    </div>
  </div>
</template>

<script>
import httpClient from '@/service/httpClient.js'
export default {
  data() {
    return {
      date: [],
      tableData: [],
      optionList: [
        {
          key: 0,
          label: this.$t('door.deviceSn'),
          attr: 'sn'
        }
        // {
        //   key: 1,
        //   label: '门编号',
        //   attr: 'doorNum'
        // }
      ],
      searchForm: {
        type: 0,
        value: ''
      },
      columns: [
        {
          title: this.$t('door.deviceSn'),
          dataIndex: 'sn'
        },
        {
          title: this.$t('door.openWay'),
          dataIndex: 'verifiedI18n'
        },
        {
          title: this.$t('door.openDetail'),
          dataIndex: 'eventCodeI18n'
        },
        // {
        //   title: '门编号',
        //   dataIndex: 'doorNum'
        // },
        {
          title: this.$t('door.inOutState'),
          scopedSlots: { customRender: 'inOutState' }
        },
        {
          title: this.$t('door.time'),
          dataIndex: 'time'
        }
      ]
    }
  },
  mounted() {
    this.getAccDataList()
  },
  methods: {
    getAccDataList() {
      let params = {}
      this.optionList.filter(item => {
        if (item.key === this.searchForm.type) {
          return (params[item.attr] = this.searchForm.value)
        }
      })
      this.searchForm.startTime &&
        (params.startTime = this.searchForm.startTime)
      this.searchForm.endTime && (params.endTime = this.searchForm.endTime)

      this.request('getAccDataList', params, this.pager)
        .then(data => {
          if (data.code === '00') {
            this.tableData = data.data
            this.setPager(data)
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    },
    onSearch() {
      this.pager.curPage = 1
      this.getAccDataList()
    },
    clearDate() {
      this.date = ''
    },
    dateChange(date, dateString) {
      this.searchForm.startTime = Date.parse(dateString[0]) / 1000
      this.searchForm.endTime = Date.parse(dateString[1]) / 1000
      // console.table(this.searchForm)
    },
    exportFn() {
      //  接口还未提供
      let params = {}
      this.optionList.filter(item => {
        if (item.key === this.searchForm.type) {
          return (params[item.attr] = this.searchForm.value)
        }
      })
      httpClient.downloadUrl('/accTransactionLog/exportExcel', {
        lang: 'zh-cn',
        payload: {
          ...params,
          startTime: this.searchForm.startTime,
          endTime: this.searchForm.endTime
        }
      })
    }
  }
}
</script>

<style scoped lang="less">
/deep/ .ant-calendar-picker {
  width: 220px;
}
.acc-data-cla {
  padding: 24px;
  .acc-top-cla {
    display: flex;
    justify-content: space-between;
  }
  .acc-bottom-cla {
  }
}
</style>
<style lang="less">
.owner-dropdown-cla {
  top: 185px !important;
  .ant-calendar-input-wrap,
  .ant-calendar-range-middle {
    display: none;
  }
  .ant-calendar-footer-btn {
    display: flex;
    justify-content: flex-end;
  }
}
</style>
