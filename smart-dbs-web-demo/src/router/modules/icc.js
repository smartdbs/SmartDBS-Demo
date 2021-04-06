import Layout from '@/layout'
const loadView = view => {
  return () =>
    import(/* webpackChunkName: "acc" */ `@/modules/iccManager/${view}.vue`)
}
const iccManager = {
  path: '/icc',
  component: Layout,
  name: 'icc',
  redirect: '/icc/iccSetting',
  meta: {
    title: 'iccManager',
    icon: 'zk-icon-renzheng'
  },
  children: [
    {
      path: 'iccSetting',
      component: loadView('iccSetting'),
      name: 'iccSetting',
      meta: {
        title: 'iccSetting'
      }
    },
    {
      path: 'iccDataRecord',
      component: loadView('iccDataRecord'),
      name: 'iccDataRecord',
      meta: {
        title: 'iccDataRecord'
      }
    }
  ]
}
export default iccManager
