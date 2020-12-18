<template>
  <div class="door-permission-group-cla">
    <a-table
      :pagination="false"
      class="table-cla"
      :columns="columns"
      :data-source="tableData"
      rowKey="groupNum"
      tableLayout="fixed"
    >
      <div slot="timePeriod" slot-scope="record">
        <span>{{ record.timezoneNum | coverTime(timeZoneList) }}</span>
      </div>
      <div slot="operation" slot-scope="record">
        <i
          class="details-cla icon iconfont zk-icon-men"
          :title="$t('door.doorSetting')"
          @click="setDoorSetting(record)"
        ></i>
        <i
          class="details-cla icon iconfont zk-icon-yonghu"
          :title="$t('door.setEmployee')"
          @click="setEmployee(record)"
        ></i>
        <i
          class=" details-cla icon  iconfont zk-icon-ziyuan"
          :title="$t('common.details')"
          @click="details(record)"
        ></i>
        <i
          class="delete-icon icon iconfont zk-icon-cangpeitubiao_shanchu"
          :title="$t('common.delete')"
          @click="deleteFn(record)"
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
        @change="pageChange(arguments[0], arguments[1], getAccAuthList)"
        @showSizeChange="pageChange(arguments[0], arguments[1], getAccAuthList)"
      ></a-pagination>
    </div>
    <a-drawer
      :title="$t('acc.createPermissionCroup')"
      placement="right"
      :closable="false"
      :visible="addVisible"
      :width="600"
      @close="addVisible = false"
    >
      <a-form-model
        ref="addRuleForm"
        :model="addFrom"
        :rules="rules"
        :label-col="{ span: 5 }"
        :wrapper-col="{ span: 14 }"
        labelAlign="left"
      >
        <a-form-model-item :label="$t('common.name')" prop="name">
          <a-input v-model="addFrom.name" />
        </a-form-model-item>
        <a-form-model-item :label="$t('acc.timezone')">
          <a-select
            v-model="addFrom.timezoneNum"
            :placeholder="$t('device.selectType')"
          >
            <a-select-option
              v-for="item in timeZoneList"
              :value="item.timezoneNum"
              :key="item.timezoneNum"
              >{{ item.timezoneName }}</a-select-option
            >
          </a-select>
        </a-form-model-item>
        <a-form-model-item :label="$t('common.date')">
          <a-range-picker
            @change="dateChange"
            :placeholder="[$t('common.startTime'), $t('common.endTime')]"
            :allowClear="true"
            v-model="date"
            dropdownClassName="owner-dropdown-cla"
            show-time
            format="YYYY-MM-DD HH:mm:ss"
          >
          </a-range-picker>
        </a-form-model-item>
        <a-button @click="addVisible = false" class="cancel-btn ">
          {{ $t('common.cancelText') }}
        </a-button>
        <a-button @click="addAccAuth" class="save-btn" type="primary">
          {{ $t('common.save') }}
        </a-button>
      </a-form-model>
    </a-drawer>

    <a-drawer
      placement="right"
      :closable="false"
      :visible="doorSettingVisable"
      :width="600"
      @close="doorSettingVisable = false"
    >
      <div class="drawer-header-cla">
        <div class="door-drawer-title">{{ $t('door.portionGate') }}</div>
        <a-icon
          @click="showAddDoor"
          class="add-door-btn-cla"
          type="plus-circle"
        />
      </div>
      <ul class="door-list-box">
        <li :key="item.id" v-for="item of doorList">
          <a-icon
            @click="unAssignDoor(item)"
            class="delete-icon"
            type="minus-circle"
          />
          <i class="iconfont zk-icon-men" style="font-size:64px"></i>
          <div>
            <div class="door-name-cla" style="height: 25px">
              {{ item.doorName }}
            </div>
            <div>{{ item.sn }}</div>
            <div
              :class="item.status === '0' ? 'door--offline' : 'door--online'"
            >
              {{ item.status === '0' ? $t('door.offline') : $t('door.online') }}
            </div>
          </div>
        </li>
      </ul>
    </a-drawer>
    <a-modal
      v-model="assginDoorModal"
      :title="$t('acc.portionGate')"
      :ok-text="$t('common.okText')"
      :cancel-text="$t('common.cancelText')"
      @ok="hideAssignModal"
      @cancel="assginDoorModal = false"
      :closable="false"
    >
      <assignDoor ref="assignDoor" :groupNums="groupNum"></assignDoor>
    </a-modal>
    <a-drawer
      placement="right"
      :closable="false"
      :visible="employeeVisable"
      :width="600"
      @close="employeeVisable = false"
    >
      <div class="drawer-header-cla">
        <div class="door-drawer-title">{{ $t('acc.employeeAuth') }}</div>
        <a-icon
          @click="showAddEmployee"
          class="add-door-btn-cla"
          type="plus-circle"
        />
      </div>
      <ul class="door-list-box">
        <li :key="item.id" v-for="item of employeeList">
          <a-icon
            @click="unAuthEmployee(item)"
            class="delete-icon"
            type="minus-circle"
          />
          <a-avatar
            v-if="item.avatar"
            class="avatar-cla"
            shape="square"
            :size="64"
            :src="item.avatar"
          />
          <a-avatar
            class="avatar-cla"
            v-else
            shape="square"
            :size="64"
            icon="user"
          />
          <div>
            <div style="height: 25px">{{ item.formattedName }}</div>
            <div>{{ item.employeeNo }}</div>
          </div>
        </li>
      </ul>
    </a-drawer>
    <a-modal
      v-model="employeeModalVisable"
      :title="$t('acc.distributionEmployee')"
      :ok-text="$t('common.okText')"
      :cancel-text="$t('common.cancelText')"
      @ok="hideEmployeeModal"
      @cancel="employeeModalVisable = false"
      :closable="false"
    >
      <assignEmployee
        ref="assignEmployee"
        :groupNums="groupNum"
      ></assignEmployee>
    </a-modal>
    <a-drawer
      placement="right"
      :closable="false"
      :visible="detialVisiable"
      :width="600"
      @close="detialVisiable = false"
    >
      <div class="drawer-header-cla">
        <div class="door-drawer-title">
          {{ $t('acc.permissionCroupInfo') }}
        </div>
      </div>
      <a-form-model
        labelAlign="left"
        :label-col="{ span: 5 }"
        :wrapper-col="{ span: 14 }"
      >
        <!-- <a-form-model-item :label="$t('acc.groupNum')">
          <label>{{ editForm.groupNum }}</label>
        </a-form-model-item> -->
        <a-form-model-item :label="$t('acc.groupName')">
          <a-input
            v-model="editForm.name"
            :placeholder="$t('acc.inputName')"
          ></a-input>
        </a-form-model-item>
        <a-form-model-item :label="$t('acc.timezone')">
          <a-select
            v-model="editForm.timezoneNum"
            :placeholder="$t('device.selectType')"
          >
            <a-select-option
              v-for="item in timeZoneList"
              :value="item.timezoneNum"
              :key="item.timezoneNum"
              >{{ item.timezoneName }}</a-select-option
            >
          </a-select>
        </a-form-model-item>
        <a-form-model-item :label="$t('common.date')">
          <a-range-picker
            @change="editDateChange"
            :placeholder="[$t('startTime.date'), $t('common.endTime')]"
            :allowClear="true"
            v-model="editDate"
            dropdownClassName="owner-dropdown-cla"
            show-time
            format="YYYY-MM-DD HH:mm:ss"
          >
          </a-range-picker>
        </a-form-model-item>
      </a-form-model>
      <a-button @click="updateAccAuth" class="save-btn" type="primary">
        {{ $t('common.save') }}
      </a-button>
    </a-drawer>
  </div>
