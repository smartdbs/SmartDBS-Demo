i<template>
  <topDownLayout>
    <div class="employee-top-bar" slot="top">
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
      <a-button @click.stop="showAdd" icon="plus" type="primary">
        {{ $t('employee.addEmployee') }}
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
        <div slot="cardNo" slot-scope="record">
          {{ isEmpty(record.cardNo) }}
        </div>
        <div slot="devicePermission" slot-scope="record">
          <span v-if="record.devicePermission === 14">{{
            $t('employee.deviceAdmin')
          }}</span>
          <span v-else>{{ $t('employee.deviceUser') }}</span>
        </div>
        <div class="template-cla" slot="template" slot-scope="record">
          <i
            :title="
              record.fingerCount > 0
                ? $t('employee.entryFinger')
                : $t('employee.noEntryFinger')
            "
            :class="{ 'active-icon-cla': record.fingerCount > 0 }"
            class="icon iconfont zk-icon-ic_zw-"
          ></i>
          <i
            :title="
              record.visibleFaceCount > 0
                ? $t('employee.entryVisible')
                : $t('employee.noEntryVisible')
            "
            :class="{ 'active-icon-cla': record.visibleFaceCount > 0 }"
            class="icon iconfont zk-icon-renlianshibie-"
          ></i>
          <i
            :title="
              record.faceCount > 0
                ? $t('employee.entryFace')
                : $t('employee.noEntryFace')
            "
            :class="{ 'active-icon-cla': record.faceCount > 0 }"
            class="icon iconfont zk-icon-ic_rl"
          ></i>
          <i
            :title="
              record.palmPrintCount > 0
                ? $t('employee.entryPlam')
                : $t('employee.noEntryPlam')
            "
            :class="{ 'active-icon-cla': record.palmPrintCount > 0 }"
            class="icon iconfont zk-icon-ic_zw"
          ></i>
        </div>
        <div slot="action" slot-scope="record">
          <i
            class="details-cla icon iconfont zk-icon-renyuan"
            :title="$t('employee.remoteRegister')"
            @click="remoteReg(record)"
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
          :show-total="
            total => `${this.$t('common.showTotal', { total: pager.total })}`
          "
          @change="pageChange(arguments[0], arguments[1], getEmployeeList)"
          @showSizeChange="
            pageChange(arguments[0], arguments[1], getEmployeeList)
          "
        ></a-pagination>
      </div>

      <a-drawer
        :title="$t('employee.editEmployee')"
        placement="right"
        :closable="false"
        :visible="visible"
        :width="600"
        @close="closeDrawer"
      >
        <a-form-model
          :model="editForm"
          :rules="rules"
          ref="editEmployeeFrom"
          layout="horizontal"
          :label-col="{ span: 8 }"
          :wrapper-col="{ span: 15 }"
          labelAlign="left"
        >
          <div class="base-info-cla">{{ $t('employee.baseInfo') }}</div>
          <a-row>
            <a-col :span="18">
              <a-form-model-item
                :label="$t('employee.employeeNo')"
                prop="employeeNo"
              >
                {{ editForm.employeeNo }}
              </a-form-model-item>

              <a-form-model-item
                :label="$t('employee.lastName')"
                prop="lastName"
              >
                <a-input v-model="editForm.lastName" />
              </a-form-model-item>

              <a-form-model-item
                :label="$t('employee.firstName')"
                prop="firstName"
              >
                <a-input v-model="editForm.firstName" />
              </a-form-model-item>

              <a-form-model-item label="临时人员" prop="temporaryStatus">
                <a-select
                  style="width: 100%"
                  v-model="editForm.temporaryStatus"
                  :allowClear="true"
                >
                  <a-select-option value="0"> 否</a-select-option>
                  <a-select-option value="1"> 是</a-select-option>
                </a-select>
              </a-form-model-item>

              <a-form-model-item
                label="证件号码"
                prop="idCard"
                v-if="editForm.temporaryStatus === '1'"
              >
                <a-input v-model="editForm.idCard" />
              </a-form-model-item>
              <a-form-model-item label="证件号码" v-else>
                <a-input v-model="editForm.idCard" />
              </a-form-model-item>
            </a-col>
            <a-col :span="6">
              <a-upload
                name="file"
                list-type="picture-card"
                class="avatar-uploader"
                :show-upload-list="false"
                :data="uploadObj"
                action="/api/employee/uploadAvatar"
                :before-upload="beforeUpload"
                @change="handleChange(arguments[0], 'edit')"
              >
                <div class="avatar-cla" v-if="this.editForm.avatar">
                  <img :src="this.editForm.avatar" alt="avatar" />
                  <div class="change-avatar-cla">
                    {{ $t('employee.changeAvatar') }}
                  </div>
                </div>
                <div v-else>
                  <a-icon :type="loading ? 'loading' : 'plus'" />
                  <div class="ant-upload-text">{{ $t('employee.upload') }}</div>
                </div>
              </a-upload>
            </a-col>
          </a-row>

          <a-divider type="vertical" />
          <div class="verify-info-cla">
            {{ $t('employee.verifyInfo') }}
          </div>
          <a-row>
            <a-col :span="18">
              <a-form-model-item
                ref="cardNo"
                :label="$t('employee.cardNo')"
                prop="cardNo"
              >
                <a-input v-model="editForm.cardNo" />
              </a-form-model-item>

              <a-form-model-item
                ref="devicePassword"
                :label="$t('employee.devicePassword')"
                prop="devicePassword"
              >
                <a-input v-model="editForm.devicePassword" type="password" />
              </a-form-model-item>

              <a-form-model-item :label="$t('employee.devicePermision')">
                <a-select v-model="editForm.devicePermission">
                  <a-select-option
                    v-for="item in devicePermission"
                    :key="item.key"
                    :value="item.key"
                    >{{ item.label }}</a-select-option
                  >
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="允许进入" prop="allowStatus">
                <a-select style="width: 100%" v-model="editForm.allowStatus">
                  <a-select-option value="0">禁止</a-select-option>
                  <a-select-option value="1">允许</a-select-option>
                </a-select>
              </a-form-model-item>

              <a-form-model-item label="有效时间">
                <a-range-picker
                  @change="editPickOnChange"
                  :placeholder="[$t('common.startTime'), $t('common.endTime')]"
                  :allowClear="true"
                  v-model="editDate"
                  renderExtraFooter
                  show-time
                  format="YYYY-MM-DD HH:mm:ss"
                />
              </a-form-model-item>
            </a-col>
          </a-row>

          <a-row>
            <div class="template-cla">
              <div class="template-item">
                <label> {{ $t('employee.fingerTemplate') }}:</label>
                <i
                  :class="[
                    editForm.fingerCount > 0 ? 'active-icon-cla' : '',
                    'iconfont zk-icon-ic_zw- icon-hover'
                  ]"
                >
                  <a-icon
                    v-if="editForm.fingerCount"
                    @click="removeTemplate('1')"
                    class="delete-icon"
                    type="minus-circle"
                  />
                </i>
              </div>
              <div class="template-item">
                <label> {{ $t('employee.visiableTemplate') }}:</label>
                <i
                  :class="[
                    editForm.visibleFaceCount > 0 ? 'active-icon-cla' : '',
                    'iconfont zk-icon-renlianshibie- icon-hover'
                  ]"
                >
                  <a-icon
                    v-if="editForm.visibleFaceCount"
                    @click="removeTemplate('2')"
                    class="delete-icon"
                    type="minus-circle"
                  />
                </i>
              </div>
              <div class="template-item">
                <label> {{ $t('employee.faceTemplate') }}:</label>
                <i
                  :class="[
                    editForm.faceCount > 0 ? 'active-icon-cla' : '',
                    'iconfont zk-icon-ic_rl icon-hover'
                  ]"
                >
                  <a-icon
                    v-if="editForm.faceCount"
                    @click="removeTemplate('3')"
                    class="delete-icon"
                    type="minus-circle"
                  />
                </i>
              </div>
              <div class="template-item">
                <label> {{ $t('employee.palmPrintTemplate') }}:</label>
                <i
                  :class="[
                    editForm.palmPrintCount > 0 ? 'active-icon-cla' : '',
                    'iconfont zk-icon-ic_zw icon-hover'
                  ]"
                >
                  <a-icon
                    v-if="editForm.palmPrintCount"
                    @click="removeTemplate('4')"
                    class="delete-icon"
                    type="minus-circle"
                  />
                </i>
              </div>
            </div>
          </a-row>
          <a-divider />
        </a-form-model>
        <div style="text-align:right">
          <a-button @click="visible = false">{{
            $t('common.cancelText')
          }}</a-button>
          <a-button style="margin:0 10px" @click="submitEdit" type="primary">
            {{ $t('common.okText') }}
          </a-button>
        </div>
      </a-drawer>

      <a-drawer
        :title="$t('employee.addEmployee')"
        placement="right"
        :closable="false"
        :visible="addVisible"
        :width="500"
        @close="closeDrawer"
      >
        <div style="overflow-y: auto;min-height:100%">
          <a-form-model
            ref="addEmployeeFrom"
            layout="horizontal"
            :model="addFrom"
            :rules="rules"
            :label-col="{ span: 8 }"
            :wrapper-col="{ span: 14 }"
            labelAlign="left"
          >
            <a-form-model-item
              :label="$t('employee.employeeNo')"
              prop="employeeNo"
            >
              <a-input v-model="addFrom.employeeNo" />
            </a-form-model-item>

            <a-form-model-item :label="$t('employee.lastName')" prop="lastName">
              <a-input v-model="addFrom.lastName" />
            </a-form-model-item>

            <a-form-model-item
              :label="$t('employee.firstName')"
              prop="firstName"
            >
              <a-input v-model="addFrom.firstName" />
            </a-form-model-item>
            <a-form-model-item label="临时人员" prop="temporaryStatus">
              <a-select
                style="width: 100%"
                v-model="addFrom.temporaryStatus"
                :allowClear="true"
              >
                <a-select-option value="0"> 否</a-select-option>
                <a-select-option value="1"> 是</a-select-option>
              </a-select>
            </a-form-model-item>
            <a-form-model-item
              label="证件号码"
              prop="idCard"
              v-if="addFrom.temporaryStatus === '1'"
            >
              <a-input v-model="addFrom.idCard" />
            </a-form-model-item>
            <a-form-model-item label="证件号码" v-else>
              <a-input v-model="addFrom.idCard" />
            </a-form-model-item>
            <a-form-model-item
              ref="cardNo"
              :label="$t('employee.cardNo')"
              prop="cardNo"
            >
              <a-input v-model="addFrom.cardNo" />
            </a-form-model-item>

            <a-form-model-item
              ref="devicePassword"
              :label="$t('employee.devicePassword')"
              prop="devicePassword"
            >
              <a-input v-model="addFrom.devicePassword" type="password" />
            </a-form-model-item>
            <a-form-model-item :label="$t('employee.devicePermision')">
              <a-select v-model="addFrom.devicePermission">
                <a-select-option
                  v-for="item in devicePermission"
                  :key="item.key"
                  :value="item.key"
                  >{{ item.label }}</a-select-option
                >
              </a-select>
            </a-form-model-item>
            <a-form-model-item label="允许进入" prop="allowStatus">
              <a-select style="width: 100%" v-model="addFrom.allowStatus">
                <a-select-option value="0">禁止</a-select-option>
                <a-select-option value="1">允许</a-select-option>
              </a-select>
            </a-form-model-item>

            <a-form-model-item label="有效时间">
              <a-range-picker
                @change="pickOnChange"
                :placeholder="[$t('common.startTime'), $t('common.endTime')]"
                :allowClear="true"
                v-model="date"
                dropdownClassName="owner-dropdown-cla"
                renderExtraFooter
                format="YYYY-MM-DD HH:mm:ss"
              />
            </a-form-model-item>
            <a-form-model-item
              ref="avatar"
              :label="$t('employee.avatar')"
              prop="avatar"
            >
              <a-upload
                name="file"
                list-type="picture-card"
                class="avatar-uploader"
                :show-upload-list="false"
                :data="uploadObj"
                action="/api/employee/uploadAvatar"
                :before-upload="beforeUpload"
                @change="handleChange(arguments[0], 'add')"
              >
                <div class="avatar-cla" v-if="this.addFrom.avatar">
                  <img :src="addFrom.avatar" alt="avatar" />
                  <div class="change-avatar-cla">
                    {{ $t('employee.changeAvatar') }}
                  </div>
                </div>
                <div v-else>
                  <a-icon :type="loading ? 'loading' : 'plus'" />
                  <div class="ant-upload-text">{{ $t('employee.upload') }}</div>
                </div>
              </a-upload>
            </a-form-model-item>
          </a-form-model>
        </div>
        <div class="" style="text-align:right">
          <a-button @click="addVisible = false">{{
            $t('common.cancelText')
          }}</a-button>
          <a-button style="margin:0 10px" @click="submitAdd" type="primary">
            {{ $t('common.okText') }}
          </a-button>
        </div>
      </a-drawer>

      <a-drawer
        placement="right"
        :closable="false"
        :visible="remoteRegVisible"
        :width="600"
      >
        <div class="step-top-cla">
          <div
            :class="{
              'step-active': steps === 0,
              'step-item-hover': steps === 1
            }"
            class="step-item"
            @click="goBackSteps"
          >
            {{ $t('employee.registerType') }}
          </div>
          <div :class="{ 'step-active': steps === 1 }" class="step-item">
            {{ $t('employee.selectDevice') }}
          </div>
          <div :class="{ 'step-active': steps === 2 }" class="step-item">
            {{ $t('employee.entryInfo') }}
          </div>
        </div>
        <div class="step-bottom-cla">
          <div class="reg-type" :style="steps === 0 ? '' : 'display:none'">
            <div
              class="reg-type-item"
              :class="{ 'checked-type': biologicForm.type === 1 }"
              @click="selectType(1)"
            >
              <i class="icon iconfont zk-icon-ic_zw-"></i>
              <p>{{ $t('employee.fingerRegister') }}</p>
            </div>
            <div
              class="reg-type-item"
              :class="{ 'checked-type': biologicForm.type === 9 }"
              @click="selectType(9)"
            >
              <i class="icon iconfont zk-icon-renlianshibie-"></i>
              <p>{{ $t('employee.faceRegister') }}</p>
            </div>
            <div
              class="reg-type-item"
              :class="{ 'checked-type': biologicForm.type === 8 }"
              @click="selectType(8)"
            >
              <i class="icon iconfont zk-icon-ic_zw"></i>
              <p>{{ $t('employee.regPlam') }}</p>
            </div>
          </div>
          <div
            class="device-list-cla"
            :style="steps === 1 ? 'display:block' : 'display:none'"
            style="margin-top:-45px"
          >
            <a-table
              :data-source="deviceList"
              :pagination="false"
              :customRow="deviceListClick"
              tableLayout="fixed"
            >
              <a-table-column
                :title="$t('door.deviceSn')"
                key="sn"
                data-index="sn"
              ></a-table-column>
              <a-table-column
                :title="$t('door.deviceAlias')"
                key="alias"
                data-index="alias"
              ></a-table-column>
              <a-table-column
                :title="$t('device.deviceType')"
                key="type"
                data-index="type"
              >
                <template slot-scope="type">
                  <!-- {{
                    type === 0 ? $t('employee.accWord') : $t('employee.attWord')
                  }} -->
                  <span v-if="type === 0">{{ $t('employee.accWord') }}</span>
                  <span v-if="type === 1">{{ $t('employee.attWord') }}</span>
                  <span v-if="type === 2">人证设备</span>
                </template>
              </a-table-column>
            </a-table>
          </div>
          <div class="entry-cla" :style="steps === 2 ? '' : 'display:none'">
            <template v-if="regBioRes === 0">
              <a-icon type="loading" class="entry-loading" />
              <p>
                <!-- 请在设备中录入{{
                  biologicForm.type === 1 ? '指纹信息' : '人脸信息'
                }} -->
                <span v-if="biologicForm.type === 1">{{
                  $t('employee.entryFingerTips')
                }}</span>
                <span v-if="biologicForm.type === 9">{{
                  $t('employee.entryFaceTips')
                }}</span>
                <span v-if="biologicForm.type === 8">{{
                  $t('employee.entryPalmTips')
                }}</span>
              </p>
              <a-button type="danger" @click="clearRegBio">{{
                $t('employee.cancelReg')
              }}</a-button>
            </template>
            <template v-else-if="regBioRes === 1">
              <div class="result-template">
                <div style=" display: flex;align-items: center;">
                  <a-icon type="check-circle" class="entry-loading" />
                  <span style="font-size:22px;margin-left:10px">{{
                    $t('employee.entrySuccess')
                  }}</span>
                </div>
                <div>
                  <a-button
                    style="margin-top:20px;width:120px"
                    type="primary"
                    @click="cancelRemoteReg"
                    >{{ $t('common.okText') }}</a-button
                  >
                </div>
              </div>
            </template>
            <template v-else-if="regBioRes === 2">
              <div class="result-template">
                <div style=" display: flex;align-items: center;">
                  <a-icon
                    type="close-circle"
                    class="entry-loading entry--fail"
                  />
                  <span style="font-size:22px;margin-left:10px">{{
                    $t('employee.entryFail')
                  }}</span>
                </div>
                <div>
                  <a-button
                    style="margin-top:20px;width:120px"
                    type="primary"
                    @click="cancelRemoteReg"
                    >{{ $t('common.okText') }}</a-button
                  >
                </div>
              </div>
            </template>
          </div>
        </div>
        <div
          class="step-btn-cla foot-bottom-cla"
          v-if="steps === 0 || steps === 1"
        >
          <a-button @click="cancelRemoteReg">{{
            $t('common.cancelText')
          }}</a-button>
        </div>
      </a-drawer>
    </div>
  </topDownLayout>
