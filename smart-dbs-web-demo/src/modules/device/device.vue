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
          :label-col="{ span: 6 }"
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
          :label-col="{ span: 8 }"
          :wrapper-col="{ span: 14 }"
          labelAlign="left"
          :model="editRow"
          :rules="rules"
        >
          <a-form-model-item :label="$t('device.deviceName')" prop="alias">
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
            <span style="position: relative;"
              ><span class="circle-cla" v-if="isUpdateFwversion"></span
              >{{ editRow.fwVersion }}</span
            >
            <a-button
              style="border:none"
              icon="cloud-download"
              v-if="isUpdateFwversion"
              @click.stop="isUpgradeFirmware"
              >有新版本</a-button
            >
          </a-form-model-item>
        </a-form-model>
        <div class="network-info-cla">{{ $t('device.networkInfo') }}</div>
        <a-form-model
          :label-col="{ span: 8 }"
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
      <a-drawer
        placement="right"
        :closable="false"
        :visible="upgradeVisible"
        :width="500"
        @close="closeUpgradeDrawer"
        class="edit-drawer-cla"
      >
        <div>
          <i class="iconfont zk-icon-fanhui enable-cla" @click="goBack"></i
          ><span style="margin-left:10px;color:black">新版功能：</span>
        </div>
        <template v-if="!showUpgradeRes">
          <div>{{ firmwareInfo.version }}</div>
          <div>
            <ul>
              <li
                v-for="(item, index) in firmwareInfo.description"
                :key="index"
              >
                {{ item }}
              </li>
            </ul>
            <!-- <div>{{ firmwareInfo.description }}</div> -->
          </div>
          <div style="text-align:center">
            <a-popconfirm
              title="此操作将升级固件，是否继续？"
              ok-text="是"
              cancel-text="否"
              @confirm="upgrade"
              @cancel="cancel"
            >
              <a-button type="primary" style="width:140px">升级</a-button>
            </a-popconfirm>
          </div>
        </template>
        <template v-else>
          <a-steps
            labelPlacement="vertical"
            v-model="currentStep"
            disabled
            style="margin-top:100px"
          >
            <!-- first step -->
            <a-step title="开始升级" disabled v-if="!isOffline">
              <a-icon
                slot="icon"
                type="sync"
                :spin="active === 0"
                v-if="!overFirst"
              />
              <a-icon slot="icon" type="check-circle" v-else />
            </a-step>
            <a-step v-else title="升级失败">
              <a-icon type="close-circle" slot="icon" class="disable-cla" />
            </a-step>
            <!-- second step -->
            <a-step :title="secondTitle" v-if="!isShowError" disabled>
              <a-icon
                slot="icon"
                type="sync"
                :spin="currentStep === 1"
                v-if="!overSecond"
              />
              <a-icon slot="icon" type="check-circle" v-else></a-icon>
            </a-step>

            <a-step title="下载失败" v-else disabled>
              <!-- <a-icon slot="icon" type="sync" :spin="active === 2" /> -->
              <a-icon type="close-circle" slot="icon" class="disable-cla" />
            </a-step>

            <!-- third step -->
            <a-step
              title="安装中"
              v-if="!isShowSuccess && !isInstallError"
              disabled
            >
              <a-icon
                slot="icon"
                type="sync"
                :spin="currentStep === 2 || currentStep === 3"
              />
            </a-step>

            <a-step
              title="安装失败"
              v-else-if="!isShowSuccess && isInstallError"
              disabled
            >
              <a-icon type="close-circle" slot="icon" class="disable-cla" />
            </a-step>

            <a-step
              title="升级完成"
              v-else-if="!isInstallError && isShowSuccess"
              disabled
            >
              <a-icon type="sync" slot="icon" v-if="!overThird" />
              <a-icon type="check-circle" slot="icon" v-else></a-icon>
            </a-step>

            <a-step title="升级失败" v-else-if="failUpgrade">
              <a-icon type="close-circle" slot="icon" class="disable-cla" />
            </a-step>
          </a-steps>
          <div style="text-align:center;margin-top:15px">
            <a-button
              @click.stop="finishUpgrade"
              type="primary"
              v-if="isShowSuccess"
              >升级完成</a-button
            >
            <a-button
              @click.stop="upgradeAagin"
              v-if="isShowError || isInstallError || isOffline"
              type="danger"
              >重试</a-button
            >
          </div>
        </template>
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
      upgradeVisible: false,
      showUpgradeRes: false,
      isUpdateFwversion: false,
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
      },
      firmwareInfo: [],
      active: 0, // 初始状态  0 升级中 1-升级成功；2-升级失败；3-下载中 4-烧制中  5-升级超时
      isShowError: false, // 是否展示错误
      isShowSuccess: false,
      isInstallError: false,
      failUpgrade: false,
      secondTitle: '下载中',
      overFirst: false, //是否完成第一步
      overSecond: false,
      overThird: false,
      currentStep: 0, // 当前第几步
      timer: null,
      isOffline: false
    }
  },
  mounted() {
    this.getDeviceList()
  },
  filters: {
    isHtml(val) {
      console.log(val)
      return val.replace('\n', '<br>')
    }
  },
  methods: {
    upgradeAagin() {
      // 升级失败 再次升级
      this.active = 0
      this.currentStep = 0

      this.overFirst = false
      this.overSecond = false
      this.overThird = false

      this.isOffline = false
      this.isShowError = false
      this.isInstallError = false
      this.secondTitle = '下载中'

      this.failUpgrade = false
      this.upgrade()
    },
    finishUpgrade() {
      // 完成升级-展示detail
      this.upgradeVisible = false
      this.editVisible = true
      this.isUpdateFwversion = false
    },
    init() {
      if (this.currentStep === 0) {
        if (this.active === 0) {
          // 初始状态
          // this.active = 5 //模拟  3 进下载中  5 2 失败
          // this.active = 1
          this.currentStep = 1
          this.overFirst = true
          return
        }

        if (this.active === 1) {
          //  直接成功
          this.currentStep = 3
          this.overFirst = true
          this.overSecond = true
          this.overThird = true
          this.secondTitle = '下载完成'
          this.isShowSuccess = true
          clearInterval(this.timer)
          this.timer = null
          return
        }

        if (this.active === 5 || this.active === 2) {
          // 直接返回失败
          this.isOffline = true
          clearInterval(this.timer)
          this.timer = null
          return
        }
        if (this.active === 3) {
          // 下载中 直接进入第二步
          this.currentStep = 1
          this.overFirst = true
          return
        }
        if (this.active === 4) {
          // 安装中  直接进入到第三步
          this.currentStep = 2
          this.overFirst = true
          this.overSecond = true
          return
        }
      }

      if (this.currentStep === 1) {
        // 下载状态
        if (this.active === 5 || this.active === 2) {
          // 下载失败
          this.isShowError = true
          this.currentStep = 1
          clearInterval(this.timer)
          this.timer = null
          return
        }
        if (this.active === 3 || this.active === 4) {
          // 下载完成
          // this.active = 4 // 模拟 4 安装中  2 5  安装失败   1 完成
          this.currentStep = 2
          this.overSecond = true
          this.secondTitle = '下载完成'
          return
        }
        if (this.active === 1) {
          // 直接跳过下载、安装中
          this.currentStep = 3
          this.overFirst = true
          this.overSecond = true
          this.overThird = true
          this.secondTitle = '下载完成'
          this.isShowSuccess = true
          clearInterval(this.timer)
          this.timer = null
          return
        }
      }

      if (this.currentStep === 2) {
        // 安装状态
        if (this.active === 2 || this.active === 5) {
          // 安装失败
          this.isInstallError = true
          clearInterval(this.timer)
          this.timer = null
          return
        }

        if (this.active === 4) {
          // 安装中
          this.active = 1
          this.currentStep = 3
          this.isUploadSuccess = true
          return
        }
        if (this.active === 1) {
          // 直接跳过下载、安装中
          this.currentStep = 3
          this.overFirst = true
          this.overSecond = true
          this.overThird = true
          this.secondTitle = '下载完成'
          this.isShowSuccess = true
          clearInterval(this.timer)
          this.timer = null
          return
        }
      }
      if (this.currentStep === 3) {
        if (this.active === 1) {
          // 升级完成
          this.currentStep = 3
          this.overThird = true
          this.isShowSuccess = true
          clearInterval(this.timer)
          this.timer = null
          return
        }
        if (this.active === 5 || this.active === 2) {
          // 升级失败
          this.failUpgrade = true
          clearInterval(this.timer)
          this.timer = null
          return
        }
      }
      // //下载中
      // if (this.active === 3) {
      //   this.active = 4
      //   this.currentStep = 2
      //   this.overSecond = true
      //   this.secondTitle = '下载完成'
      //   return
      // }
      // // 下载失败
      // if (this.active === 3) {
      //   this.active = 5
      //   this.isShowError = true
      //   this.currentStep = 1
      //   clearInterval(this.timer)
      //   this.timer = null
      //   return
      // }
      // // 安装中
      // if (this.active === 4) {
      //   this.active = 1
      //   this.currentStep = 3
      //   this.isUploadSuccess = true
      //   return
      // }
      // // 安装失败
      // if (this.active === 4) {
      //   this.active = 2
      //   this.currentStep = 3
      //   this.isInstallError = true
      //   clearInterval(this.timer)
      //   this.timer = null
      //   return
      // }
      // // 升级完成
      // if (this.active === 1) {
      //   this.currentStep = 3
      //   this.overThird = true
      //   this.isShowSuccess = true
      //   clearInterval(this.timer)
      //   this.timer = null
      //   return
      // }
    },
    closeUpgradeDrawer() {
      if (this.showUpgradeRes) {
        // 获取历史结果 是否在升级中界面
        if (
          this.isOffline ||
          this.isShowError ||
          this.isInstallError ||
          this.isShowSuccess ||
          this.failUpgrade
        ) {
          // 升级过程 不可关闭
          this.upgradeVisible = false
        } else {
          // 升级完成后 可关闭
          this.upgradeVisible = true
          this.isUpdateFwversion = false
        }
      } else {
        this.upgradeVisible = false
        this.isUpdateFwversion = false
      }
    },
    goBack() {
      if (this.showUpgradeRes) {
        this.showUpgradeRes = false
      } else if (!this.showUpgradeRes) {
        this.upgradeVisible = false
        this.editVisible = true
      }
    },
    upgrade() {
      this.showUpgradeRes = true
      let params = {
        sn: this.editRow.sn,
        version: this.firmwareInfo.version
      }
      this.request('upgradeFwVersion', params)
        .then(data => {
          if (data.code === '00') {
            if (data.data.taskId) {
              this.timer = setInterval(() => {
                // 定时获取升级状态
                this.getUpgrageHistory(data.data.taskId).then(res => {
                  this.active = res
                  this.init()
                })
              }, 2000)
            } else {
              this.errorMessage('缺少taskId')
            }
          } else {
            this.errorMessage(data.message)
            this.isOffline = true
          }
        })
        .catch(e => this.errorMessage(e))
    },
    // 升级进度
    getUpgrageHistory(taskId) {
      return new Promise((resolve, reject) => {
        let params = {
          sn: this.editRow.sn,
          taskId
          // state: this.active //模拟
        }
        this.request('upgradeFwVersionHistory', params, this.pager)
          .then(data => {
            if (data.code === '00') {
              resolve(data.data[0].status)
            } else {
              reject(data.message)
              this.errorMessage(data.message)
              clearInterval(this.timer)
              this.timer = null
            }
          })
          .catch(e => {
            this.errorMessage(e)
            clearInterval(this.timer)
            this.timer = null
          })
      })
    },
    isUpgradeFirmware() {
      if (this.isUpdateFwversion) {
        this.upgradeVisible = true
        this.editVisible = false
      }
    },
    loadTimeZoneList() {
      this.request('loadTimezoneList')
        .then(data => {
          if (data.code === '00') {
            this.timeZoneListOptions = data.data
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    },
    onTimeZoneChange(value) {
      if (value.length === 1) {
        this.addFrom.timeZone = value[0]
        delete this.addForm.timezoneId
      } else {
        this.addFrom.timeZone = value[0]
        this.addFrom.timezoneId = value[1]
      }
    },
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
      this.isShowError = false
      this.isShowSuccess = false
      this.isInstallError = false
      this.isOffline = false
      this.failUpgrade = false

      this.overFirst = false
      this.overSecond = false
      this.overThird = false

      this.currentStep = 0
      this.active = 0
      this.showUpgradeRes = false
      this.title = '下载中'

      this.getUpgrageList(record.sn)
      this.originRow = record
      this.editRow = Object.assign({}, record)
      // this.editRow.protocol = this.deviceProtocol[this.editRow.protocol]
      // this.editRow.model
      // this.editRow.timeZone = [this.editRow.timeZone, this.editRow.timezoneId]
      this.editVisible = true
    },

    getUpgrageList(sn) {
      this.request('getUpgradeFwVersionListBySn', {
        sn
      })
        .then(data => {
          if (data.code === '00') {
            if (data.data.length !== 0) {
              this.isUpdateFwversion = true
              this.firmwareInfo = data.data[0] // 取升级列表第一项
              if (this.firmwareInfo.description) {
                let temp = this.firmwareInfo.description.split('\\n')
                this.firmwareInfo.description = temp
              }
            } else {
              this.isUpdateFwversion = false
            }
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    },
    submitEdit() {
      // let timezone = this.editRow.timeZone
      // let timeZone, timezoneId
      // if (timezone) {
      //   if (timezone.length === 1) {
      //     timeZone = timezone[0]
      //   } else {
      //     timeZone = timezone[0]
      //     timezoneId = timezone[1]
      //   }
      // }
      this.request('updateDevice', {
        sn: this.editRow.sn,
        alias: this.editRow.alias
        // timeZone,
        // timezoneId
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
        title: this.$t('device.rebortDevice'),
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
.enable-cla {
  color: @primary-color;
}
.disable-cla {
  color: @error-color;
}
.circle-cla {
  position: absolute;
  top: 0px;
  left: -10px;
  width: 6px;
  height: 6px;
  background: red;
  display: inline-block;
  border-radius: 50%;
}
ul {
  list-style: none;
}
</style>
