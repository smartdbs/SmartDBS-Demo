<template>
  <div class="door-list-cla">
    <div classs="door-top-cla">
      <a-form-model layout="inline" :model="searchForm">
        <a-form-model-item>
          <a-select style="width:120px" v-model="searchForm.type">
            <a-select-option
              v-for="item in optionList"
              :key="item.key"
              :value="item.key"
              >{{ $t(item.label) }}</a-select-option
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
    </div>
    <div classs="door-bottom-cla">
      <a-table
        :pagination="false"
        class="table-cla"
        :columns="columns"
        :data-source="tableData"
        rowKey="id"
        tableLayout="fixed"
      >
        <div slot="status" slot-scope="record">
          <span v-if="record.status === 0" class="door--offline">{{
            $t('door.offline')
          }}</span>
          <span v-if="record.status === 1" class="door--online">{{
            $t('door.online')
          }}</span>
        </div>
        <div slot="doorOperation" slot-scope="record">
          <i
            class="details-cla icon iconfont zk-icon-kaimen2-05-05"
            :title="$t('door.open')"
            @click="remoteOpenDoor(record)"
          ></i>

          <i
            :title="$t('common.details')"
            @click="details(record)"
            class="details-cla icon iconfont zk-icon-ziyuan"
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
          :show-total="
            total => `${this.$t('common.showTotal', { total: pager.total })}`
          "
          @change="pageChange(arguments[0], arguments[1], getDoorList)"
          @showSizeChange="pageChange(arguments[0], arguments[1], getDoorList)"
        ></a-pagination>
      </div>
    </div>
    <a-modal :visible="openModal" :footer="null" style="width:250px">
      <div class="open-door-modal">
        <template v-if="remoteOpenDoorStatus === 0">
          <a-icon type="loading" class="open-door-loading" />
          <div class="open-door-msg">{{ $t('door.openingDoor') }}</div>
        </template>
        <template v-else-if="remoteOpenDoorStatus === 1">
          <a-icon type="check-circle" class="open-door-loading" />
          <div class="open-door-msg">{{ $t('door.openDoorSuccess') }}</div>
        </template>
        <template v-else-if="remoteOpenDoorStatus === 2">
          <a-icon
            type="close-circle"
            class="open-door-loading open-door--fail"
          />
          <div class="open-door-msg">
            {{ $t('door.openDoorFail') }}
          </div>
        </template>
      </div>
    </a-modal>
    <a-modal
      :title="$t('common.tips')"
      :visible="pwdModal"
      @ok="handleOk"
      @cancel="closeModal"
    >
      <a-form-model :model="pwdForm" layout="inline">
        <a-form-model-item :label="$t('door.doorPassword')">
          <a-input
            :placeholder="$t('door.reqDoorPassword')"
            v-model="pwdForm.password"
            type="password"
          />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
    <a-drawer
      placement="right"
      :closable="false"
      :visible="visible"
      :width="500"
      class="edit-drawer-cla"
      @close="onClose"
    >
      <a-form-model
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 14 }"
        labelAlign="left"
      >
        <div class="base-info-cla">{{ $t('device.deviceInfo') }}</div>
        <a-form-model-item :label="$t('door.deviceSn')" prop="sn">
          {{ editForm.sn }}
        </a-form-model-item>
        <a-form-model-item :label="$t('door.ownerDevice')" prop="alias">
          {{ editForm.alias }}
        </a-form-model-item>
        <a-form-model-item :label="$t('door.doorName')">
          <a-input
            v-model="editForm.doorName"
            :placeholder="$t('door.reqDoorName')"
          />
        </a-form-model-item>
      </a-form-model>
      <div class="foot-bottom-cla">
        <a-button @click.stop="submitUpdate" type="primary">
          {{ $t('common.okText') }}
        </a-button>
      </div>
    </a-drawer>
    <a-drawer
      placement="right"
      :closable="false"
      :visible="settingVisible"
      :width="500"
      class="edit-drawer-cla"
      @close="closeSettingDrawer"
    >
      <a-form-model
        :label-col="{ span: 8 }"
        :wrapper-col="{ span: 14 }"
        labelAlign="left"
        :model="settingForm"
        :rules="rules"
        ref="settingForms"
      >
        <div class="base-info-cla">{{ $t('door.remoteOpenDoorSetting') }}</div>
        <a-form-model-item :label="$t('door.openDoorByPassword')">
          <a-switch
            @change="onChange('remoteType')"
            :checked="settingForm.remoteType !== 0"
          />
          <a-tooltip placement="right">
            <template slot="title">
              <span>{{ $t('door.openDoorByPwdTip') }}</span>
            </template>
            <i class="iconfont zk-icon-wenti tooltip-cla"></i>
          </a-tooltip>
        </a-form-model-item>

        <a-form-model-item
          :label="$t('door.passwrodSetting')"
          prop="password"
          v-if="settingForm.remoteType"
        >
          <a-input v-model="settingForm.password" type="password" />
        </a-form-model-item>

        <div>
          <a-button
            @click.stop="saveDoorPassword('remote')"
            type="primary"
            class="operation-btn-cla"
            v-if="settingForm.remoteType"
          >
            {{ $t('common.okText') }}
          </a-button>

          <a-button
            @click.stop="clearDoorPassword"
            type="danger"
            class="operation-btn-cla"
          >
            {{ $t('door.clearDoorPassword') }}
          </a-button>
        </div>

        <div class="base-info-cla setting-door-color">
          {{ $t('door.openDoorByDevice') }}
        </div>
        <a-form-model-item :label="$t('door.tempPassword')">
          <a-switch
            @change="onChange('deviceType')"
            :checked="settingForm.deviceType !== 0"
          />
          <a-tooltip placement="right">
            <template slot="title">
              <span style="display:inline-block;text-align:center">{{
                $t('door.tempPwdTips')
              }}</span>
            </template>
            <i class="iconfont zk-icon-wenti tooltip-cla"></i>
          </a-tooltip>
        </a-form-model-item>
        <a-form-model-item
          :label="$t('door.vaildTime')"
          v-if="settingForm.deviceType"
        >
          <a-select v-model="settingForm.minutes">
            <a-select-option :value="2">{{
              $t('door.secondMinutes')
            }}</a-select-option>
            <a-select-option :value="5">{{
              $t('door.fiveMinutes')
            }}</a-select-option>
            <a-select-option :value="10">{{
              $t('door.tenMinutes')
            }}</a-select-option>
            <a-select-option :value="30">{{
              $t('door.thirtyMinutes')
            }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item
          :label="$t('door.tempPassword')"
          v-if="settingForm.deviceType"
          prop="tempPassword"
        >
          <a-input v-model="settingForm.tempPassword"></a-input>
        </a-form-model-item>
      </a-form-model>
      <div v-if="settingForm.deviceType">
        <a-button
          @click.stop="saveDoorPassword('device')"
          type="primary"
          class="operation-btn-cla"
          >{{ $t('common.okText') }}</a-button
        >
      </div>
    </a-drawer>
  </div>
</template>

<script>
export default {
  data() {
    let handlePass = (rule, value, callback) => {
      // if (value.length === 0) {
      //   return callback()
      // }
      if (value.length !== 6) {
        return callback(new Error(this.$t('door.reqPwdTips')))
      } else {
        return callback()
      }
    }
    return {
      tableData: [],
      searchForm: {
        type: 0,
        value: ''
      },
      optionList: [
        {
          key: 0,
          label: this.$t('door.doorName'),
          attr: 'doorName'
        },
        {
          key: 1,
          label: this.$t('door.ownerDevice'),
          attr: 'alias'
        },
        {
          key: 2,
          label: this.$t('door.deviceSn'),
          attr: 'sn'
        }
      ],
      editForm: {},

      columns: [
        {
          title: this.$t('door.doorName'),
          dataIndex: 'doorName'
        },
        {
          title: this.$t('door.ownerDevice'),
          dataIndex: 'alias'
        },
        {
          title: this.$t('door.deviceSn'),
          dataIndex: 'sn'
        },
        {
          title: this.$t('door.doorStatus'),
          scopedSlots: { customRender: 'status' }
        },
        {
          title: this.$t('door.doorOperation'),
          scopedSlots: { customRender: 'doorOperation' }
        }
      ],
      visible: false,
      openModal: false,
      remoteOpenDoorStatus: 0, // 0 初始  1 成功 2 失败
      pwdModal: false,
      pwdForm: {
        id: '',
        password: ''
      },
      settingVisible: false,

      settingForm: {
        id: '',
        password: '',
        tempPassword: '',
        remoteType: 0,
        deviceType: 0,
        minutes: 2
      },
      rules: {
        password: [
          { required: true, validator: handlePass, trigger: ['blur', 'change'] }
        ],
        tempPassword: [
          { required: true, validator: handlePass, trigger: ['blur', 'change'] }
        ]
      },
      timer1: null,
      timer2: null,
      timer3: null,
      errorMsg: '',
      record: {}
    }
  },
  mounted() {
    this.getDoorList()
  },
  methods: {
    closeModal() {
      this.pwdModal = false
      this.remoteOpenDoorStatus = 0
      this.pwdForm.password = ''
      clearTimeout(this.timer1)
      clearTimeout(this.timer2)
    },
    closeSettingDrawer() {
      this.settingVisible = false
      this.settingForm = {
        id: '',
        password: '',
        tempPassword: '',
        remoteType: 0,
        deviceType: 0,
        minutes: 2
      }
    },
    clearDoorPassword() {
      this.$confirm({
        title: this.$t('door.clearDoorPwdTips'),
        okText: this.$t('common.okText'),
        okType: 'danger',
        cancelText: this.$t('common.cancelText'),
        onOk: () => {
          let params = {
            id: this.settingForm.id
          }
          this.request('removeDoorPassword', params)
            .then(data => {
              if (data.code === '00') {
                this.settingVisible = false
                this.getDoorList()
                this.successMessage()
              } else {
                this.errorMessage(data.message)
              }
            })
            .catch(e => this.errorMessage(e))
        },
        onCancel() {
          console.log('Cancel')
        }
      })
    },
    saveDoorPassword(type) {
      // 设置固定密码 || 临时密码
      let _self = this
      if (type === 'remote') {
        _self.$refs.settingForms.validateField('password', valid => {
          if (!valid) {
            let params = {
              id: _self.settingForm.id,
              password: _self.settingForm.password,
              passwordType: 0
            }
            _self.setDoorPassword(params)
          }
        })
      } else if (type === 'device') {
        _self.$refs.settingForms.validateField('tempPassword', valid => {
          if (!valid) {
            let params = {
              id: _self.settingForm.id,
              password: _self.settingForm.tempPassword,
              passwordType: 1,
              minutes: _self.settingForm.minutes
            }
            _self.setDoorPassword(params)
          }
        })
      }
    },
    setDoorPassword(params) {
      // 通用设置密码接口
      this.request('assignDoorPassword', params)
        .then(data => {
          if (data.code === '00') {
            this.settingVisible = false
            this.successMessage()
            this.getDoorList()
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    },
    // 设置  开关
    onChange(type) {
      if (type === 'remoteType') {
        this.settingForm.remoteType = this.settingForm.remoteType === 1 ? 0 : 1
        if (!this.record.password) {
          this.settingForm.password = ''
        }
      } else if (type === 'deviceType') {
        this.settingForm.deviceType = this.settingForm.deviceType === 1 ? 0 : 1
        this.settingForm.tempPassword = ''
      }
    },

    remoteOpenDoor(record) {
      this.errorMsg = ''
      if (record.password) {
        this.pwdModal = true
        this.pwdForm.id = record.id
      } else {
        let _that = this
        this.$confirm({
          title: this.$t('door.remoteOpenDoorTips'),
          content: '',
          okText: this.$t('common.okText'),
          cancelText: this.$t('common.cancelText'),
          okType: 'primary',
          onOk() {
            let params = {
              id: record.id
            }
            _that.remoteOpenDoorStatus = 0
            _that.openModal = true
            _that
              .request('remoteOpenDoor', params)
              .then(data => {
                _that.commonHander(data)
              })
              .catch(e => _that.errorMessage(e))
          },
          onCancel() {
            console.log('Cancel')
          }
        })
      }
    },
    handleOk() {
      this.pwdModal = false
      this.openModal = true

      this.request('passwordOpenDoor', this.pwdForm).then(data => {
        // loading
        this.commonHander(data)
      })
    },

    commonHander(data) {
      this.remoteOpenDoorStatus = 0
      if (data.code === '00') {
        this.timer1 = setTimeout(() => {
          this.remoteOpenDoorStatus = 1
          this.timer2 = setTimeout(() => {
            this.openModal = false
            this.closeModal()
          }, 3000)
        }, 3000)
      } else {
        this.remoteOpenDoorStatus = 2
        this.timer2 = setTimeout(() => {
          this.openModal = false
          this.errorMsg = data.message
          this.closeModal()
        }, 3000)
      }
    },
    pwdOpenDoor(record) {
      this.pwdModal = true
      this.pwdForm.id = record.id
    },
    getDoorList() {
      let params = {}
      this.optionList.filter(item => {
        if (item.key === this.searchForm.type) {
          return (params[item.attr] = this.searchForm.value)
        }
      })

      this.request('getDoorListInfo', params, this.pager)
        .then(data => {
          if (data.code === '00') {
            this.tableData = data.data
            this.setPager(data)
          }
        })
        .catch(e => {
          this.errorMessage(e)
        })
    },
    onSearch() {
      this.pager.curPage = 1
      this.getDoorList()
    },
    details(record) {
      this.visible = true
      this.editForm = Object.assign({}, record)
    },
    onClose() {
      this.visible = false
    },
    submitUpdate() {
      let params = {
        id: this.editForm.id,
        doorName: this.editForm.doorName
      }
      this.request('updateDoorListInfo', params)
        .then(data => {
          if (data.code === '00') {
            this.visible = false
            this.successMessage()
            this.getDoorList()
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    }
  }
}
</script>

<style scoped lang="less">
.door-list-cla {
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

.door--offline {
  color: @error-color;
}
.door--online {
  color: @primary-color;
}
.open-door-modal {
  width: 100%;
  text-align: center;
  .open-door-loading {
    font-size: 72px;
    color: @primary-color;
  }
  .open-door--fail {
    color: @error-color;
  }
  .open-door-msg {
    margin: 10px 0;
  }
}
.operation-btn-cla {
  width: 140px;
  margin-top: 15px;
  margin-left: 95px;
  display: block;
}
.setting-door-color {
  border-left-color: @warning-color !important;
}
.tooltip-cla {
  margin-left: 10px;
  color: @primary-color;
}
</style>
