/* eslint-disable */
import Layout from '@/layout'
const loadView = view => {
  return () =>
    import(/* webpackChunkName: "att" */ `@/modules/attManager/${view}.vue`)
}
const attManager = {
  path: '/att',
  component: Layout,
  name: 'att',
  redirect: '/att/attDevice',
  meta: {
    title: 'attManager',
    icon: 'zk-icon-kaoqinguanli'
  },
  children: [
    {
      path: 'attDevice',
      component: loadView('attDevice'),
      name: 'attDevice',
      meta: {
        title: 'attDevice'
      }
    },
    {
      path: 'attData',
      component: loadView('attData'),
      name: 'attData',
      meta: {
        title: 'attData'
      }
    }
  ]
}
export default attManager
