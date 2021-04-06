<template>
  <div>
    <div class="acc-bottom-cla">
      <a-table
        :pagination="false"
        :columns="columns"
        :data-source="tableData"
        rowKey="timezoneNum"
      >
        <div slot="action" slot-scope="record">
          <i
            :title="$t('acc.timezoneSeeting')"
            @click="setAccTimezone(record)"
            class="shijian-icon-cla icon iconfont zk-icon-shijian"
          ></i>
          <!-- <i
            :title="$t('common.details')"
            @click="details(record)"
            class="details-cla icon iconfont zk-icon-ziyuan"
          ></i> -->

          <i
            :title="$t('common.delete')"
            @click="deleteFn(record)"
            class="delete-icon icon iconfont zk-icon-cangpeitubiao_shanchu"
          ></i>
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
        :show-total="
          total => `${this.$t('common.showTotal', { total: pager.total })}`
        "
        @change="pageChange(arguments[0], arguments[1], getPageList)"
        @showSizeChange="pageChange(arguments[0], arguments[1], getPageList)"
      ></a-pagination>
    </div>

    <a-drawer
      placement="right"
      :title="isUpdate ? $t('acc.timezoneSeeting') : $t('acc.createTimeSlot')"
      :closable="false"
      :visible="addVisible"
      :width="600"
      @close="onCloseAdd()"
      class="add-drawer-cla"
    >
      <a-form-model
        :label-col="{ span: 8 }"
        :wrapper-col="{ span: 14 }"
        labelAlign="left"
        :model="editFrom"
        ref="editForm"
        :rules="rules"
      >
        <a-form-model-item :label="$t('acc.timezoneName')" prop="timezoneName">
          <a-input v-model="editFrom.timezoneName" />
        </a-form-model-item>
        <a-form-model-item :label="$t('acc.week1')">
          <RangeTimePicker v-model="editFrom.week1"></RangeTimePicker>
        </a-form-model-item>
        <a-form-model-item :label="$t('acc.week2')">
          <RangeTimePicker v-model="editFrom.week2"></RangeTimePicker>
        </a-form-model-item>
        <a-form-model-item :label="$t('acc.week3')">
          <RangeTimePicker v-model="editFrom.week3"></RangeTimePicker>
        </a-form-model-item>
        <a-form-model-item :label="$t('acc.week4')">
          <RangeTimePicker v-model="editFrom.week4"></RangeTimePicker>
        </a-form-model-item>
        <a-form-model-item :label="$t('acc.week5')">
          <RangeTimePicker v-model="editFrom.week5"></RangeTimePicker>
        </a-form-model-item>
        <a-form-model-item :label="$t('acc.week6')">
          <RangeTimePicker v-model="editFrom.week6"></RangeTimePicker>
        </a-form-model-item>
        <a-form-model-item :label="$t('acc.week0')">
          <RangeTimePicker v-model="editFrom.week0"></RangeTimePicker>
        </a-form-model-item>
        <a-checkbox
          v-if="editFrom.week1.length > 1"
          @change="copyChange"
          :checked="checked"
        >
          {{ $t('acc.copyTip') }}
        </a-checkbox>
      </a-form-model>
      <a-button @click="saveAccTimezone" class="save-btn" type="primary">
        {{ $t('common.save') }}
      </a-button>
    </a-drawer>

    <!-- <a-drawer
      placement="right"
      :title="$t('acc.baseInfo')"
      :closable="false"
      :visible="editBaseVisible"
      :width="500"
      @close="onCloseEdit()"
    >
      <a-form-model
        :label-col="{ span: 8 }"
        :wrapper-col="{ span: 14 }"
        labelAlign="left"
        :model="currentRow"
        :rules="rules"
        ref="currentRow"
      >
        <a-form-model-item :label="$t('acc.timezoneName')" prop="timezoneName">
          <a-input v-model="currentRow.timezoneName" />
        </a-form-model-item>
      </a-form-model>
      <a-button @click="saveEdit" class="save-btn" type="primary">
        {{ $t('common.save') }}
      </a-button>
    </a-drawer> -->
  </div>