</template>

<script>
import assignDoor from './modal/assignDoor'
import assignEmployee from './modal/assignEmployee'
export default {
  components: {
    assignDoor,
    assignEmployee
  },
  data() {
    return {
      columns: [
        // { title: this.$('acc.permissionCroupNum'), dataIndex: 'groupNum' },
        { title: this.$t('acc.permissionCroupName'), dataIndex: 'name' },
        {
          title: this.$t('acc.timezone'),
          scopedSlots: { customRender: 'timePeriod' }
        },
        {
          title: this.$t('common.action'),
          scopedSlots: { customRender: 'operation' }
        }
      ],
      tableData: [],
      timeZoneList: [],
      date: [],
      addVisible: false,
      doorSettingVisable: false,
      addFrom: {
        name: '',
        timezoneNum: '',
        startTime: '',
        endTime: ''
      },
      rules: {
        timezoneNum: [
          {
            required: true,
            message: '请先添加门禁时间段',
            trigger: ['blur', 'change']
          }
        ]
      },
      doorList: [],
      assginDoorModal: false,
      groupNum: '',
      employeeVisable: false,
      employeeModalVisable: false,
      employeeList: [],
      detialVisiable: false,
      editForm: {},
      editDate: []
    }
  },
  mounted() {
    this.getAccTimezone()
    this.getAccAuthList()
  },
  filters: {
    coverTime(val, list) {
      let res = list.filter(item => {
        if (item.timezoneNum === val) {
          return item
        }
      })
      return res.length !== 0 ? res[0].timezoneName : ''
    }
  },
  methods: {
    toConverTime(val) {
      this.timeZoneList.filter(item => {
        if (item.timezoneNum === val) {
          console.log(item.timezoneName)
          return item.timezoneName
        }
      })
    },
    updateAccAuth() {
      let params = {
        ...this.editForm
      }
      this.request('updateAccAuthDoor', params)
        .then(data => {
          if (data.code === '00') {
            this.successMessage()
            this.detialVisiable = false
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    },
    deleteFn(record) {
      let _self = this
      this.$confirm({
        title: this.$t('acc.deletePermissionCroup'),
        content: '',
        okText: 'Yes',
        okType: 'danger',
        cancelText: 'No',
        onOk() {
          let params = {
            groupNum: record.groupNum
          }
          _self
            .request('removeAccAuthDoor', params)
            .then(data => {
              if (data.code === '00') {
                _self.successMessage()
                _self.getAccAuthList()
              } else {
                _self.errorMessage(data.message)
              }
            })
            .catch(e => _self.errorMessage(e))
        },
        onCancel() {
          console.log('Cancel')
        }
      })
    },
    details(record) {
      this.detialVisiable = true
      this.editForm = Object.assign({}, record)
      let startTime = record.startTime.split('+')
      let endTime = record.endTime.split('+')
      this.editDate = [startTime[0], endTime[0]]
    },

    setEmployee(record) {
      this.employeeVisable = true
      this.groupNum = record.groupNum
      this.getEmployeeAllowList()
    },
    getEmployeeAllowList() {
      let params = {
        groupNum: this.groupNum
      }
      this.request('employeeAllowList', params)
        .then(data => {
          if (data.code === '00') {
            this.employeeList = data.data
            console.log()
          }
        })
        .catch(e => this.errorMessage(e))
    },
    showAddEmployee() {
      this.employeeModalVisable = true
      this.$nextTick(() => {
        this.$refs.assignEmployee.clearSelectRow()
        this.$refs.assignEmployee.getUnassignEmployee()
      })
    },
    showAddDoor() {
      this.assginDoorModal = true
      this.$nextTick(() => {
        this.$refs.assignDoor.clearSelectRow()
        this.$refs.assignDoor.getAccDoorNotAllow()
      })
    },
    unAuthEmployee(record) {
      this.$confirm({
        title: '此操作将进行人员解绑，是否继续?',
        okText: this.$t('common.okText'),
        okType: 'danger',
        cancelText: this.$t('common.cancelText'),
        onOk: () => {
          let params = {
            groupNum: this.groupNum,
            employeeNo: record.employeeNo
          }
          this.request('removeEmployee', params)
            .then(data => {
              if (data.code === '00') {
                this.successMessage()
                this.getEmployeeAllowList()
              }
            })
            .catch(e => this.errorMessage(e))
        },
        onCancel() {
          console.log('Cancel')
        }
      })
    },
    unAssignDoor(record) {
      this.$confirm({
        title: '此操作将进行门解绑，是否继续?',
        okText: this.$t('common.okText'),
        okType: 'danger',
        cancelText: this.$t('common.cancelText'),
        onOk: () => {
          let params = {
            groupNum: this.groupNum,
            doorNum: record.doorNum,
            sn: record.sn
          }
          this.request('removeDoor', params)
            .then(data => {
              if (data.code === '00') {
                this.successMessage()
                this.getAccDoorAllow()
              }
            })
            .catch(e => this.errorMessage(e))
        },
        onCancel() {
          console.log('Cancel')
        }
      })
    },

    hideAssignModal() {
      this.$refs.assignDoor
        .sumbitAssignDoor()
        .then(data => {
          if (data.code === '00') {
            this.assginDoorModal = false
            this.successMessage()
            this.getAccDoorAllow()
            // this.$refs.assignDoor.clearChecked()
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    },
    hideEmployeeModal() {
      this.$refs.assignEmployee
        .sumbitAssignEmployee()
        .then(data => {
          if (data.code === '00') {
            this.employeeModalVisable = false
            this.successMessage()
            this.getEmployeeAllowList()
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    },
    addAccAuth() {
      this.$refs.addRuleForm.validate(valid => {
        if (valid) {
          let params = {
            ...this.addFrom
          }
          this.request('addAccAuth', params)
            .then(data => {
              if (data.code === '00') {
                this.addVisible = false
                this.successMessage()
                this.getAccAuthList()
              }
            })
            .catch(e => this.errorMessage(e))
        }
      })
    },
    dateChange(date, dateString) {
      this.addFrom.startTime = dateString[0].replace(/\s+/g, 'T')
      this.addFrom.endTime = dateString[1].replace(/\s+/g, 'T')
    },
    editDateChange(date, dateString) {
      this.editForm.startTime = ''
      this.editForm.endTime = ''
      this.editForm.startTime = dateString[0].replace(/\s+/g, 'T')
      this.editForm.endTime = dateString[1].replace(/\s+/g, 'T')
    },
    setDoorSetting(record) {
      this.doorSettingVisable = true
      this.groupNum = record.groupNum
      this.getAccDoorAllow()
    },
    getAccDoorAllow() {
      let params = {
        groupNum: this.groupNum
      }
      this.request('accDoorAllow', params).then(data => {
        if (data.code === '00') {
          this.doorList = data.data
        }
      })
    },
    getAccTimezone() {
      this.request('getAccTimezone').then(data => {
        if (data.code === '00') {
          this.timeZoneList = data.data
          if (this.timeZoneList.length !== 0) {
            this.addFrom.timezoneNum = this.timeZoneList[0].timezoneNum
          }
        }
      })
    },
    getAccAuthList() {
      this.request('getAccAuthList', this.pager).then(data => {
        if (data.code === '00') {
          this.tableData = data.data
          this.setPager(data)
        }
      })
    },
    showCreate() {
      this.addVisible = true
      this.getAccTimezone()
    }
  }
}
</script>

<style scoped lang="less">
/deep/ .ant-modal-body {
  height: 500px;
  overflow: auto;
}
.save-btn {
  position: fixed;
  right: 10px;
  bottom: 30px;
}
.door--online {
  color: @primary-color;
}
.door--offline {
  color: @error-color;
}
.cancel-btn {
  position: fixed;
  right: 80px;
  bottom: 30px;
}
.drawer-header-cla {
  margin-bottom: 15px;
  display: flex;
  justify-content: space-between;
  .door-drawer-title {
    border-left: 4px solid @primary-color;
    padding-left: 16px;
  }
  .add-door-btn-cla {
    font-size: 20px;
    cursor: pointer;
    color: @primary-color;
  }
}

.door-list-box {
  display: flex;
  flex-wrap: wrap;
  padding: 0px;
  .door-name-cla {
    width: 150px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  li {
    height: 80px;
    border: 1px solid #e5e5e5;
    border-radius: 4px;
    box-shadow: 0 3px 10px 0 rgba(0, 0, 0, 0.2);
    margin: 0px 10px 10px 0px;
    cursor: pointer;
    list-style: none;
    display: flex;
    justify-content: flex-start;
    padding-left: 12px;
    padding-right: 12px;
    align-items: center;
    position: relative;
    .avatar-cla {
      margin-right: 12px;
    }
    &:hover {
      .delete-icon {
        display: block;
      }
    }
    .delete-icon {
      position: absolute;
      top: -7px;
      right: -20px;
      font-size: 20px;
      color: #ffffff;
      color: @error-color;
      border-radius: 24px;
      display: none;
    }
  }
}
</style>
