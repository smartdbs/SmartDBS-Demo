/**
 * 注册全局组件
 */
import Vue from 'vue'
import topDownLayout from '@/components/topDownLayout.vue'
import changeLanguage from '@/components/changeLanguage.vue'
import RangeTimePicker from '@/components/RangeTimePicker.vue'
import zkPopper from '@/components/zkPopper.vue'

import {
  Button,
  Menu,
  ConfigProvider,
  Spin,
  Breadcrumb,
  Icon,
  Layout,
  Upload,
  Tabs,
  Table,
  Popover,
  FormModel,
  Input,
  Drawer,
  Dropdown,
  Pagination,
  Divider,
  Modal,
  notification,
  message,
  Select,
  DatePicker,
  TimePicker,
  Avatar,
  Row,
  Col,
  Checkbox,
  Switch,
  Tooltip,
  Cascader,
  steps,
  Popconfirm
} from 'ant-design-vue'

//自定义字体图标库
// eslint-disable-next-line
const IconFont = Icon.createFromIconfontCN({
  scriptUrl: require('@/styles/icon/iconfont.js')
})
Vue.component('IconFont', IconFont)

let components = [
  Button,
  Menu,
  ConfigProvider,
  Spin,
  Breadcrumb,
  Icon,
  Layout,
  Upload,
  Tabs,
  Table,
  Popover,
  FormModel,
  Input,
  Drawer,
  Dropdown,
  Pagination,
  Divider,
  Modal,
  Select,
  DatePicker,
  TimePicker,
  Avatar,
  Row,
  Col,
  Checkbox,
  Switch,
  Tooltip,
  Cascader,
  steps,
  Popconfirm
]
/**
 * 注册ant 组件
 */
components.map(component => {
  Vue.use(component)
})

Vue.prototype.$message = message
Vue.prototype.$notification = notification
Vue.prototype.$info = Modal.info
Vue.prototype.$success = Modal.success
Vue.prototype.$error = Modal.error
Vue.prototype.$warning = Modal.warning
Vue.prototype.$confirm = Modal.confirm
Vue.prototype.$destroyAll = Modal.destroyAll

/**
 * 注册自定义组件
 */
Vue.component('topDownLayout', topDownLayout)
Vue.component('changeLanguage', changeLanguage)
Vue.component('RangeTimePicker', RangeTimePicker)
Vue.component('zkPopper', zkPopper)
