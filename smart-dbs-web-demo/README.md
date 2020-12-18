# DBS API Demo 前端工程

## 使用技术栈
* NODEJS 基础运行环境
* Vue 前端渐进式框架
* ant-design-vue Vue的前端UI组件库

## 前端项目运行流程
>>注：具体详细配置参考后面详情
* 第一步：安装node 运行环境
* 第二步：下载前端源代码
* 第三步：在前端项目根目录配置后端服务
* 第四步：安装项目依赖，运行 npm install 命令
* 第五步：运行开发环境，运行 npm run dev 命令
* 第六部：开发完成之后，执行 npm run build 命令编译生产环境，根目录下生产dist文件即前端开发环境代码。

## 后端服务地址配置
    在根目录下，找到package.json ，修改内容中的serviceUrl，为您的后端服务地址serviceUrl,

## 安装项目依赖，执行以下命令
```
npm install
```
## 运行开发环境，执行以下命令
```
npm run dev
```

## 编译生产环境，执行以下命令，在根目录下生成dist目录
```
npm run build
```

## 生成包含打包分析文件，执行以下命令
```
npm run report
```

## 测试打包之后的文件，执行以下命令
```
npm run start
```

## 自动修复格式化，执行以下命令
```
npm run lint
```
