# blog-server
博客系统的后端，使用`SpringBoot`搭建，数据库使用`Postgresql`。使用了`Druid`进行SQL监控、`Redis`进行缓存管理和实现文章排行榜、`Sa-Token`进行登录鉴权。图片上传和验证码发送调用阿里云的API。Web服务器放弃`tomcat`使用`undertow`。

完整介绍[lisang_blog](https://github.com/wnnce/lisang_blog)

演示[Demo](https://blog.zeroxn.com)

## 使用到的工具框架
- MyBatis
- PageHelper
- Druid
- gson
- lombok
- gson
## 后续计划
- 缓存支持更多数据
- 引入多线程支持
- 按照需求添加功能