<template>
  <div class="att-device-box">
    <div class="att-device-left">
      <addDeviceList @selectItem="selectItem"></addDeviceList>
    </div>
    <div class="att-device-right">
      <div class="att-top-bar-cla">
        <span>{{ $t('att.employeeAuth') }}</span>
        <a-icon
          v-if="currentDivice && currentDivice.sn !== ''"
          @click="showBind"
          class="bind-btn-cla"
          type="plus-circle"
        />
      </div>

      <ul class="employee-list-box">
        <li :key="item.id" v-for="item of employeeList">
          <a-icon
            @click="unBind(item)"
            class="delete-icon"
            type="minus-circle"
          />
          <a-avatar
            v-if="item.avatar"
            class="avatar-cla"
            :size="64"
            :src="item.avatar"
          />
          <a-avatar class="avatar-cla" v-else :size="64" icon="user" />
          <div>
            <div style="height: 25px">{{ item.formattedName }}</div>
            <div>{{ item.employeeNo }}</div>
          </div>
        </li>
      </ul>
    </div>
    <a-modal
      v-model="visible"
      :title="$t('att.distriEmployee')"
      @ok="bindSubmit"
      @cancel="cancelModal"
    >
      <a-form-model layout="inline" :model="searchForm">
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
            @search="showBind"
          />
        </a-form-model-item>
      </a-form-model>
      <a-table
        style="height: 400px;overflow: auto;"
        :pagination="false"
        :row-selection="{
          selectedRowKeys: selectedRowKeys,
          onChange: onSelectChange
        }"
        rowKey="employeeNo"
        :columns="columns"
        :data-source="unassignedList"
        tableLayout="fixed"
        :loading="loading"
      />
    </a-modal>
  </div>
</template>

<script>
import addDeviceList from './addDeviceList'
export default {
  data() {
    return {
      loading: false,
      visible: false,
      currentDivice: null,
      employeeList: [],
      selectedRowKeys: [],
      unassignedList: [],
      searchForm: {
        type: 0,
        value: ''
      },
      optionList: [
        {
          key: 0,
          label: this.$t('employee.employeeNo'),
          attr: 'employeeNo'
        },
        {
          key: 1,
          label: this.$t('employee.formattedName'),
          attr: 'formattedName'
        }
      ],
      columns: [
        {
          key: 'employeeNo',
          title: this.$t('employee.employeeNo'),
          dataIndex: 'employeeNo'
        },
        {
          key: 'id',
          title: this.$t('employee.formattedName'),
          dataIndex: 'formattedName'
        }
      ]
    }
  },
  components: {
    addDeviceList
  },
  mounted() {},
  watch: {
    currentDivice: {
      handler(newValue) {
        if (newValue) {
          this.deviceEmployeeList()
        }
        // this.deviceEmployeeList()
      },
      immediate: false
    }
  },
  methods: {
    cancelModal() {
      this.unassignedList = null
      this.visible = false
    },
    selectItem(item) {
      if (item) {
        this.currentDivice = item
      } else {
        this.currentDivice = {
          sn: ''
        }
        this.deviceEmployeeList()
      }
    },
    showBind() {
      this.visible = true
      this.selectedRowKeys = []
      this.loading = true
      let params = {}
      this.optionList.filter(item => {
        if (item.key === this.searchForm.type) {
          return (params[item.attr] = this.searchForm.value)
        }
      })
      this.request('unassignedList', {
        sn: this.currentDivice.sn,
        ...params
      })
        .then(data => {
          if (data.code === '00') {
            this.loading = false
            // let employeeNum = data.data
            // if (employeeNum.length < 10) {
            //   this.unassignedList = employeeNum
            // } else {
            //   this.unassignedList = employeeNum.splice(0, 8)
            // }
            this.unassignedList = data.data
            this.loading = false
          }
        })
        .catch(e => {
          this.errorMessage(e)
        })
    },
    scrollUnassignedEmployee() {
      console.log('ssssss')
    },
    bindSubmit() {
      this.request('deviceEmployeeBind', {
        sn: this.currentDivice.sn,
        employeeNos: this.selectedRowKeys
      })
        .then(data => {
          if (data.code === '00') {
            this.visible = false
            this.successMessage()
            this.deviceEmployeeList()
          } else {
            this.errorMessage(data)
          }
        })
        .catch(e => {
          this.errorMessage(e)
          console.log(e)
        })
    },
    unBind(item) {
      //解除绑定
      this.$confirm({
        title: this.$t('att.unbindEmployee'),
        okText: this.$t('common.okText'),
        okType: 'danger',
        cancelText: this.$t('common.cancelText'),
        onOk: () => {
          this.request('deviceEmployeeUnBind', {
            sn: item.sn,
            employeeNo: item.employeeNo
          })
            .then(data => {
              if (data.code === '00') {
                this.successMessage()
                this.deviceEmployeeList()
              } else {
                this.errorMessage(data)
              }
            })
            .catch(e => {
              this.errorMessage(e)
              console.log(e)
            })
        },
        onCancel() {
          console.log('Cancel')
        }
      })
    },
    onSelectChange(selectedRowKeys) {
      //选择绑定
      this.selectedRowKeys = selectedRowKeys
    },
    deviceEmployeeList() {
      let params = {
        sn: this.currentDivice.sn
      }
      this.request('deviceEmployeeList', params)
        .then(data => {
          if (data.code === '00') {
            this.employeeList = data.data
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
.att-device-box {
  display: flex;
  align-items: stretch;
  min-height: calc(100vh - @layout-header-height - @breadcrumb-height - 24px);
  background-color: #f0f2f5;
  .att-device-left {
    background-color: #ffff;
    border-radius: 4px;
    padding: 15px;
    width: 400px;
    flex-shrink: 0;
  }
  .att-device-right {
    background-color: #ffff;
    border-radius: 4px;
    padding: 0px 15px 15px 15px;
    flex-grow: 1;
    margin-left: 15px;
  }
}
.att-top-bar-cla {
  height: 40px;
  line-height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .bind-btn-cla {
    font-size: 20px;
    cursor: pointer;
    color: @primary-color;
  }
}

.employee-list-box {
  display: flex;
  flex-wrap: wrap;
  padding: 0px;
  li {
    width: 190px;
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