</template>
<script>
export default {
  data() {
    return {
      timeValue: ['', ''],
      addVisible: false,
      editBaseVisible: false,
      tableData: [],
      currentRow: {},
      checked: false,
      columns: [
        // {
        //   title: this.$t('acc.timezoneNum'),
        //   dataIndex: 'timezoneNum'
        // },
        {
          title: this.$t('acc.timezoneName'),
          dataIndex: 'timezoneName'
        },
        {
          title: this.$t('common.action'),
          key: 'action',
          scopedSlots: { customRender: 'action' },
          width: 300
        }
      ],
      isEdit: false,
      isUpdate: false,
      editFrom: {
        timezoneNum: '',
        timezoneName: '',
        week0: [],
        week1: [],
        week2: [],
        week3: [],
        week4: [],
        week5: [],
        week6: []
      },
      weeks: [0, 1, 2, 3, 4, 5, 6],
      rules: {
        timezoneName: [
          {
            required: true,
            message: '请输入时间段名称',
            trigger: ['blur', 'change']
          }
        ]
      }
    }
  },
  watch: {
    // editFrom: {
    //   handler(newVal) {
    //     if (newVal) {
    //       let arr = []
    //       Object.keys(newVal).forEach(item => {
    //         if (item.startsWith('week')) {
    //           arr.push(newVal.item)
    //         }
    //       })
    //       this.isCheckAll(newVal.week0, arr)
    //     }
    //   },
    //   immediate: true,
    //   deep: true
    // }
  },
  mounted() {
    this.getPageList()
  },
  methods: {
    getPageList() {
      this.request('getAccTimezoneList', {}, this.pager)
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
    setAccTimezone(row) {
      this.isEdit = true
      this.isUpdate = true
      this.editFrom.timezoneNum = row.timezoneNum
      this.editFrom.timezoneName = row.timezoneName
      // this.isCheckAll(row.detail[0], row.detail)
      row.detail.forEach(item => {
        this.editFrom['week' + item.week] = [item.startTime, item.endTime]
      })
      this.addVisible = true
    },
    // isCheckAll(mon, arr) {
    //   let statrTime = mon.statrTime
    //   let endTime = mon.endTime
    //   arr.some(item => {
    //     if (statrTime !== item.statrTime || endTime !== item.endTime) {
    //       this.checked = false
    //       return
    //     }
    //     this.checked = true
    //   })
    //   console.log(this.checked)
    // },
    details(row) {
      this.editBaseVisible = true
      this.currentRow = Object.assign({}, row)
    },
    saveEdit() {
      this.$refs.currentRow.validate(valid => {
        if (valid) {
          this.request('accTimezoneUpdate', {
            timezoneNum: this.currentRow.timezoneNum,
            timezoneName: this.currentRow.timezoneName
          })
            .then(data => {
              if (data.code === '00') {
                this.successMessage()
                this.editBaseVisible = false
                this.getPageList()
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
      let _that = this
      this.$confirm({
        title: this.$t('common.deleteTip'),
        content: '',
        okText: this.$t('common.okText'),
        cancelText: this.$t('common.cancelText'),
        okType: 'danger',
        onOk() {
          _that
            .request('accTimezoneRemove', {
              timezoneNum: record.timezoneNum
            })
            .then(data => {
              if (data.code === '00') {
                _that.successMessage()
                _that.getPageList()
              } else {
                _that.errorMessage(data.message)
              }
            })
            .catch(e => {
              _that.errorMessage(e)
            })
        },
        onCancel() {
          console.log('Cancel')
        }
      })
    },
    onCloseEdit() {
      this.editBaseVisible = false
    },
    saveAccTimezone() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          if (this.isUpdate) {
            console.log('ssss')
            let params = {
              timezoneNum: this.editFrom.timezoneNum,
              timezoneName: this.editFrom.timezoneName,
              detail: []
            }
            Object.keys(this.editFrom).forEach(item => {
              if (item.startsWith('week')) {
                let week = item.substr(4)
                let info = this.editFrom[item]
                if (info instanceof Array && info.length === 2) {
                  params.detail.push({
                    week: week,
                    startTime: info[0],
                    endTime: info[1]
                  })
                } else {
                  params.detail.push({
                    week: week,
                    startTime: '',
                    endTime: ''
                  })
                }
              }
            })
            this.request('updateAccTimezone', params)
              .then(data => {
                if (data.code === '00') {
                  this.successMessage()
                  this.addVisible = false
                  this.getPageList()
                } else {
                  this.errorMessage(data.message)
                }
              })
              .catch(e => this.errorMessage(e))
          } else {
            let params = {
              detail: []
            }
            if (this.isEdit) {
              params.timezoneNum = this.editFrom.timezoneNum
            }
            params.timezoneName = this.editFrom.timezoneName
            Object.keys(this.editFrom).forEach(item => {
              if (item.startsWith('week')) {
                let week = item.substr(4)
                let info = this.editFrom[item]
                if (info instanceof Array && info.length === 2) {
                  params.detail.push({
                    week: week,
                    startTime: info[0],
                    endTime: info[1]
                  })
                } else {
                  params.detail.push({
                    week: week,
                    startTime: '',
                    endTime: ''
                  })
                }
              }
            })

            this.request(
              this.isEdit ? 'accTimezoneSetUp' : 'accTimezoneAdd',
              params
            )
              .then(data => {
                if (data.code === '00') {
                  this.successMessage()
                  this.addVisible = false
                  this.getPageList()
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
      })
    },
    showCreate() {
      this.isEdit = false
      this.isUpdate = false
      this.editFrom.timezoneNum = ''
      this.editFrom.timezoneName = ''
      this.weeks.forEach(item => {
        this.editFrom['week' + item] = []
      })
      this.addVisible = true
      this.checked = false
    },
    onCloseAdd() {
      //关闭新增窗口
      this.addVisible = false
      this.checked = false
    },
    copyChange(e) {
      this.checked = !this.checked
      let result = e.target.checked
      if (result) {
        let value = this.editFrom.week1
        if (value instanceof Array && value.length === 2) {
          this.weeks.forEach(item => {
            this.editFrom['week' + item] = value
          })
        }
      } else {
        // this.weeks.forEach(item => {
        //   if (!(item === 1)) {
        //     this.editFrom['week' + item] = []
        //   }
        // })
      }
    }
  }
}
</script>

<style scoped lang="less">
.acc-bottom-cla {
  /deep/ .ant-table-thead > tr > th {
    width: 50%;
  }
}
.save-btn {
  position: fixed;
  right: 10px;
  bottom: 30px;
}
</style>
