<template>
  <div class="assign_cla">
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
          @search="getAccDoorNotAllow"
        />
      </a-form-model-item>
    </a-form-model>

    <a-table
      :pagination="false"
      :columns="columns"
      :data-source="tableData"
      :row-selection="{
        selectedRowKeys: selectedRowKeys,
        onChange: onSelectChange
      }"
      rowKey="doorId"
      tableLayout="fixed"
    >
      <div slot="status" slot-scope="record">
        <span
          :class="record.status === '0' ? 'device--offline' : 'device--online'"
          >{{
            record.status === '0' ? $t('door.offline') : $t('door.online')
          }}</span
        >
      </div>
    </a-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
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
      searchForm: {
        type: 0,
        value: ''
      },
      tableData: [],
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
          title: this.$t('door.status'),
          scopedSlots: { customRender: 'status' }
        }
      ],
      selectedRows: [],
      selectedRowKeys: []
    }
  },
  props: ['groupNums'],
  watch: {
    groupNums: {
      handler() {},
      immediate: true
    }
  },
  methods: {
    clearSelectRow() {
      this.selectedRowKeys = []
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    sumbitAssignDoor() {
      return new Promise((resolve, reject) => {
        let params = {
          groupNum: this.groupNums,
          doors: this.selectedRows
        }
        this.request('assignDoor', params).then(data => {
          if (data.code === '00') {
            resolve(data)
          } else {
            reject(data.message)
          }
        })
      })
    },
    getAccDoorNotAllow() {
      let params = {}
      this.optionList.filter(item => {
        if (item.key === this.searchForm.type) {
          return (params[item.attr] = this.searchForm.value)
        }
      })
      params.groupNum = this.groupNums
      this.request('accDoorNotAllow', params)
        .then(data => {
          if (data.code === '00') {
            this.tableData = data.data
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    }
  },
  mounted() {
    this.getAccDoorNotAllow()
  }
}
</script>

<style scoped lang="less">
.device--offline {
  color: @error-color;
}
.device--online {
  color: @primary-color;
}
</style>
