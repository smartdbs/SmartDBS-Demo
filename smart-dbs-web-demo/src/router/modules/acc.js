import Layout from '@/layout'
const loadView = view => {
  return () =>
    import(/* webpackChunkName: "acc" */ `@/modules/accManager/${view}.vue`)
}
const accManager = {
  path: '/acc',
  component: Layout,
  name: 'acc',
  redirect: '/acc/doorList',
  meta: {
    title: 'accManager',
    icon: 'zk-icon-menjinguanli'
  },
  children: [
    {
      path: 'doorList',
      component: loadView('doorList'),
      name: 'doorList',
      meta: {
        title: 'doorList'
      }
    },
    {
      path: 'doorSeeting',
      component: loadView('doorSeeting'),
      name: 'doorSeeting',
      meta: {
        title: 'doorSetting'
      }
    },
    {
      path: 'accData',
      component: loadView('accData'),
      name: 'accData',
      meta: {
        title: 'accData'
      }
    }
  ]
}
export default accManager
