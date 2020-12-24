# SmartDBS-Demo

此工程为SmartDBS配套Demo，其中包含两大块

  后端工程：smart-dbs-demo
  
  前端工程：smart-dbs-web-demo

后端工程使用文档地址：https://docs.zkclouds.com/demo/

前端工程相关使用文档附在工程中，下载后查看工程README文件

Smart DBS官方网站：https://dbs.zkclouds.com/

**工程结构**
 
├─smart-dbs-demo                        --后端工程
│  ├─smart-dbs-demo-basic               --基础模块
│  │  ├─smart-dbs-demo-common           --公共模块
│  │  │  └─src
│  │  └─smart-dbs-demo-mybatis          --持久层中间件
│  │      └─src
│  ├─smart-dbs-demo-biz                 --业务模块
│  │  └─src                              
│  │      ├─main
│  │      │  ├─java
│  │      │  │  └─com
│  │      │  │      └─zkteco
│  │      │  │          └─dbs
│  │      │  │              ├─acc      --门禁模块 
│  │      │  │              ├─att      --考勤模块
│  │      │  │              ├─company  --企业模块
│  │      │  │              ├─device   --设备模块
│  │      │  │              ├─receiver --数据推送模块
│  │      │  │              └─system   --系统信息模块
│  │      │  └─resources
│  │      │      ├─mapper              --DAO实现
│  │      │      │  ├─acc              --门禁业务 
│  │      │      │  ├─att              --考勤业务 
│  │      │      │  ├─company          --企业业务
│  │      │      │  └─device           --设备业务
│  │      │      └─zk-i18n             --国际化 
│  │      └─test
│  └─smart-dbs-demo-startup            --工程启动模块
│      └─src
└─smart-dbs-web-demo                   --前端工程
   ├─public                            --公共目录 
   └─src
      ├─assets                         --静态资源 
      ├─components                     --公共组件 
      ├─i18n                           --国际化文件
      ├─layout                         --公共布局 
      ├─mixins                         --全局方法
      ├─modules                        --业务模块
      ├─router                         --路由 
      ├─service                        --服务端API 
      ├─store                          --状态对象 
      ├─style                          --全局样式 
      ├─utils                          --工具库
      ├─App.vue                        --vue模板
      ├─main.js                        --入口文件
      ├─registerComponent.js           --UI注册组件
      └─registerServiceWorker.js       --注册PWA 
   ├─package.json                       
   ├─README.md                          
   └─vue.config.js