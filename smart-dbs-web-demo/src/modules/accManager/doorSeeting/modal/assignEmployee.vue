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
      style="height: 400px; overflow: auto"
      :loading="loading"
    >
    </a-table>
    <a-row>
      <a-col :span="6"
        ><span
          >共{{ showLength }}/{{ orginUnassignedList.length }}条</span
        ></a-col
      >
      <a-col :span="12" style="text-align: center">
        <span v-if="!noData" @click="loadMore" class="load-more-cla">
          点击加载更多
        </span>
        <span v-else>已经到底了</span>
      </a-col>
    </a-row>
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
  computed: {
    showLength() {
      return this.tableData.length
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
      orginUnassignedList: [],
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
      selectedRow: [],
      params: {
        curPage: 1, // 当前页数
        pageSize: 50, // 一次加载多少
        count: 0 // 总页数
      },
      noData: false,
      timer: null
    }
  },
  methods: {
    closeEmp() {
      this.onData = false
      this.tableData = []
      this.searchForm.type = 0
      this.searchForm.value = ''
    },
    loadMore() {
      if (this.timer !== null) {
        return
      }
      if (this.params.curPage > this.params.count) {
        this.noData = true
        return
      }
      this.loading = true
      let curSize =
        this.params.curPage === 1
          ? this.params.pageSize
          : this.params.curPage * this.params.pageSize

      this.params.curPage += 1

      let newPageData = this.orginUnassignedList.slice(
        curSize,
        curSize + this.params.pageSize
      )

      this.timer = setTimeout(() => {
        this.tableData = this.tableData.concat(newPageData) // 合并数据
        this.loading = false
        clearTimeout(this.timer)
        this.timer = null
      }, 1500)
    },
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
          this.noData = false
          if (data.code === '00') {
            let employeeNum = data.data
            this.orginUnassignedList = employeeNum
            this.params.curPage = 1
            this.params.count = Math.ceil(
              this.orginUnassignedList.length / this.params.pageSize
            )
            if (employeeNum.length < this.params.pageSize) {
              this.tableData = employeeNum
            } else {
              this.tableData = employeeNum.slice(0, this.params.pageSize)
            }
            this.loading = false
            // this.tableData = data.data
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
.load-more-cla {
  &:hover {
    cursor: pointer;
    color: @primary-color;
  }
}
</style>
