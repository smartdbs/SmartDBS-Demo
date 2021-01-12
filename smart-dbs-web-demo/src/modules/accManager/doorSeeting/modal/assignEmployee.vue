<template>
  <div class="employee_cla">
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
          @search="getUnassignEmployee"
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
      rowKey="id"
      tableLayout="fixed"
      style="height: 400px;overflow: auto;"
      :loading="loading"
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
  props: ['groupNums'],
  watch: {
    groupNums: {
      handler() {},
      immediate: true
    }
  },
  data() {
    return {
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
      searchForm: {
        type: 0,
        value: ''
      },
      loading: false,
      tableData: [],
      columns: [
        {
          title: this.$t('employee.employeeNo'),
          dataIndex: 'employeeNo'
        },
        {
          title: this.$t('employee.formattedName'),
          dataIndex: 'formattedName'
        }
      ],
      selectedRowKeys: [],
      selectedRow: []
    }
  },
  methods: {
    onSelectChange(selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRow = selectedRows
    },
    clearSelectRow() {
      this.selectedRowKeys = []
    },
    getUnassignEmployee() {
      let parmas = {}
      this.optionList.filter(item => {
        if (item.key === this.searchForm.type) {
          return (parmas[item.attr] = this.searchForm.value)
        }
      })
      parmas.groupNum = this.groupNums
      this.loading = true
      this.request('employeeUnassignList', parmas)
        .then(data => {
          if (data.code === '00') {
            this.loading = false
            this.tableData = data.data
            this.loading = false
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    },
    sumbitAssignEmployee() {
      return new Promise((resolve, reject) => {
        let parmas = {
          groupNum: this.groupNums,
          employees: this.selectedRow
        }
        this.request('assignEmployee', parmas).then(data => {
          if (data.code === '00') {
            resolve(data)
          } else {
            reject(data)
          }
        })
      })
    }
  },
  mounted() {
    this.getUnassignEmployee()
  }
}
</script>

<style scoped lang="less">
.employee_cla {
}
</style>
