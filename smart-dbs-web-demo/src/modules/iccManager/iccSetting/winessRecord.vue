<template>
  <topDownLayout>
    <div class="winess_person_cla" slot="top">
      <a-form-model :model="form" layout="inline">
        <a-form-model-item label="起始时间">
          <a-range-picker
            @change="pickOnChange"
            :placeholder="[$t('common.startTime'), $t('common.endTime')]"
            :allowClear="true"
            v-model="date"
            dropdownClassName="owner-dropdown-cla"
            renderExtraFooter
            style="width:220px"
          />
        </a-form-model-item>
        <a-form-model-item label="核验结果">
          <a-select
            v-model="form.status"
            style="width: 140px"
            placeholder="请选择核验结果"
          >
            <a-select-option
              v-for="option in statusList"
              :key="option.key"
              :value="option.key"
              >{{ option.label }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <!-- <a-form-model-item label="设备序列号">
        <a-input v-model="form.sn"></a-input>
      </a-form-model-item>
      <a-form-model-item label="姓名">
        <a-input v-model="form.name"></a-input>
      </a-form-model-item> -->
        <a-form-model-item>
          <a-select v-model="form.type" style="width: 120px">
            <a-select-option
              v-for="item in optionList"
              :key="item.key"
              :value="item.key"
            >
              {{ item.label }}</a-select-option
            >
          </a-select>
        </a-form-model-item>
        <a-form-model-item>
          <a-input-search
            v-model="form.value"
            :placeholder="$t('common.searchKey')"
            enter-button
            @search="onSearch"
          />
        </a-form-model-item>
      </a-form-model>
      <a-button @click.stop="exportFn" icon="arrow-down" type="primary">
        导出数据
      </a-button>
    </div>
    <div slot="down">
      <a-row style="text-align:right;padding:5px 20px">
        <span class="normal_cla"><span class="base_cicle"></span>体温正常</span>
        <span class="error_cla"><span class="base_cicle"></span>体温异常</span>
        <a-popover placement="bottom" trigger="click">
          <template slot="content">
            <a-checkbox-group
              :defaultValue="checkedValues"
              @change="chooseColumns"
            >
              <div>
                <a-checkbox value="temperature">
                  温度
                </a-checkbox>
              </div>
              <div>
                <a-checkbox value="maskStatus">
                  是否佩戴口罩
                </a-checkbox>
              </div>
              <div>
                <a-checkbox value="verifyMode">
                  核验方式
                </a-checkbox>
              </div>
            </a-checkbox-group>
          </template>
          <a-button icon="filter" type="link"></a-button>
        </a-popover>
      </a-row>
      <a-table
        :pagination="false"
        class="table-cla"
        :columns="showColumns"
        :data-source="tableData"
        rowKey="id"
        tableLayout="fixed"
      >
        <div slot="status" slot-scope="record">
          {{ record.status | chooseIccStatus(statusList) }}
        </div>
        <div slot="temperature" slot-scope="record">
          <span
            :class="
              parseInt(record.temperature) > 37.3
                ? 'error__color'
                : 'normal__color'
            "
            v-if="record.temperature"
            >{{ record.temperature }}</span
          >
          <span v-else>N/A</span>
        </div>
        <div slot="maskStatus" slot-scope="record">
          <span v-if="record.maskStatus">
            {{ record.maskStatus === '0' ? '否' : '是' }}</span
          >
          <span v-else> N/A</span>
        </div>
        <div slot="verifyMode" slot-scope="record">
          {{ record.verifyMode | chooseVertifyMode(deviceVerifyModeList) }}
        </div>
      </a-table>
      <div class="pagination-box-cla">
        <a-pagination
          v-model="pager.curPage"
          show-quick-jumper
          size="small"
          :page-size.sync="pager.pageSize"
          :total="pager.total"
          :show-total="
            total => `${this.$t('common.showTotal', { total: pager.total })}`
          "
          @change="pageChange(arguments[0], arguments[1], getPersonRecordList)"
          @showSizeChange="
            pageChange(arguments[0], arguments[1], getPersonRecordList)
          "
        ></a-pagination>
      </div>
    </div>
  </topDownLayout>
</template>

<script>
import httpClient from '@/service/httpClient.js'
import { getStartTime, getEndTime } from '@/utils/index.js'
export default {
  inject: ['deviceVerifyModeList', 'statusList'],
  data() {
    return {
      date: [],
      plainOptions: ['Apple', 'Pear', 'Orange'],
      checkedList: [],
      form: {
        startTime: '',
        endTime: '',
        status: '', //  0.  1 . 2
        sn: '',
        name: '',
        type: 0,
        value: ''
      },
      tableData: [],
      optionList: [
        {
          label: '设备序列号',
          key: 0,
          attr: 'sn'
        },
        {
          label: '姓名',
          key: 1,
          attr: 'name'
        }
      ],
      checkedValues: ['temperature', 'maskStatus', 'verifyMode']
    }
  },
  methods: {
    pickOnChange(date, dateString) {
      this.form.startTime = Date.parse(dateString[0])
      this.form.endTime = Date.parse(dateString[1])
    },
    chooseColumns(checkedValues) {
      this.checkedValues = checkedValues
    },
    checkboxOnChange(e) {
      console.log(e.target.checked)
    },
    onSearch() {
      this.pager.curPage = 1
      this.getPersonRecordList()
    },
    getParams() {
      let params = {
        startTime: this.form.startTime
          ? getStartTime(this.form.startTime) / 1000
          : '',
        endTime: this.form.endTime ? getEndTime(this.form.endTime) / 1000 : '',
        status: this.form.status
      }
      this.optionList.filter(item => {
        if (item.key === this.form.type) {
          return (params[item.atrr] = this.form.value)
        }
      })
      return params
    },
    getPersonRecordList() {
      let params = this.getParams()
      this.request('getIccPersonVerifyRecordList', params, this.pager)
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
    exportFn() {
      let params = this.getParams()
      httpClient.downloadUrl('/iccPersonVerifyRecord/exportExcel ', {
        lang: 'zh-cn',
        payload: {
          ...params
        }
      })
    }
  },
  computed: {
    showColumns() {
      let result = [
        {
          title: '设备序列号',
          dataIndex: 'sn'
        },
        {
          title: '姓名',
          dataIndex: 'firstName'
        },
        {
          title: '核验时间',
          dataIndex: 'time'
        },
        {
          title: '核验结果',
          key: 'status',
          scopedSlots: { customRender: 'status' }
        }
      ]
      this.checkedValues.forEach(item => {
        if (item === 'temperature') {
          result.push({
            title: '温度',
            key: 'temperature',
            scopedSlots: { customRender: 'temperature' }
          })
        }
        if (item === 'maskStatus') {
          result.push({
            title: '是否佩戴口罩',
            key: 'maskStatus',
            scopedSlots: { customRender: 'maskStatus' }
          })
        }
        if (item === 'verifyMode') {
          result.push({
            title: '核验类型',
            key: 'verifyMode',
            scopedSlots: { customRender: 'verifyMode' }
          })
        }
      })
      return result
    }
  },
  filters: {
    chooseVertifyMode(val, data) {
      return data[val]
    },
    chooseIccStatus(val, data) {
      let result = data.filter(item => {
        return item.key === parseInt(val)
      })
      return result[0].label
    }
  },
  mounted() {
    this.getPersonRecordList()
  }
}
</script>

<style scoped lang="less">
.winess_person_cla {
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px;
}
.base_cicle {
  display: inline-block;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin: 0 10px;
}
.error__color {
  color: @error-color;
}
.normal__color {
  color: @primary-color;
}

.normal_cla {
  .normal__color;
  span {
    .base_cicle;
    background: @primary-color;
  }
}
.error_cla {
  .error__color;
  span {
    .base_cicle;
    background: @error-color;
  }
}
.screen_cla {
  margin: 0 10px;
  cursor: pointer;
}
</style>