</template>

<script>
import Vue from 'vue'
import { wsUrl } from '../../../package.json'
export default {
  data() {
    let handlePass = (rule, value, callback) => {
      if (value === null || value === undefined || value.length === 0) {
        return callback()
      }
      if (value.length !== 6) {
        return callback(new Error(this.$t('door.reqPwdTips')))
      } else {
        return callback()
      }
    }
    return {
      isOnCheck: false,
      loading: false,
      date: [],
      editDate: [],
      addImageUrl: '',
      imageUrl: '',
      uploadObj: {
        lang: ''
      },
      visible: false,
      addVisible: false,
      tableData: [],
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
          title: this.$t('employee.employeeNo'),
          dataIndex: 'employeeNo'
        },
        {
          title: this.$t('employee.formattedName'),
          dataIndex: 'formattedName'
        },
        {
          title: this.$t('employee.cardNo'),
          key: 'cardNo',
          scopedSlots: { customRender: 'cardNo' }
        },
        {
          title: this.$t('employee.devicePermission'),
          key: 'devicePermission',
          scopedSlots: { customRender: 'devicePermission' }
        },
        {
          title: this.$t('employee.template'),
          key: 'template',
          scopedSlots: { customRender: 'template' },
          width: 200
        },
        {
          title: this.$t('common.action'),
          key: 'action',
          scopedSlots: { customRender: 'action' },
          width: 200
        }
      ],
      editForm: {},
      addFrom: {
        employeeNo: '',
        firstName: '',
        lastName: '',
        cardNo: '',
        devicePassword: '',
        avatar: '',
        devicePermission: 0,
        idCard: '',
        temporaryStatus: '',
        allowStatus: '',

        startTime: '',
        endTIme: ''
      },
      rules: {
        idCard: [
          {
            required: true,
            message: this.$t('common.noEmpty'),
            trigger: 'blur'
          }
        ],
        devicePassword: [
          { validator: handlePass, trigger: ['blur', 'change'] }
        ],
        employeeNo: [
          {
            required: true,
            message: this.$t('common.noEmpty'),
            trigger: 'blur'
          }
        ],
        firstName: [
          {
            required: true,
            message: this.$t('common.noEmpty'),
            trigger: 'blur'
          },
          {
            max: 32,
            message: '超过最大长度32位',
            trigger: ['blur', 'change']
          }
        ],
        lastName: [
          {
            required: true,
            message: this.$t('common.noEmpty'),
            trigger: 'blur'
          },
          {
            max: 32,
            message: '超过最大长度32位',
            trigger: ['blur', 'change']
          }
        ]
      },
      devicePermission: [
        {
          label: this.$t('employee.ordinaryUser'),
          key: 0
        },
        {
          label: this.$t('employee.adminUser'),
          key: 14
        }
      ],
      remoteRegVisible: false,
      deviceList: [],
      steps: 0,
      biologicForm: {
        type: '',
        employeeNo: '',
        sn: ''
      },
      regBioRes: 0, // 0  初始状态  1  录入成功  2 录入失败
      socket: null,
      timer: null
    }
  },
  mounted() {
    this.getEmployeeList()
  },
  watch: {
    'addFrom.temporaryStatus': function(newValue) {
      if ('1' === newValue) {
        this.$set(this.rules, 'idCard', {
          required: true,
          message: this.$t('common.noEmpty'),
          trigger: 'blur'
        })
      } else {
        Vue.delete(this.rules, 'idCard')
      }
    }
  },
  methods: {
    pickOnChange(date, dateString) {
      let startTime = new Date(dateString[0]).toISOString().split('.')
      let endTime = new Date(dateString[1]).toISOString().split('.')
      this.addFrom.startTime = `${startTime[0]}`
      this.addFrom.endTime = `${endTime[0]}`
    },
    editPickOnChange(date, dateString) {
      let startTime = dateString[0].split(' ')
      let endTime = dateString[1].split(' ')
      this.editForm.startTime = `${startTime[0]}T${startTime[1]}`
      this.editForm.endTime = `${endTime[0]}T${endTime[1]}`
    },
    onSearch() {
      this.pager.curPage = 1
      this.getEmployeeList()
    },
    closeDrawer() {
      this.visible = false
      this.addVisible = false
      if (this.$refs.addEmployeeFrom) {
        this.$refs.addEmployeeFrom.resetFields()
      }
      if (this.$refs.editEmployeeFrom) {
        this.$refs.editEmployeeFrom.resetFields()
      }
    },
    cancelRemoteReg() {
      this.remoteRegVisible = false
      this.steps = 0
      this.biologicForm.type = ''
      this.regBioRes = 0
      clearTimeout(this.timer)
      if (this.socket) {
        this.socket.close()
        // console.log('cancelRem关闭webscoket')
      }
      this.getEmployeeList()
    },
    clearRegBio() {
      let params = {
        sn: this.biologicForm.sn
      }
      this.request('cancelRegBio', params)
        .then(() => {
          this.cancelRemoteReg()
        })
        .catch(e => {
          this.errorMessage(e)
        })
    },
    initWebScoket(path) {
      let url = `${wsUrl}/ws/${path}`
      if (typeof WebSocket === 'undefined') {
        // console.log('您的浏览器不支持socket')
      } else {
        // 实例化socket
        this.socket = new WebSocket(url)
        // 监听socket连接
        this.socket.onopen = this.open
        // 监听socket错误信息
        this.socket.onerror = this.error
        // 监听socket消息
        this.socket.onmessage = this.getMessage
      }
    },
    open: function() {
      console.log('socket连接成功')
    },
    error: function() {
      console.log('连接错误')
      this.socket.close()
    },
    getMessage: function(msg) {
      let res = JSON.parse(msg.data)
      if (res.code === '0') {
        // 成功
        this.regBioRes = 1
        this.close()
      } else {
        // 失败
        this.regBioRes = 2
        this.close()
      }
    },
    close: function() {
      this.socket.close()
      // console.log('监听socket已经关闭')
    },
    deviceListClick(record) {
      return {
        on: {
          click: () => {
            this.biologicForm.sn = record.sn
            this.steps = 2
            this.regBioTemplate()
          }
        }
      }
    },
    regBioTemplate() {
      this.request('regBioTemplate', this.biologicForm)
        .then(data => {
          if (data.code === '00') {
            this.initWebScoket(data.data.sid)
            // 2分后 超时关闭   2分内
            this.timer = setTimeout(() => {
              if (this.regBioRes === 1) {
                return false
              }
              this.socket.close()
              this.regBioRes = 2
            }, 2 * 60 * 1000)
          } else {
            this.regBioRes = 2
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    },
    goBackSteps() {
      if (this.steps !== 1) return
      this.steps = 0
    },
    selectType(val) {
      this.biologicForm.type = val
      this.steps = 1
      let params = {
        employeeNo: this.biologicForm.employeeNo,
        biometricType: val
      }
      this.request('deviceByEmpList', params)
        .then(data => {
          if (data.code === '00') {
            this.deviceList = data.data
          } else {
            this.errorMessage(data.message)
          }
        })
        .catch(e => this.errorMessage(e))
    },
    remoteReg(record) {
      this.biologicForm.employeeNo = record.employeeNo
      this.remoteRegVisible = true
      this.steps = 0
    },
    details(row) {
      this.editForm = Object.assign({}, row)
      try {
        if (this.editForm.startTime && this.editForm.endTime) {
          let startTime = this.editForm.startTime.split('+')
          this.editForm.startTime = startTime[0]
          let endTime = this.editForm.endTime.split('+')
          this.editForm.endTime = endTime[0]
          this.editDate = [startTime[0], endTime[0]]
        } else {
          this.editDate = []
        }

        this.visible = true
      } catch (error) {
        console.log(error)
      }
    },
    removeTemplate(val) {
      val === '1' && (this.editForm.fingerCount = -1)
      val === '2' && (this.editForm.visibleFaceCount = -1)
      val === '3' && (this.editForm.faceCount = -1)
      val === '4' && (this.editForm.palmPrintCount = -1)
    },
    submitEdit() {
      this.$refs.editEmployeeFrom.validate(valid => {
        if (valid) {
          if (this.editForm.temporaryStatus === '0') {
            this.editForm.idCard = ''
          }
          this.request('updateEmployee', this.editForm)
            .then(data => {
              if (data.code === '00') {
                this.visible = false
                this.successMessage()
                this.getEmployeeList()
              } else {
                this.errorMessage(data.message)
              }
            })
            .catch(e => {
              this.errorMessage(e)
            })
        }
      })
    },

    showAdd() {
      this.onCheck = false
      this.date = []
      this.addFrom = {
        employeeNo: '',
        firstName: '',
        lastName: '',
        cardNo: '',
        devicePassword: '',
        avatar: '',
        devicePermission: 0,
        idCard: '',
        temporaryStatus: '',
        allowStatus: '',

        startTime: '',
        endTime: ''
      }
      this.addVisible = true
    },

    submitAdd() {
      this.$refs.addEmployeeFrom.validate(valid => {
        if (valid) {
          this.request('addEmployee', this.addFrom)
            .then(data => {
              if (data.code === '00') {
                this.addVisible = false
                this.successMessage()
                this.getEmployeeList()
              } else {
                this.errorMessage(data.message)
              }
            })
            .catch(e => {
              this.errorMessage(e)
            })
        }
      })
    },

    getEmployeeList() {
      this.$store.dispatch('showloadding', true)
      let params = {
        user: name
      }
      this.optionList.filter(item => {
        if (item.key === this.searchForm.type) {
          return (params[item.attr] = this.searchForm.value)
        }
      })
      this.request('getEmployeeList', params, this.pager)
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
        })
        .finally(() => {
          this.$store.dispatch('showloadding', false)
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
            .request('deleteEmployee', {
              id: record.id
            })
            .then(data => {
              if (data.code === '00') {
                _that.successMessage()
                _that.getEmployeeList()
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

    handleChange(info, oprateName) {
      if (info.file.status === 'uploading') {
        this.loading = true
        return
      }
      if (info.file.status === 'done') {
        this.loading = false
        let rep = info.file.response
        if (rep.code === '00') {
          if (oprateName === 'edit') {
            this.editForm.avatar = rep.data
          } else {
            this.addFrom.avatar = rep.data
          }
        } else {
          this.errorMessage(rep)
        }
      }
    },

    beforeUpload(file) {
      this.uploadObj.lang =
        localStorage.getItem('locale') === 'en_US'
          ? 'en'
          : localStorage.getItem('locale')
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error(this.$t('employee.isJpgOrPng'))
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error(this.$t('employee.isLt2M'))
      }
      return isJpgOrPng && isLt2M
    }
  }
}
</script>

<style scoped lang="less">
/deep/ .ant-calendar-picker {
  width: 100% !important;
}
.employee-top-bar {
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px;
}
.template-cla {
  display: flex;
  .template-item {
    position: relative;
    padding-left: 28px;
    display: flex;
    align-items: center;
  }
  i {
    font-size: 32px;
    margin-left: 10px;
  }

  .delete-icon {
    position: absolute;
    right: -20px;
    top: 0;
    font-size: 14px;
    display: none;
  }
}
.icon-hover:hover .delete-icon {
  display: block;
}

.avatar-cla {
  width: 102px;
  height: 102px;
  position: relative;
  &:hover {
    .change-avatar-cla {
      display: block;
    }
  }
  img {
    width: 100%;
    height: 100%;
  }
  .change-avatar-cla {
    width: 100%;
    height: 100%;
    position: absolute;
    display: none;
    top: 0px;
    line-height: 102px;
    background-color: rgba(255, 255, 255, 0.7);
  }
}
.table-cla {
  i {
    font-size: 25px;
    margin-left: 12px;
    cursor: pointer;
  }
}

.foot-bottom-cla {
  // padding-right: 18%;
  position: absolute;
  right: 20px;
  bottom: 15px;
}
.verify-info-cla {
  height: 20;
  line-height: 20px;
  border-left: 5px solid @error-color;
  padding-left: 10px;
}
.base-info-cla {
  height: 20;
  line-height: 20px;
  border-left: 5px solid @primary-color;
  padding-left: 10px;
}
.template-cla {
  .active-icon-cla {
    color: @primary-color;
  }
}
.step-top-cla {
  padding: 10px;
  border-bottom: 1px solid @border-color-base;
  display: flex;
  justify-content: center;
  .step-item {
    width: 150px;
  }
  .step-active {
    color: @primary-color;
  }
  .step-item-hover {
    cursor: pointer;
  }
}
.step-bottom-cla {
  margin-top: 50px;
  .checked-type {
    color: @primary-color;
  }
  .reg-type {
    display: flex;
    justify-content: center;
    .reg-type-item {
      text-align: center;
      cursor: pointer;
      &:hover {
        color: @primary-color;
      }
      i {
        font-size: 32px;
        margin: 20px 30px;
      }
    }
  }
  .entry-cla {
    text-align: center;
    .entry-loading {
      font-size: 40px;
      color: @primary-color;
    }
    .entry--fail {
      color: @error-color;
    }
  }
}
.step-btn-cla {
  position: absolute;
  bottom: 15px;
  right: 15px;
}
.result-template {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
</style>
