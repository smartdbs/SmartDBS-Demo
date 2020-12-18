/*
 * @Author: your name
 * @Date: 2019-10-09 10:50:39
 * @LastEditTime: 2020-05-20 16:54:10
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: \smart-dbs-web-pc\distStart.js
 */
var express = require('express')
const packageConfig = require('./package.json')
var { createProxyMiddleware } = require('http-proxy-middleware')

var proxyOption1 = {
  target: packageConfig.serviceUrl,
  changeOrigin: true,
  pathRewrite: {
    '^/api': '/'
  }
}

var proxyOption2 = {
  target: packageConfig.serviceUrl,
  changeOrigin: true,
  pathRewrite: {}
}

var app = express()
app.use(express.static('dist'))

app.use('^/api/**', createProxyMiddleware(proxyOption1))
app.use('^/temp/**', createProxyMiddleware(proxyOption2))

app.get('*', function(req, res) {
  res.redirect('dist/index.html')
})

let hostName = '192.168.211.30'
let port = '8081'
var server = app.listen(port, hostName, function() {
  // var host = server.address().address
  // var port = server.address().port
  // host = 'localhost'
  console.log(server.address().address)
  console.log(`应用实例，访问地址为 http://${hostName}:${port}/`)
})
