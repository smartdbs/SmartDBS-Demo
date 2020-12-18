import Layout from '@/layout'
const sysManager = {
  path: '/sys',
  component: Layout,
  name: 'sys',
  redirect: '/sys/info',
  meta: {
    title: 'sysManager',
    icon: 'zk-icon-erji-xitongxinxi'
  },
  children: [
    {
      path: 'info',
      component: () =>
        import(/* webpackChunkName: "sys" */ '@/modules/system/sysInfo.vue'),
      name: 'sysInfo',
      meta: {
        title: 'sysInfo',
        hidden: true
      }
    }
  ]
}

export default sysManager
