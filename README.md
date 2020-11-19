yeepay1.0版本，使用 springboot + dubbo 架构开发,支持分布式部署.

### 开发说明
yeepay-generator 生成mybatis代码,然后将model拷贝到yeepay-core项目中,将mapper拷贝到yeepay-service项目中,拷贝mapper时要比对是否有修改

yeepay-service 为dubbo服务生产者,所有与数据库操作,或公共的的业务逻辑都封装此业务层

yeepay-core 为公共方法,dubbo服务接口以及实体bean,每个项目都需要引用

yeepay-manage 运营管理平台的接口

yeepay-merchant 商户系统的接口

yeepay-agent 代理商系统的接口

yeepay-pay 支付核心,所有支付渠道对接实现

yeepay-task 定时任务,包括对账服务,结算服务.部署时需单节点部署

| 项目  | 端口 | 描述
|---|---|---
|yeepay-core |  | 公共方法,实体Bean,API接口定义
|yeepay-generator |  | mybatis数据访问层生成代码
|yeepay-manage | 8193 | 运营平台接口
|yeepay-merchant | 8191 | 商户系统接口
|yeepay-agent | 8192 | 代理商系统接口
|yeepay-pay | 3020 | 支付核心系统
|yeepay-service |  | 业务接口
|yeepay-task | 8194 | 定时任务,包括对账和结算服务