/**
 * 定义全局的混入插件
 * @param {*} Vue
 */
let install = Vue => {
  if (install.installed) return
  Vue.mixin({
    data() {
      return {
        pager: {
          total: 0, //总记录数
          curPage: 1, //当前也
          pageSize: 10 // 每页大小
        }
      }
    },
    methods: {
      //全局错误提示
      errorMessage(message) {
        if (typeof message === 'object' && message !== null) {
          message = message.message
        }
        if (!message) {
          message = '失败'
        }
        this.$message.error(message)
      },
      //全局的成功提示
      successMessage(message) {
        if (typeof message === 'object' && message !== null) {
          message = message.message
        }
        if (!message) {
          message = '成功'
        }
        this.$message.success(message)
      },
      pageChange(page, pageSize, callback) {
        this.pager.curPage = page
        this.pager.pageSize = pageSize
        if (callback && typeof callback === 'function') {
          callback()
        }
      },
      setPager(data) {
        if (data && data.total) {
          this.pager.total = data.total
          this.pager.curPage = data.curPage
          this.pager.pageSize = data.pageSize
        } else if (data.total === 0) {
          this.pager.total = data.total
          this.pager.curPage = data.curPage
          this.pager.pageSize = data.pageSize
        }
      },
      isEmpty(val) {
        if (val === null || val === undefined || val === '') {
          return 'N/A'
        }
        return val
      }
    }
  })
}
export default install
