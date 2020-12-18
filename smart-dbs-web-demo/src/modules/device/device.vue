<template>
  <topDownLayout>
    <div class="device-top-bar" slot="top">
      <a-form-model layout="inline" :model="searchForm">
        <a-form-model-item>
          <a-select
            style="width: 120px"
            v-model="searchForm.type"
            :placeholder="$t('device.selectType')"
          >
            <a-select-option value="">
              {{ $t('device.allDevice') }}
            </a-select-option>
            <a-select-option :value="0">
              {{ $t('device.attDevice') }}
            </a-select-option>
            <a-select-option :value="1">
              {{ $t('device.accDevice') }}
            </a-select-option>
          </a-select>
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

      <a-button @click.stop="showAdd" icon="plus" type="primary">
        {{ $t('device.addDevice') }}
      </a-button>
    </div>
    <div slot="down">
      <a-table
        :pagination="false"
        class="table-cla"
        :columns="columns"
        :data-source="tableData"
        rowKey="id"
        tableLayout="fixed"
      >
        <div slot="deviceType" slot-scope="record">
          <span v-if="record.type === 0">{{ $t('device.attDevice') }}</span>
          <span v-if="record.type === 1">{{ $t('device.accDevice') }}</span>
        </div>
        <div slot="enable" slot-scope="record">
          <span v-if="record.enable === 0" class="disable-cla">{{
            $t('device.disable')
          }}</span>
          <span v-if="record.enable === 1" class="enable-cla">{{
            $t('device.enable')
          }}</span>
        </div>

        <div slot="status" slot-scope="record">
          <span v-if="record.status === 0" class="offline-cla">{{
            $t('device.offLine')
          }}</span>
          <span v-if="record.status === 1" class="online-cla">{{
            $t('device.onLine')
          }}</span>
        </div>

        <div slot="action" slot-scope="record">
          <i
            v-if="record.enable === 1"
            :title="$t('device.disableDevice')"
            @click="deviceOperator('disableDevice', record)"
            class="disable-btn-cla icon iconfont zk-icon-jinyong1"
          ></i>

          <i
            v-if="record.enable === 0"
            :title="$t('device.enableDevice')"
            @click="deviceOperator('enableDevice', record)"
            class="enable-btn-cla icon iconfont zk-icon-qiyong"
          ></i>

          <i
            :title="$t('device.restartDevice')"
            @click="rebootFn(record)"
            class="restart-cla icon iconfont zk-icon-ziyuanxhdpi"
          ></i>

          <i
            :title="$t('common.details')"
            @click="details(record)"
            class="details-cla icon iconfont zk-icon-ziyuan"
          ></i>

          <i
            :title="$t('common.delete')"
            @click="deleteFn(record)"
            class="delete-icon icon iconfont zk-icon-cangpeitubiao_shanchu"
          ></i>
        </div>
      </a-table>
      <div class="pagination-box-cla">
        <a-pagination
          v-model="pager.curPage"
          show-size-changer
          show-quick-jumper
          :page-size.sync="pager.pageSize"
          :total="pager.total"
          @change="pageChange(arguments[0], arguments[1], getDeviceList)"
          @showSizeChange="
            pageChange(arguments[0], arguments[1], getDeviceList)
          "
        ></a-pagination>
      </div>

      <a-drawer
        :title="$t('device.addDevice')"
        placement="right"
        :closable="false"
        :visible="addVisible"
        :width="500"
        @close="onCloseAdd"
      >
        <a-form-model
          ref="addRuleForm"
          :model="addFrom"
          :rules="rules"
          :label-col="{ span: 5 }"
          :wrapper-col="{ span: 14 }"
          labelAlign="left"
        >
          <a-form-model-item :label="$t('device.deviceSn')" prop="sn">
            <a-input v-model="addFrom.sn" />
          </a-form-model-item>

          <a-form-model-item :label="$t('device.deviceName')" prop="alias">
            <a-input v-model="addFrom.alias" />
          </a-form-model-item>

          <a-form-model-item :label="$t('device.deviceType')" prop="type">
            <a-select v-model="addFrom.type">
              <a-select-option :value="0"
                >{{ $t('device.attDevice') }}
              </a-select-option>
              <a-select-option :value="1">
                {{ $t('device.accDevice') }}
              </a-select-option>
            </a-select>
          </a-form-model-item>
        </a-form-model>
        <div class="foot-bottom-cla">
          <a-button @click.stop="onCloseAdd">{{
            $t('common.cancelText')
          }}</a-button>
          <a-button @click.stop="submitAdd" type="primary">
            {{ $t('common.okText') }}
          </a-button>
        </div>
      </a-drawer>

      <a-drawer
        placement="right"
        :closable="false"
        :visible="editVisible"
        :width="500"
        @close="onCloseEdit"
        class="edit-drawer-cla"
      >
        <div class="base-info-cla">{{ $t('device.deviceInfo') }}</div>
        <a-form-model
          :label-col="{ span: 5 }"
          :wrapper-col="{ span: 14 }"
          labelAlign="left"
        >
          <a-form-model-item :label="$t('device.deviceName')">
            <a-input
              v-model="editRow.alias"
              :placeholder="$t('device.inputAlias')"
            />
          </a-form-model-item>
          <a-form-model-item :label="$t('device.deviceSn')">
            {{ editRow.sn }}
          </a-form-model-item>
          <a-form-model-item :label="$t('device.model')">
            {{ editRow.model }}
          </a-form-model-item>
          <a-form-model-item :label="$t('device.fwVersion')">
            {{ editRow.fwVersion }}
          </a-form-model-item>
        </a-form-model>
        <div class="network-info-cla">{{ $t('device.networkInfo') }}</div>
        <a-form-model
          :label-col="{ span: 5 }"
          :wrapper-col="{ span: 14 }"
          labelAlign="left"
        >
          <a-form-model-item :label="$t('device.localIp')">
            {{ editRow.localIp }}
          </a-form-model-item>
          <a-form-model-item :label="$t('device.ipAddress')">
            {{ editRow.ipAddress }}
          </a-form-model-item>
          <a-form-model-item :label="$t('device.mac')">
            {{ editRow.mac }}
          </a-form-model-item>
        </a-form-model>
        <div class="foot-bottom-cla">
          <a-button @click.stop="submitEdit" type="primary">
            {{ $t('common.okText') }}
          </a-button>
        </div>
      </a-drawer>
    </div>
  </topDownLayout>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      deviceProtocol: {
        0: 'pull',
        1: 'push',
        2: 'best',
        3: 'ufo',
        4: 'best-w',
        5: 'best-t'
      },
      columns: [
        {
          title: this.$t('device.deviceSn'),
          dataIndex: 'sn'
        },
        {
          title: this.$t('device.deviceName'),
          dataIndex: 'alias'
        },
        {
          title: this.$t('device.deviceType'),
          scopedSlots: { customRender: 'deviceType' }
        },

        {
          title: this.$t('device.enableStatus'),
          scopedSlots: { customRender: 'enable' }
        },

        {
          title: this.$t('device.deviceStatus'),
          scopedSlots: { customRender: 'status' }
        },
        {
          title: this.$t('common.action'),
          key: 'action',
          scopedSlots: { customRender: 'action' },
          width: 250
        }
      ],
      searchForm: {
        type: '',
        valueType: 'deviceSn',
        value: ''
      },
      addVisible: false,
      editVisible: false,
      addFrom: {
        sn: '',
        alias: '',
        type: 0
      },
      originRow: {},
      editRow: {},
      rules: {
        sn: [
          {
            required: true,
            message: this.$t('device.inputSN'),
            trigger: 'blur'
          }
        ],
        alias: [
          {
            required: true,
            message: this.$t('device.inputDeviceName'),
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted() {
    this.getDeviceList()
  },
  methods: {
    showAdd() {
      this.addFrom = {
        sn: '',
        alias: '',
        type: 0
      }
      this.addVisible = true
    },
    onSearch() {
      //查询设备列表
      this.pager.curPage = 1
      this.getDeviceList()
    },
    onCloseAdd() {
      //关闭添加窗口
      this.addVisible = false
    },
    onCloseEdit() {
      //关闭编辑窗口
      this.editVisible = false
    },

    details(record) {
      this.originRow = record
      this.editRow = Object.assign({}, record)
      // this.editRow.protocol = this.deviceProtocol[this.editRow.protocol]
      // this.editRow.model
      this.editVisible = true
    },
    submitEdit() {
      this.request('updateDevice', {
        sn: this.editRow.sn,
        alias: this.editRow.alias
      })
        .then(data => {
          if (data.code === '00') {
            this.editVisible = false
            this.successMessage()
            this.getDeviceList()
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => {
          this.errorMessage(e)
          console.log(e)
        })
      // if (
      //   this.editRow.alias !== this.originRow.alias &&
      //   this.originRow.alias !== ''
      // ) {

      // }
    },
    submitAdd() {
      //保存新增
      this.$refs.addRuleForm.validate(valid => {
        if (valid) {
          this.request('saveDevice', this.addFrom)
            .then(data => {
              if (data.code === '00') {
                this.addVisible = false
                this.successMessage()
                this.getDeviceList()
              } else {
                this.errorMessage(data.message)
              }
            })
            .catch(e => {
              this.errorMessage(e)
              console.log(e)
            })
        }
      })
    },

    deleteFn(record) {
      //删除设备
      let _that = this
      this.$confirm({
        title: this.$t('common.deleteTip'),
        content: '',
        okText: this.$t('common.okText'),
        cancelText: this.$t('common.cancelText'),
        okType: 'danger',
        onOk() {
          _that.deviceOperator('removeDevice', record)
        },
        onCancel() {
          console.log('Cancel')
        }
      })
    },
    rebootFn(record) {
      this.$confirm({
        title: '此操作将重启设备，是否继续？',
        content: '',
        okText: this.$t('common.okText'),
        cancelText: this.$t('common.cancelText'),
        okType: 'primary',
        onOk: () => {
          this.deviceOperator('rebootDevice', record)
        },
        onCancel() {
          console.log('Cancel')
        }
      })
    },
    deviceOperator(operatorName, record) {
      //对设备操作：启用、禁用、重启、删除
      this.request(operatorName, {
        sn: record.sn
      })
        .then(data => {
          if (data.code === '00') {
            this.successMessage()
            this.getDeviceList()
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => {
          this.errorMessage(e)
          console.log(e)
        })
    },

    getDeviceList() {
      //获取设备列表
      let params = {
        type: this.searchForm.type
      }
      if (this.searchForm.valueType === 'deviceSn') {
        params.sn = this.searchForm.value
      } else {
        params.alias = this.searchForm.value
      }
      this.request('getDeviceList', params, this.pager)
        .then(data => {
          if (data.code === '00') {
            this.tableData = data.data
            this.setPager(data)
          } else {
            this.errorMessage(data.message)
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
.edit-drawer-cla {
  .ant-form-item {
    margin-bottom: 0px;
  }
  .baseInfo {
    height: 20;
    line-height: 20px;
    padding-left: 10px;
    margin: 10px 0px;
  }
  .network-info-cla {
    .baseInfo;
    border-left: 5px solid @error-color;
  }
  .base-info-cla {
    .baseInfo;
    border-left: 5px solid @primary-color;
  }
  .foot-bottom-cla {
    position: absolute;
    right: 0;
    bottom: 0;
    padding: 20px 16px;
    width: '100%';
  }
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
    font-size: 25px;
  }
  .restart-cla {
    color: @primary-color;
  }
  .details-cla {
    color: @primary-color;
  }
  .offline-cla {
    color: @error-color;
  }
  .online-cla {
    color: @primary-color;
  }
  .disable-cla {
    color: @error-color;
  }
  .enable-cla {
    color: @primary-color;
  }
}
</style>
