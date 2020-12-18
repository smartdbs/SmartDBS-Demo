import Layout from '@/layout'
const loadView = view => {
  return () =>
    import(
      /* webpackChunkName: "employee" */ `@/modules/enterprise/${view}.vue`
    )
}
const enterpriseManager = {
  path: '/enterprise',
  component: Layout,
  name: 'enterprise',
  meta: {
    title: 'enterpriseManager',
    icon: 'zk-icon-company'
  },
  children: [
    {
      path: 'employee',
      component: loadView('employee'),
      name: 'employee',
      meta: {
        title: 'employee'
      }
    },
    {
      path: 'enterpriseInfo',
      component: loadView('enterpriseInfo'),
      name: 'enterpriseInfo',
      meta: {
        title: 'enterpriseInfo'
      }
    }
  ]
}

export default enterpriseManager
