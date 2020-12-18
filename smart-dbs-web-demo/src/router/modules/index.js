import Layout from '@/layout'
const indexPage = {
  path: '/',
  component: Layout,
  name: 'device',
  redirect: '/indexPage',
  meta: {
    title: 'index',
    icon: 'zk-icon-shouyeshouye'
  },
  children: [
    {
      path: 'indexPage',
      component: () =>
        import(/* webpackChunkName: "base" */ '@/modules/index/index.vue'),
      name: 'indexPage',
      meta: {
        title: 'index',
        hidden: true
      }
    }
  ]
}

export default indexPage
