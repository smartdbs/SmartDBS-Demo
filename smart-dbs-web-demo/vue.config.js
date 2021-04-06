let path = require('path')
const webpack = require('webpack')
const Happypack = require('happypack')
const { IgnorePlugin } = require('webpack')
const packageConfig = require('./package.json')
console.log('后端地址-->', packageConfig.serviceUrl)
function resolve(dir) {
  // path.join()方法用于连接路径
  return path.join(__dirname, dir)
}

const getCurrentTime = () => {
  let date = new Date()
  let hours = date.getHours() < 10 ? +'0' + date.getHours() : date.getHours()
  let minutes =
    date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
  return (
    date.getFullYear() +
    '-' +
    (date.getMonth() + 1) +
    '-' +
    date.getDate() +
    ' ' +
    hours +
    ':' +
    minutes +
    ':' +
    date.getSeconds()
  )
}

const cdn = {
  // css: ['/lib/antd.min.css'],
  js: [
    '/lib/vue.runtime.min.js',
    '/lib/vue.min.js',
    '/lib/vue-router.min.js',
    '/lib/axios.min.js',
    '/lib/vuex.min.js',
    // '/lib/antd.min.js',
    '/lib/vue-i18n.min.js'
  ]
}

module.exports = {
  productionSourceMap: false,
  chainWebpack: config => {
    config.plugin('html').tap(args => {
      args[0].cdn = cdn
      args[0].title = 'dbs-demo'
      return args
    })
    // 移除 prefetch 插件
    config.plugins.delete('prefetch')
    // 移除 preload 插件
    config.plugins.delete('preload')
    // 压缩代码
    // config.optimization.minimize(true)
    // 分割代码
    // config.optimization.splitChunks({
    //   chunks: 'all'
    // })
  },
  configureWebpack: {
    devtool: 'source-map',
    output: {
      // 输出重构  打包编译后的 文件名称  【模块名称.版本号.js】
      filename: `js/[name]-[hash].js`,
      chunkFilename: `js/[name]-[hash].js`
    },
    externals: {
      vue: 'Vue',
      // 'vue-antd-ui': 'antd',
      'vue-router': 'VueRouter',
      vuex: 'Vuex',
      axios: 'axios',
      'vue-i18n': 'VueI18n'
    },
    plugins: [
      new IgnorePlugin(/^\.\/locale$/, /moment$/),
      new Happypack({
        loaders: ['babel-loader', 'vue-loader', 'url-loader'],
        cache: true,
        threads: 6
      }),
      new webpack.BannerPlugin(
        'project name: ' +
          packageConfig.name +
          ' \ncreateTime ' +
          getCurrentTime() +
          '\nversion: ' +
          packageConfig.version
      )
    ],
    resolve: {
      alias: {
        '@ant-design/icons/lib/dist$': resolve('./src/antd/icons.js')
      }
    }
  },

  devServer: {
    open: process.platform === 'darwin',
    host: '0.0.0.0',
    port: 8080,
    disableHostCheck: true,
    https: false,
    hotOnly: false,
    proxy: {
      '/api': {
        target: packageConfig.serviceUrl, //
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': '/'
        }
      },
      '/temp': {
        target: packageConfig.serviceUrl, //packageConfig.serviceUrl
        changeOrigin: true,
        ws: true,
        pathRewrite: {}
      }
    }
    // before: app => {}
  },
  pwa: {
    iconPaths: {
      favicon32: 'favicon.ico',
      favicon16: 'favicon.ico',
      appleTouchIcon: 'favicon.ico',
      maskIcon: 'favicon.ico',
      msTileImage: 'favicon.ico'
    }
  },
  css: {
    loaderOptions: {
      less: {
        lessOptions: {
          modifyVars: {
            //自定义全局主题样式
            'primary-color': '#4db22f', // 全局主色
            'link-color': '#1890ff', // 链接色
            'success-color': '#01b538', // 成功色
            'warning-color': '#ff9a00', // 警告色
            'error-color': '#ff3b3b', // 错误色
            'font-size-base': '14px', // 主字号
            'font-size-lg': '@font-size-base + 2px',
            'heading-color': 'rgba(0, 0, 0, 0.9)', // 标题色
            'text-color': 'rgba(0, 0, 0, 0.65)', // 主文本色2
            'text-color-secondary': 'rgba(0, 0, 0, 0.40)', // 次文本色
            'disabled-color': 'rgba(0, 0, 0, 0.25)', // 失效色
            'border-radius-base': '2px', // 组件/浮层圆角
            'border-color-base': '#d9d9d9', // 边框色
            'box-shadow-base':
              '0 3px 6px -4px rgba(0, 0, 0, 0.12), 0 6px 16px 0 rgba(0, 0, 0, 0.08),0 9px 28px 8px rgba(0, 0, 0, 0.05)', // 浮层阴影
            'menu-inline-toplevel-item-height': '50px',
            'menu-item-height': '50px',
            'menu-collapsed-width': '80px',
            'menu-item-color': '@text-color',
            'menu-highlight-color': '@primary-color',
            'menu-item-active-border-width': '3px',
            'menu-item-group-title-color': '@text-color-secondary',
            'menu-icon-size': '@font-size-base',
            'menu-icon-size-lg': '@font-size-lg',
            'menu-item-vertical-margin': '1px',
            'menu-item-font-size': '@font-size-base',
            'menu-item-boundary-margin': '8px',
            'layout-header-height': '60px',
            'breadcrumb-height': '49px',
            'layout-body-background': '#f2f4f6',
            'content-height':
              'calc(100vh - @layout-header-height - @breadcrumb-height - 24px)'
          },
          javascriptEnabled: true
        }
      }
    }
  }
}
