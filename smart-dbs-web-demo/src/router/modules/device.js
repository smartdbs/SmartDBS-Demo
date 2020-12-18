import Layout from '@/layout'
const loadView = view => {
  return () =>
    import(/* webpackChunkName: "device" */ `@/modules/device/${view}.vue`)
}
const deviceManager = {
  path: '/device',
  component: Layout,
  name: 'device',
  redirect: '/device/deviceList',
  meta: {
    title: 'deviceManager',
    icon: 'zk-icon-shebeiguanli'
  },
  children: [
    {
      path: 'deviceList',
      component: loadView('device'),
      name: 'deviceList',
      meta: {
        title: 'deviceList',
        hidden: true
      }
    }
  ]
}

export default deviceManager
