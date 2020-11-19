/* 支付中心相关表结构 */
DROP TABLE IF EXISTS t_mch_info;
CREATE TABLE `t_mch_info` (
                            `MchId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商户ID',
                            `Name` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
                            `userName` varchar(30) DEFAULT NULL COMMENT '用户登录名',
                            `realName` varchar(30) DEFAULT NULL COMMENT '用户真实姓名',
                            `Type` tinyint(6) NOT NULL DEFAULT 1 COMMENT '类型:1-平台账户,2-私有账户',
                            `AgentId` bigint(20) DEFAULT NULL COMMENT '代理商ID',
                            `ParentAgentId` bigint(20) DEFAULT 0 COMMENT '一级代理商ID',
                            `Mobile` bigint(20) DEFAULT NULL COMMENT '手机号',
                            `Email` varchar(128) DEFAULT NULL COMMENT '邮箱',
                            `idCard` varchar(32) DEFAULT NULL COMMENT '身份证号',
                            `qq` varchar(32) DEFAULT NULL COMMENT 'QQ号码',
                            `siteName` varchar(128) DEFAULT NULL COMMENT '网站名称',
                            `siteUrl` varchar(128) DEFAULT NULL COMMENT '网站地址',
                            `Password` varchar(128) DEFAULT NULL COMMENT '密码',
                            `PayPassword` varchar(32) DEFAULT NULL COMMENT '支付密码',
                            `Role` varchar(512) DEFAULT NULL COMMENT '角色',
                            `MchRate` decimal(20,6) DEFAULT NULL COMMENT '商户费率,百分比',
                            `PrivateKey` varchar(128) DEFAULT NULL COMMENT '私钥',
                            `Address` varchar(128) DEFAULT NULL COMMENT '通讯地址',
                            `AccountAttr` tinyint(6) DEFAULT 0 COMMENT '账户属性:0-对私,1-对公,默认对私',
                            `AccountType` tinyint(6) DEFAULT 1 COMMENT '账户类型:1-银行卡转账,2-微信转账,3-支付宝转账',
                            `BankName` varchar(128) DEFAULT NULL COMMENT '开户行名称',
                            `BankNetName` varchar(128) DEFAULT NULL COMMENT '开户网点名称',
                            `AccountName` varchar(64) DEFAULT NULL COMMENT '账户名',
                            `AccountNo` varchar(64) DEFAULT NULL COMMENT '账户号',
                            `Province` varchar(32) DEFAULT NULL COMMENT '开户行所在省',
                            `City` varchar(32) DEFAULT NULL COMMENT '开户行所在市',
                            `SettConfigMode` tinyint(6) NOT NULL DEFAULT '1' COMMENT '结算配置模式,1-继承系统,2-自定义',
                            `DrawFlag` tinyint(6) NOT NULL DEFAULT '1' COMMENT '提现开关:0-关闭,1-开启',
                            `AllowDrawWeekDay` varchar(64) DEFAULT NULL COMMENT '每周周几允许提现,数字表示,多个逗号分隔',
                            `DrawDayStartTime` varchar(20) DEFAULT NULL COMMENT '每天提现开始时间',
                            `DrawDayEndTime` varchar(20) DEFAULT NULL COMMENT '每天提现结束时间',
                            `DrawMaxDayAmount` bigint(20) DEFAULT NULL COMMENT '每天提现最大金额,单位分',
                            `FeeType` tinyint(6) NOT NULL DEFAULT 2 COMMENT '手续费类型,1-百分比收费,2-固定收费',
                            `FeeRate` decimal(20,6) DEFAULT 0 COMMENT '手续费百分比值',
                            `FeeLevel` varchar(256) DEFAULT 0 COMMENT '手续费等级值,使用json存储每一等级信息',
                            `DrawFeeLimit` bigint(20) DEFAULT NULL COMMENT '每笔提现手续费上限,单位分',
                            `RiskDay` int DEFAULT 0 COMMENT '风险预存期',
                            `MaxDrawAmount` bigint(20) DEFAULT NULL COMMENT '最大提现金额,单位分',
                            `MinDrawAmount` bigint(20) DEFAULT NULL COMMENT '最小提现金额,单位分',
                            `DayDrawTimes` int DEFAULT 10 COMMENT '每日提现次数',
                            `SettType` tinyint(6) NOT NULL DEFAULT 0 COMMENT '结算类型,0-手动提现,1-自动结算',
                            `SettMode` tinyint(6) NOT NULL DEFAULT 2 COMMENT '结算方式,1-D0到账,2-D1到账,3-T0到账,4-T1到账',
                            `Status` tinyint(6) NOT NULL COMMENT '商户状态,-1-待审核,0-停止使用,1-使用中',
                            `LoginSecurityType` tinyint(6) NOT NULL DEFAULT 1 COMMENT '登录安全类型,1-仅登录密码验证,2-登录密码+谷歌组合验证',
                            `PaySecurityType` tinyint(6) NOT NULL DEFAULT 1 COMMENT '支付安全类型,0-无需验证,1-仅支付密码验证,2-仅谷歌验证,3-支付密码+谷歌组合验证',
                            `GoogleAuthStatus` tinyint(6) NOT NULL DEFAULT 0 COMMENT '绑定谷歌验证状态,0-未绑定,1-已绑定',
                            `GoogleAuthSecretKey` varchar(32) DEFAULT NULL COMMENT '谷歌验证密钥',
                            `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                            `Tag` varchar(128) DEFAULT NULL COMMENT '标记',
                            `LastLoginIp` varchar(128) DEFAULT NULL COMMENT '最后一次登录IP',
                            `LastLoginTime` datetime DEFAULT NULL COMMENT '最后一次登录时间',
                            `LastPasswordResetTime` datetime DEFAULT NULL COMMENT '最后一次重置密码时间',
                            `LoginWhiteIp` varchar(1024) DEFAULT NULL COMMENT '登录白名单,半角逗号分隔',
                            `LoginBlackIp` varchar(1024) DEFAULT NULL COMMENT '登录黑名单,半角逗号分隔',
                            `PayWhiteIp` varchar(1024) DEFAULT NULL COMMENT '支付白名单,半角逗号分隔',
                            `PayBlackIp` varchar(1024) DEFAULT NULL COMMENT '支付黑名单,半角逗号分隔',
                            `AgentpayWhiteIp` varchar(1024) DEFAULT NULL COMMENT '代付白名单,半角逗号分隔',
                            `AgentpayBlackIp` varchar(1024) DEFAULT NULL COMMENT '代付黑名单,半角逗号分隔',
                            `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `offRechargeRate` decimal(20,6) DEFAULT 0 COMMENT '线下充值费率,百分比',
                            PRIMARY KEY (`MchId`),
                            UNIQUE KEY `IDX_UserName` (`userName`),
                            UNIQUE KEY `IDX_Mobile` (`Mobile`),
                            UNIQUE KEY `IDX_Email` (`Email`),
                            UNIQUE KEY `IDX_Tag` (`Tag`)
) ENGINE=InnoDB AUTO_INCREMENT=20000000 DEFAULT CHARSET=utf8mb4 COMMENT='商户信息表';

DROP TABLE IF EXISTS t_mch_app;
CREATE TABLE `t_mch_app` (
                           `AppId` varchar(32) NOT NULL COMMENT '应用ID',
                           `AppName` varchar(128) NOT NULL DEFAULT '' COMMENT '应用名称',
                           `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                           `MchType` tinyint(6) NOT NULL COMMENT '商户类型:1-平台账户,2-私有账户',
                           `Status` tinyint(6) NOT NULL COMMENT '应用状态,0-停止使用,1-使用中',
                           `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                           `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`AppId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户应用表';

DROP TABLE IF EXISTS t_pay_order;
CREATE TABLE `t_pay_order` (
                             `PayOrderId` varchar(30) NOT NULL COMMENT '支付订单号',
                             `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                             `MchType` tinyint(6) NOT NULL COMMENT '商户类型:1-平台账户,2-私有账户',
                             `MchRate` decimal(20,6) DEFAULT NULL COMMENT '商户费率',
                             `MchIncome` bigint(20) DEFAULT NULL COMMENT '商户入账,单位分',
                             `AppId` varchar(32) DEFAULT NULL COMMENT '应用ID',
                             `MchOrderNo` varchar(30) NOT NULL COMMENT '商户订单号',
                             `AgentId` bigint(20) DEFAULT NULL COMMENT '代理商ID',
                             `ParentAgentId` bigint(20) DEFAULT NULL COMMENT '一级代理商ID',
                             `AgentRate` decimal(20,6) DEFAULT NULL COMMENT '代理商费率',
                             `ParentAgentRate` decimal(20, 6) COMMENT '一级代理商费率',
                             `AgentProfit` bigint(20) DEFAULT NULL COMMENT '代理商利润,单位分',
                             `ParentAgentProfit` bigint(20) DEFAULT 0 COMMENT '一级代理商利润,单位分',
                             `ProductId` int(11) DEFAULT NULL COMMENT '支付产品ID',
                             `PassageId` int(11) DEFAULT NULL COMMENT '通道ID',
                             `PassageAccountId` int(11) DEFAULT NULL COMMENT '通道账户ID',
                             `ChannelType` varchar(30) NOT NULL COMMENT '渠道类型,对接支付接口类型代码',
                             `ChannelId` varchar(24) NOT NULL COMMENT '渠道ID,对应支付接口代码',
                             `Amount` bigint(20) NOT NULL COMMENT '支付金额,单位分',
                             `Currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
                             `Status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '支付状态,0-订单生成,1-支付中,2-支付成功,3-业务处理完成,4-已退款',
                             `ClientIp` varchar(32) DEFAULT NULL COMMENT '客户端IP',
                             `Device` varchar(64) DEFAULT NULL COMMENT '设备',
                             `Subject` varchar(64) NOT NULL COMMENT '商品标题',
                             `Body` varchar(256) NOT NULL COMMENT '商品描述信息',
                             `Extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起额外参数',
                             `ChannelUser` varchar(32) DEFAULT NULL COMMENT '渠道用户标识,如微信openId,支付宝账号',
                             `ChannelMchId` varchar(32) NOT NULL COMMENT '渠道商户ID',
                             `ChannelOrderNo` varchar(64) DEFAULT NULL COMMENT '渠道订单号',
                             `ChannelAttach` varchar(1024) DEFAULT NULL COMMENT '渠道数据包',
                             `PlatProfit` bigint(20) DEFAULT NULL COMMENT '平台利润,单位分',
                             `ChannelRate` decimal(20,6) DEFAULT NULL COMMENT '渠道费率',
                             `ChannelCost` bigint(20) DEFAULT NULL COMMENT '渠道成本,单位分',
                             `IsRefund` tinyint(6) NOT NULL DEFAULT '0' COMMENT '是否退款,0-未退款,1-退款',
                             `RefundTimes` int NULL COMMENT '退款次数',
                             `SuccessRefundAmount` bigint(20) NULL COMMENT '成功退款金额,单位分',
                             `ErrCode` varchar(64) DEFAULT NULL COMMENT '渠道支付错误码',
                             `ErrMsg` varchar(128) DEFAULT NULL COMMENT '渠道支付错误描述',
                             `Param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
                             `Param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
                             `NotifyUrl` varchar(128) NOT NULL COMMENT '通知地址',
                             `ReturnUrl` varchar(128) DEFAULT '' COMMENT '跳转地址',
                             `ExpireTime` datetime DEFAULT NULL COMMENT '订单失效时间',
                             `PaySuccTime` datetime DEFAULT NULL COMMENT '订单支付成功时间',
                             `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `ProductType` tinyint(6) DEFAULT 1 COMMENT '产品类型:1-收款,2-充值',
                             PRIMARY KEY (`PayOrderId`),
                             UNIQUE KEY `IDX_MchId_MchOrderNo` (`MchId`, MchOrderNo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付订单表';

DROP TABLE IF EXISTS t_iap_receipt;
CREATE TABLE `t_iap_receipt` (
                               `PayOrderId` varchar(30) NOT NULL COMMENT '支付订单号',
                               `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                               `AppId` varchar(32) NOT NULL COMMENT '应用ID',
                               `TransactionId` varchar(24) NOT NULL COMMENT 'IAP业务号',
                               `ReceiptData` TEXT NOT NULL COMMENT '渠道ID',
                               `Status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '处理状态:0-未处理,1-处理成功,-1-处理失败',
                               `HandleCount` tinyint(6) NOT NULL DEFAULT 0 COMMENT '处理次数',
                               `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`PayOrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='苹果支付凭据表';

DROP TABLE IF EXISTS t_trans_order;
CREATE TABLE `t_trans_order` (
                               `TransOrderId` varchar(30) NOT NULL COMMENT '转账订单号',
                               `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                               `MchType` tinyint(6) NOT NULL COMMENT '商户类型:1-平台账户,2-私有账户',
                               `AppId` varchar(32) DEFAULT NULL COMMENT '应用ID',
                               `MchTransNo` varchar(30) NOT NULL COMMENT '商户转账单号',
                               `ChannelType` varchar(30) NOT NULL COMMENT '渠道类型,对接支付接口类型代码',
                               `ChannelId` varchar(24) NOT NULL COMMENT '渠道ID,对应支付接口代码',
                               `PassageId` int(11) DEFAULT NULL COMMENT '通道ID',
                               `PassageAccountId` int(11) DEFAULT NULL COMMENT '通道账户ID',
                               `Amount` bigint(20) NOT NULL COMMENT '转账金额,单位分',
                               `Currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
                               `Status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '转账状态:0-订单生成,1-转账中,2-转账成功,3-转账失败,4-业务处理完成',
                               `Result` tinyint(6) NOT NULL DEFAULT '0' COMMENT '转账结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败',
                               `ClientIp` varchar(32) DEFAULT NULL COMMENT '客户端IP',
                               `Device` varchar(64) DEFAULT NULL COMMENT '设备',
                               `RemarkInfo` varchar(256) DEFAULT NULL COMMENT '备注',
                               `ChannelUser` varchar(32) DEFAULT NULL COMMENT '渠道用户标识,如微信openId,支付宝账号',
                               `AccountAttr` tinyint(6) DEFAULT 0 COMMENT '账户属性:0-对私,1-对公,默认对私',
                               `AccountType` tinyint(6) DEFAULT NULL COMMENT '账户类型:1-银行卡转账,2-微信转账,3-支付宝转账',
                               `AccountName` varchar(64) DEFAULT NULL COMMENT '账户名',
                               `AccountNo` varchar(64) DEFAULT NULL COMMENT '账户号',
                               `Province` varchar(32) DEFAULT NULL COMMENT '开户行所在省',
                               `City` varchar(32) DEFAULT NULL COMMENT '开户行所在市',
                               `BankName` varchar(128) DEFAULT NULL COMMENT '开户行名称',
                               `BankType` bigint(20) DEFAULT NULL COMMENT '联行号',
                               `BankCode` varchar(32) DEFAULT NULL COMMENT '银行代码',
                               `ChannelMchId` varchar(32) NOT NULL COMMENT '渠道商户ID',
                               `ChannelRate` decimal(20,6) DEFAULT NULL COMMENT '渠道费率',
                               `channelFeeEvery` bigint(20) DEFAULT NULL COMMENT '渠道每笔费用,单位分',
                               `ChannelCost` bigint(20) DEFAULT NULL COMMENT '渠道成本,单位分',
                               `ChannelOrderNo` varchar(32) DEFAULT NULL COMMENT '渠道订单号',
                               `ChannelErrCode` varchar(128) DEFAULT NULL COMMENT '渠道错误码',
                               `ChannelErrMsg` varchar(128) DEFAULT NULL COMMENT '渠道错误描述',
                               `Extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
                               `NotifyUrl` varchar(128) DEFAULT NULL COMMENT '通知地址',
                               `Param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
                               `Param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
                               `ExpireTime` datetime DEFAULT NULL COMMENT '订单失效时间',
                               `TransSuccTime` datetime DEFAULT NULL COMMENT '订单转账成功时间',
                               `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`TransOrderId`),
                               UNIQUE KEY `IDX_MchId_MchOrderNo` (`MchId`, MchTransNo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='转账订单表';

DROP TABLE IF EXISTS t_refund_order;
CREATE TABLE `t_refund_order` (
                                `RefundOrderId` varchar(30) NOT NULL COMMENT '退款订单号',
                                `PayOrderId` varchar(30) NOT NULL COMMENT '支付订单号',
                                `ChannelPayOrderNo` varchar(64) DEFAULT NULL COMMENT '渠道支付单号',
                                `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                `MchType` tinyint(6) NOT NULL COMMENT '商户类型:1-平台账户,2-私有账户',
                                `AppId` varchar(32) NOT NULL COMMENT '应用ID',
                                `MchRefundNo` varchar(30) NOT NULL COMMENT '商户退款单号',
                                `ChannelType` varchar(30) NOT NULL COMMENT '渠道类型,WX:微信,ALIPAY:支付宝',
                                `ChannelId` varchar(24) NOT NULL COMMENT '渠道ID',
                                `PassageId` int(11) DEFAULT NULL COMMENT '通道ID',
                                `PayAmount` bigint(20) NOT NULL COMMENT '支付金额,单位分',
                                `RefundAmount` bigint(20) NOT NULL COMMENT '退款金额,单位分',
                                `Currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
                                `Status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成',
                                `Result` tinyint(6) NOT NULL DEFAULT '0' COMMENT '退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败',
                                `ClientIp` varchar(32) DEFAULT NULL COMMENT '客户端IP',
                                `Device` varchar(64) DEFAULT NULL COMMENT '设备',
                                `RemarkInfo` varchar(256) DEFAULT NULL COMMENT '备注',
                                `ChannelUser` varchar(32) DEFAULT NULL COMMENT '渠道用户标识,如微信openId,支付宝账号',
                                `UserName` varchar(24) DEFAULT NULL COMMENT '用户姓名',
                                `ChannelMchId` varchar(32) NOT NULL COMMENT '渠道商户ID',
                                `ChannelOrderNo` varchar(32) DEFAULT NULL COMMENT '渠道订单号',
                                `ChannelErrCode` varchar(128) DEFAULT NULL COMMENT '渠道错误码',
                                `ChannelErrMsg` varchar(128) DEFAULT NULL COMMENT '渠道错误描述',
                                `Extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
                                `NotifyUrl` varchar(128) DEFAULT NULL COMMENT '通知地址',
                                `Param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
                                `Param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
                                `ExpireTime` datetime DEFAULT NULL COMMENT '订单失效时间',
                                `RefundSuccTime` datetime DEFAULT NULL COMMENT '订单退款成功时间',
                                `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`RefundOrderId`),
                                UNIQUE KEY `IDX_MchId_MchOrderNo` (`MchId`, MchRefundNo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退款订单表';

DROP TABLE IF EXISTS t_mch_notify;
CREATE TABLE `t_mch_notify` (
                              `OrderId` varchar(64) NOT NULL COMMENT '订单ID',
                              `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                              `AppId` varchar(32) DEFAULT NULL COMMENT '应用ID',
                              `MchOrderNo` varchar(64) NOT NULL COMMENT '商户订单号',
                              `OrderType` varchar(8) NOT NULL COMMENT '订单类型:1-支付,2-转账,3-退款',
                              `NotifyUrl` varchar(2048) NOT NULL COMMENT '通知地址',
                              `NotifyCount` tinyint(6) NOT NULL DEFAULT 0 COMMENT '通知次数',
                              `Result` varchar(2048) DEFAULT NULL COMMENT '通知响应结果',
                              `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '通知状态,1-通知中,2-通知成功,3-通知失败',
                              `LastNotifyTime` datetime DEFAULT NULL COMMENT '最后一次通知时间',
                              `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`OrderId`),
                              UNIQUE KEY `IDX_MchId_OrderType_MchOrderNo` (`MchId`, `OrderType`, `MchOrderNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户通知表';

# 账户结算相关表结构
DROP TABLE IF EXISTS t_mch_account;
CREATE TABLE `t_mch_account` (
                               `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                               `Name` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
                               `Type` tinyint(6) NOT NULL DEFAULT 1 COMMENT '类型:1-平台账户,2-私有账户',
                               `Balance` bigint(20) NOT NULL COMMENT '账户余额',
                               `UnBalance` bigint(20) NOT NULL COMMENT '不可用余额',
                               `SecurityMoney` bigint(20) NOT NULL COMMENT '保证金',
                               `TotalIncome` bigint(20) NOT NULL COMMENT '总收益',
                               `TotalExpend` bigint(20) NOT NULL COMMENT '总支出',
                               `TodayIncome` bigint(20) NOT NULL COMMENT '今日收益',
                               `TodayExpend` bigint(20) NOT NULL COMMENT '今日支出',
                               `SettAmount` bigint(20) NOT NULL COMMENT '可结算金额',
                               `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '账户状态,1-可用,0-停止使用',
                               `AccountUpdateTime` datetime DEFAULT NULL COMMENT '账户修改时间',
                               `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `FrozenMoney` bigint(20) DEFAULT 0 COMMENT '冻结金额',
                               `AgentpayBalance` bigint(20) DEFAULT 0 COMMENT '代付余额',
                               `UnAgentpayBalance` bigint(20) DEFAULT 0 COMMENT '不可用代付余额',
                               PRIMARY KEY (`MchId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户账户表';

DROP TABLE IF EXISTS t_mch_account_history;
CREATE TABLE `t_mch_account_history` (
                                       `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                       `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                       `Amount` bigint(20) NOT NULL COMMENT '变动金额',
                                       `Balance` bigint(20) NOT NULL COMMENT '变更前账户余额',
                                       `AfterBalance` bigint(20) NOT NULL COMMENT '变更后账户余额',
                                       `AgentId` bigint(20) DEFAULT NULL COMMENT '代理商ID',
                                       `ParentAgentId` bigint(20) DEFAULT NULL COMMENT '一级代理商ID',
                                       `OrderAmount` bigint(20) DEFAULT 0 COMMENT '订单金额',
                                       `Fee` bigint(20) DEFAULT NULL COMMENT '手续费',
                                       `AgentProfit` bigint(20) DEFAULT 0 COMMENT '代理商利润,单位分',
                                       `ParentAgentProfit` bigint(20) DEFAULT 0 COMMENT '父级代理商利润,单位分',
                                       `PlatProfit` bigint(20) DEFAULT 0 COMMENT '平台利润,单位分',
                                       `ChannelCost` bigint(20) DEFAULT 0 COMMENT '渠道成本,单位分',
                                       `FundDirection` tinyint(6) NOT NULL COMMENT '资金变动方向,1-加款,2-减款',
                                       `IsAllowSett` tinyint(6) NOT NULL COMMENT '是否允许结算,1-允许,0-不允许',
                                       `MchSettStatus` tinyint(6) NOT NULL COMMENT '商户结算状态,1-已完成,0-未完成',
                                       `AgentSettStatus` tinyint(6) NOT NULL COMMENT '代理商结算状态,1-已完成,0-未完成',
                                       `parentAgentSettStatus` tinyint(6) DEFAULT 0 COMMENT '父级代理商结算状态,1-已完成,0-未完成',
                                       `OrderId` varchar(64) NOT NULL COMMENT '平台订单号',
                                       `ChannelOrderNo` varchar(32) DEFAULT NULL COMMENT '渠道订单号',
                                       `BizType` tinyint(6) NOT NULL DEFAULT '1' COMMENT '业务类型,1-支付,2-提现,3-调账,4-充值,5-差错处理,6-代付',
                                       `RiskDay` int NOT NULL COMMENT '风险预存期',
                                       `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                       `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `BizItem` varchar(2) NOT NULL DEFAULT '' COMMENT '业务类目:10-余额,11-代付余额,12-冻结金额,13-保证金,20-支付,21-代付,22-线下充值,23-线上充值',
                                       `AgentRiskDay` int(11) DEFAULT 0 COMMENT '代理商风险预存期',
                                       PRIMARY KEY (`Id`),
                                       UNIQUE KEY `IDX_OrderId` (`OrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户资金账户流水表';

DROP TABLE IF EXISTS t_mch_sett_daily_collect;
CREATE TABLE `t_mch_sett_daily_collect` (
                                          `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                          `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                          `CollectDate` date NOT NULL COMMENT '汇总日期',
                                          `CollectType` tinyint(6) NOT NULL COMMENT '汇总类型:1-存入/减少汇总,2-临时汇总,3-遗留汇总',
                                          `TotalAmount` bigint(20) NOT NULL COMMENT '交易金额',
                                          `TotalCount` INT NOT NULL COMMENT '交易总笔数',
                                          `TotalMchIncome` bigint(20) DEFAULT 0 COMMENT '商户入账,单位分',
                                          `TotalAgentProfit` bigint(20) DEFAULT 0 COMMENT '代理商利润,单位分',
                                          `TotalPlatProfit` bigint(20) DEFAULT 0 COMMENT '平台利润,单位分',
                                          `TotalChannelCost` bigint(20) DEFAULT 0 COMMENT '渠道成本,单位分',
                                          `SettStatus` tinyint(6) NOT NULL COMMENT '结算状态,0-未结算,1-已结算',
                                          `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                          `RiskDay` int NOT NULL COMMENT '风险预存期天数',
                                          `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户每日结算汇总';

DROP TABLE IF EXISTS t_mch_sett_batch_record;
CREATE TABLE `t_mch_sett_batch_record` (
                                         `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                         `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                         `SettRecordId` bigint(20) NOT NULL COMMENT '结算记录ID',
                                         `BankAccountName` varchar(128) DEFAULT NULL COMMENT '收款银行户名',
                                         `BankAccountNumber` varchar(128) DEFAULT NULL COMMENT '收款银行账号',
                                         `BankName` varchar(128) DEFAULT NULL COMMENT '开户银行',
                                         `BankNetName` varchar(128) DEFAULT NULL COMMENT '开户网点名称',
                                         `BankProvince` varchar(128) DEFAULT NULL COMMENT '开户行所在省',
                                         `BankCity` varchar(128) DEFAULT NULL COMMENT '开户行所在市',
                                         `Amount` bigint(20) DEFAULT NULL COMMENT '金额',
                                         `PublicFlag` tinyint(6) NOT NULL COMMENT '对公私标识',
                                         `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='批量结算明细表';

DROP TABLE IF EXISTS t_mch_bank_account;
CREATE TABLE `t_mch_bank_account` (
                                    `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                    `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                    `Name` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
                                    `AccountType` tinyint(6) NOT NULL COMMENT '账号类型:1-银行账号,2-支付宝账号,3-微信账号',
                                    `BankName` varchar(64) DEFAULT '' COMMENT '银行名称',
                                    `AccountName` varchar(128) NOT NULL COMMENT '账号名称',
                                    `AccountNo` varchar(64) NOT NULL COMMENT '账号',
                                    `IsDefault` tinyint(6) NOT NULL DEFAULT 0 COMMENT '是否默认:0-不是默认,1-默认',
                                    `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                    `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`Id`),
                                    UNIQUE KEY `IDX_AccountNo` (`AccountNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户银行账号表';

DROP TABLE IF EXISTS t_mch_agentpay_record;
CREATE TABLE `t_mch_agentpay_record` (
                                       `AgentpayOrderId` varchar(30) NOT NULL COMMENT '代付单号',
                                       `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                       `MchType` tinyint(6) NOT NULL COMMENT '商户类型:1-平台账户,2-私有账户',
                                       `Amount` bigint(20) DEFAULT NULL COMMENT '申请代付金额',
                                       `Fee` bigint(20) DEFAULT NULL COMMENT '代付手续费',
                                       `RemitAmount` bigint(20) DEFAULT NULL COMMENT '打款金额',
                                       `SubAmount` bigint(20) DEFAULT NULL COMMENT '扣减账户金额',
                                       `AccountAttr` tinyint(6) DEFAULT 0 COMMENT '账户属性:0-对私,1-对公,默认对私',
                                       `AccountType` tinyint(6) DEFAULT 1 COMMENT '账户类型:1-银行卡转账,2-微信转账,3-支付宝转账',
                                       `AccountName` varchar(64) DEFAULT NULL COMMENT '账户名',
                                       `AccountNo` varchar(64) DEFAULT NULL COMMENT '账户号',
                                       `Province` varchar(32) DEFAULT NULL COMMENT '开户行所在省',
                                       `City` varchar(32) DEFAULT NULL COMMENT '开户行所在市',
                                       `BankName` varchar(128) DEFAULT NULL COMMENT '开户行名称',
                                       `BankNetName` varchar(128) DEFAULT NULL COMMENT '开户网点名称',
                                       `BankNumber` varchar(64) DEFAULT NULL COMMENT '联行号',
                                       `BankCode` varchar(32) DEFAULT NULL COMMENT '银行代码',
                                       `Status` tinyint(6) DEFAULT 0 COMMENT '状态:0-待处理,1-处理中,2-成功,3-失败',
                                       `TransOrderId` varchar(30) DEFAULT NULL COMMENT '转账订单号',
                                       `TransMsg` varchar(128) DEFAULT NULL COMMENT '转账返回消息',
                                       `ChannelId` varchar(24) DEFAULT NULL COMMENT '渠道ID',
                                       `PassageId` int(11) DEFAULT NULL COMMENT '通道ID',
                                       `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                       `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `ClientIp` varchar(32) DEFAULT NULL COMMENT '客户端IP',
                                       `Device` varchar(64) DEFAULT NULL COMMENT '设备',
                                       `BatchNo` varchar(64) DEFAULT NULL COMMENT '代付批次号',
                                       `AgentpayChannel` tinyint(6) DEFAULT '1' COMMENT '代付渠道:1-商户后台,2-API接口',
                                       `MchOrderNo` varchar(64) DEFAULT NULL COMMENT '商户单号',
                                       `NotifyUrl` varchar(128) DEFAULT NULL COMMENT '通知地址',
                                       `Extra` varchar(128) DEFAULT NULL COMMENT '扩展域',
                                       PRIMARY KEY (`AgentpayOrderId`),
                                       UNIQUE KEY `IDX_TransOrderId` (`TransOrderId`),
                                       UNIQUE KEY `IDX_MchId_MchOrderNo` (MchId, MchOrderNo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代付记录表';

DROP TABLE IF EXISTS `t_mch_trade_order`;
CREATE TABLE `t_mch_trade_order` (
                                   `TradeOrderId` varchar(30) NOT NULL COMMENT '交易单号',
                                   `TradeType` tinyint(6) DEFAULT NULL COMMENT '交易类型:1-收款,2-充值',
                                   `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                   `AppId` varchar(32) DEFAULT NULL COMMENT '应用ID',
                                   `ClientIp` varchar(32) DEFAULT NULL COMMENT '客户端IP',
                                   `Device` varchar(64) DEFAULT NULL COMMENT '设备',
                                   `GoodsId` varchar(30) DEFAULT NULL COMMENT '商品ID',
                                   `Subject` varchar(64) NOT NULL COMMENT '商品标题',
                                   `Body` varchar(256) NOT NULL COMMENT '商品描述信息',
                                   `Amount` bigint(20) NOT NULL COMMENT '交易金额,单位分',
                                   `MchIncome` bigint(20) DEFAULT NULL COMMENT '商户入账,单位分',
                                   `UserId` varchar(30) DEFAULT NULL COMMENT '用户ID',
                                   `ChannelUserId` varchar(64) DEFAULT NULL COMMENT '支付渠道用户ID(微信openID或支付宝账号等第三方支付账号)',
                                   `Status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '订单状态,生成(0),处理中(1),成功(2),失败(-1)',
                                   `PayOrderId` varchar(30) DEFAULT NULL COMMENT '支付订单号',
                                   `ProductId` varchar(24) DEFAULT NULL COMMENT '产品ID',
                                   `ProductType` tinyint(6) DEFAULT NULL COMMENT '产品类型',
                                   `TradeSuccTime` datetime DEFAULT NULL COMMENT '交易成功时间',
                                   `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (`TradeOrderId`),
                                   UNIQUE KEY `IDX_PayOrderId` (`PayOrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户交易表';

DROP TABLE IF EXISTS t_mch_qr_code;
CREATE TABLE `t_mch_qr_code` (
                               `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                               `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                               `AppId` varchar(32) NOT NULL COMMENT '应用ID',
                               `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '状态,启用(1),停止(0)',
                               `Channels` varchar(128) DEFAULT NULL COMMENT '支付渠道,json格式[wx,alipay]',
                               `CodeName` varchar(24) DEFAULT NULL COMMENT '统一扫码名称',
                               `MinAmount` bigint(20) DEFAULT NULL COMMENT '最小金额,单位分',
                               `MaxAmount` bigint(20) DEFAULT NULL COMMENT '最大金额,单位分',
                               `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户统一扫码配置';

DROP TABLE IF EXISTS t_sys_message;
CREATE TABLE `t_sys_message` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                               `title` varchar(120) NOT NULL COMMENT '消息名称',
                               `message` text COMMENT '消息内容',
                               `status` tinyint DEFAULT 1 COMMENT '状态 0：隐藏, 1：显示',
                               `createUserId` bigint(20) COMMENT '创建者ID',
                               `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统消息表';

DROP TABLE IF EXISTS t_mgr_sys_user;
CREATE TABLE `t_mgr_sys_user` (
                                `userId` bigint NOT NULL AUTO_INCREMENT,
                                `userName` varchar(50) NOT NULL COMMENT '用户名',
                                `passWord` varchar(100) COMMENT '密码',
                                `nickName` varchar(100) COMMENT '用户昵称',
                                `email` varchar(32) COMMENT '邮箱',
                                `mobile` varchar(12) COMMENT '手机号',
                                `status` tinyint DEFAULT 1 COMMENT '状态 0：禁用, 1：正常',
                                `isSuperAdmin` tinyint DEFAULT 0 COMMENT '是否超级管理员 0：否, 1：是',
                                `createUserId` bigint(20) COMMENT '创建者ID',
                                `lastPasswordResetTime` datetime DEFAULT NULL COMMENT '最后一次重置密码时间',
                                `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`userId`),
                                UNIQUE KEY `IDX_UserName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

DROP TABLE IF EXISTS t_mgr_sys_role;
CREATE TABLE `t_mgr_sys_role` (
                                `roleId` bigint NOT NULL AUTO_INCREMENT,
                                `roleName` varchar(100) COMMENT '角色名称',
                                `createUserId` bigint(20) COMMENT '创建者ID',
                                `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

DROP TABLE IF EXISTS t_mgr_sys_user_role;
CREATE TABLE `t_mgr_sys_user_role` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `userId` bigint COMMENT '用户ID',
                                     `roleId` bigint COMMENT '角色ID',
                                     `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系表';

DROP TABLE IF EXISTS t_mgr_sys_resource;
CREATE TABLE `t_mgr_sys_resource` (
                                    `resourceId` bigint NOT NULL AUTO_INCREMENT,
                                    `name` varchar(50) COMMENT '资源名称',
                                    `title` varchar(50) COMMENT '资源标题',
                                    `jump` varchar(200) COMMENT '跳转URL',
                                    `permName` varchar(500) COMMENT '授权名,以ROLE_开头,如ROLE_MCH',
                                    `permUrl` varchar(500) COMMENT '授权URL,如:mch_info/**',
                                    `type` tinyint DEFAULT 1 COMMENT '资源类型 1：菜单, 2：按钮',
                                    `system` tinyint DEFAULT 1 COMMENT '所属系统 1：运营平台, 2：商户平台, 3:代理商系统',
                                    `icon` varchar(50) COMMENT '菜单图标',
                                    `orderNum` int COMMENT '排序',
                                    `parentId` bigint DEFAULT 0 COMMENT '父资源ID，一级为0',
                                    `status` tinyint DEFAULT 1 COMMENT '状态 0：禁用, 1：正常',
                                    `property` varchar(50) DEFAULT '' COMMENT '属性,为空都可见.否则对应商户类型,如1 表示平台账户可见, 1,2 表示平台账户和私有账户都可见',
                                    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`resourceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

DROP TABLE IF EXISTS t_mgr_sys_permission;
CREATE TABLE `t_mgr_sys_permission` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `roleId` bigint COMMENT '角色ID',
                                      `resourceId` bigint COMMENT '资源ID',
                                      `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授权表';

###  对账相关表
DROP TABLE IF EXISTS t_check_batch;
CREATE TABLE `t_check_batch` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                               `batchNo` varchar(32) NOT NULL COMMENT '对账批次号',
                               `billDate` date DEFAULT NULL COMMENT '账单时间(账单交易发生时间)',
                               `billType` tinyint(6) NOT NULL COMMENT '账单类型(默认全部是交易成功)',
                               `handleStatus` tinyint(6) NOT NULL DEFAULT 1 COMMENT '类型:0-未处理,1-已处理',
                               `bankType` varchar(30) NOT NULL COMMENT '银行类型,wxpay:微信,alipay:支付宝',
                               `channelMchId` varchar(32) DEFAULT NULL COMMENT '渠道商户ID',
                               `mistakeCount` int DEFAULT NULL COMMENT '所有差错总单数',
                               `unhandleMistakeCount` int DEFAULT NULL COMMENT '待处理的差错总单数',
                               `tradeCount` int DEFAULT NULL COMMENT '平台总交易单数',
                               `bankTradeCount` int DEFAULT NULL COMMENT '银行总交易单数',
                               `tradeAmount` bigint(20) DEFAULT NULL COMMENT '平台交易总金额',
                               `bankTradeAmount` bigint(20) DEFAULT NULL COMMENT '银行交易总金额',
                               `refundAmount` bigint(20) DEFAULT NULL COMMENT '平台退款总金额',
                               `bankRefundAmount` bigint(20) DEFAULT NULL COMMENT '银行退款总金额',
                               `fee` bigint(20) DEFAULT NULL COMMENT '平台总手续费',
                               `bankFee` bigint(20) DEFAULT NULL COMMENT '银行总手续费',
                               `orgCheckFilePath` varchar(128) DEFAULT NULL COMMENT '原始对账文件存放地址',
                               `releaseCheckFilePath` varchar(128) DEFAULT NULL COMMENT '解析后文件存放地址',
                               `releaseStatus` tinyint(6) NOT NULL DEFAULT 1 COMMENT '解析状态:0-失败,1-成功',
                               `checkFailMsg` varchar(128) DEFAULT NULL COMMENT '解析检查失败的描述信息',
                               `bankErrMsg` varchar(128) DEFAULT NULL COMMENT '银行返回的错误信息',
                               `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `IDX_batchNo` (`batchNo`)
) ENGINE=InnoDB AUTO_INCREMENT=0000000 DEFAULT CHARSET=utf8mb4 COMMENT='对账批次表';

DROP TABLE IF EXISTS t_check_mistake;
CREATE TABLE `t_check_mistake` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `batchNo` varchar(32) NOT NULL COMMENT '对账批次号',
                                 `billDate` date DEFAULT NULL COMMENT '账单时间(账单交易发生时间)',
                                 `billType` tinyint(6) DEFAULT NULL COMMENT '账单类型(默认全部是交易成功)',
                                 `bankType` varchar(30) DEFAULT NULL COMMENT '银行类型,wxpay:微信,alipay:支付宝',
                                 `channelMchId` varchar(32) DEFAULT NULL COMMENT '渠道商户ID',
                                 `orderTime` datetime DEFAULT NULL COMMENT '下单时间',
                                 `mchId` bigint(20) DEFAULT NULL COMMENT '商户ID',
                                 `mchName` varchar(30) DEFAULT NULL DEFAULT '' COMMENT '商户名称',
                                 `mchOrderNo` varchar(30) DEFAULT NULL COMMENT '商户订单号',
                                 `tradeTime` datetime DEFAULT NULL COMMENT '平台交易时间',
                                 `orderId` varchar(24) DEFAULT NULL COMMENT '平台订单ID',
                                 `orderAmount` bigint(20) DEFAULT NULL COMMENT '平台交易金额',
                                 `refundAmount` bigint(20) DEFAULT NULL COMMENT '平台退款金额',
                                 `orderStatus` tinyint(6) DEFAULT NULL DEFAULT 0 COMMENT '平台订单状态',
                                 `fee` bigint(20) DEFAULT NULL COMMENT '平台手续费',
                                 `bankTradeTime` datetime DEFAULT NULL COMMENT '银行交易时间',
                                 `bankOrderNo` varchar(40) DEFAULT NULL COMMENT '银行订单号',
                                 `bankOrderStatus` varchar(30) DEFAULT NULL DEFAULT 0 COMMENT '银行订单状态',
                                 `bankAmount` bigint(20) DEFAULT NULL COMMENT '银行交易金额',
                                 `bankRefundAmount` bigint(20) DEFAULT NULL COMMENT '银行退款金额',
                                 `bankFee` bigint(20) DEFAULT NULL COMMENT '银行手续费',
                                 `errType` tinyint(6) DEFAULT NULL COMMENT '差错类型:1',
                                 `handleStatus` tinyint(6) DEFAULT NULL DEFAULT 0 COMMENT '类型:0-未处理,1-已处理',
                                 `handleValue` varchar(30) DEFAULT NULL COMMENT '处理结果',
                                 `handleRemark` varchar(128) DEFAULT NULL COMMENT '处理备注',
                                 `operatorName` varchar(30) DEFAULT NULL COMMENT '操作人姓名',
                                 `operatorUserId` varchar(30) DEFAULT NULL COMMENT '操作人用户ID',
                                 `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='对账差错表';

DROP TABLE IF EXISTS t_check_mistake_pool;
CREATE TABLE `t_check_mistake_pool` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                      `productame` varchar(50) DEFAULT NULL COMMENT '商品名称',
                                      `mchOrderNo` varchar(30) NOT NULL COMMENT '商户订单号',
                                      `orderId` varchar(24) NOT NULL COMMENT '平台订单ID',
                                      `bankOrderNo` varchar(40) DEFAULT NULL COMMENT '银行订单号',
                                      `orderAmount` bigint(20) DEFAULT NULL COMMENT '订单金额',
                                      `platIncome` bigint(20) DEFAULT NULL COMMENT '平台收入',
                                      `feeate` decimal(20,6) DEFAULT NULL COMMENT '平台费率',
                                      `platCost` bigint(20) DEFAULT NULL COMMENT '平台成本',
                                      `platProfit` bigint(20) DEFAULT NULL COMMENT '平台利润',
                                      `status` tinyint(6) DEFAULT NULL DEFAULT 0 COMMENT '状态',
                                      `channelId` varchar(24) DEFAULT NULL COMMENT '渠道ID',
                                      `channelType` varchar(30) DEFAULT NULL COMMENT '渠道类型,WX:微信,ALIPAY:支付宝',
                                      `paySuccessTime` datetime DEFAULT NULL COMMENT '支付成功时间',
                                      `completeTime` datetime DEFAULT NULL COMMENT '完成时间',
                                      `isRefund` tinyint(6) NOT NULL DEFAULT 0 COMMENT '类型:0-否,1-是',
                                      `refundTimes` int DEFAULT NULL COMMENT '退款次数',
                                      `successRefundAmount` bigint(20) DEFAULT NULL COMMENT '成功退款总金额',
                                      `remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                      `batchNo` varchar(32) NOT NULL COMMENT '对账批次号',
                                      `billDate` date DEFAULT NULL COMMENT '账单时间(账单交易发生时间)',
                                      `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='对账差错缓冲池表';

DROP TABLE IF EXISTS t_mch_bill;
CREATE TABLE `t_mch_bill` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `mchId` bigint(20) NOT NULL COMMENT '商户ID',
                            `mchName` varchar(30) NOT NULL COMMENT '名称',
                            `mchType` tinyint(6) NOT NULL COMMENT '类型:1-平台账户,2-私有账户',
                            `billDate` date DEFAULT NULL COMMENT '账单时间',
                            `Status` tinyint(6) NOT NULL DEFAULT 0 COMMENT '账单状态:0-未生成,1--已生成',
                            `billPath` varchar(128) DEFAULT NULL COMMENT '账单地址',
                            `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `IDX_MchId_BillDate` (`mchId`, billDate)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户对账单';

### 用户账户表
DROP TABLE IF EXISTS t_user_account;
CREATE TABLE `t_user_account` (
                                `UserId` varchar(64) NOT NULL COMMENT '用户ID',
                                `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                `Balance` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '账户余额',
                                `CheckSum` varchar(32) NOT NULL COMMENT '用户账户校验码',
                                `UpdateTimeJava` bigint(20) NOT NULL COMMENT '账户更新时间',
                                `TotalRollIn` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户累计转入金额',
                                `TotalRollOut` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户累计转出金额',
                                `UseableBalance` bigint(20) NOT NULL DEFAULT '0' COMMENT '账户可用余额',
                                `UseableCheckSum` varchar(32) NOT NULL COMMENT '用户可用账户校验码',
                                `UseableUpdateTimeJava` bigint(20) NOT NULL COMMENT '可用账户更新时间',
                                `UseableRollIn` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户累计转入可用金额',
                                `UseableRollOut` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户累计转出可用金额',
                                `State` smallint(6) NOT NULL DEFAULT '1' COMMENT '状态.0表示账户冻结.1表示正常',
                                `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`UserId`,`MchId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账户表';

DROP TABLE IF EXISTS t_user_account_log;
CREATE TABLE `t_user_account_log` (
                                    `LogId` bigint(20) NOT NULL AUTO_INCREMENT,
                                    `UserId` varchar(64) NOT NULL COMMENT '用户ID',
                                    `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                    `ChangeAmount` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '变动金额',
                                    `Type` smallint(6) NOT NULL DEFAULT '1' COMMENT '账户变动类型.0表示转入.1表示转出.2表示初始化',
                                    `AccountType` smallint(6) NOT NULL DEFAULT '1' COMMENT '账户类型 0:总账户 1:可用账户',
                                    `OldBalance` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '变动前账户余额',
                                    `OldUpdateTimeJava` bigint(20) NOT NULL COMMENT '变动前账户更新时间',
                                    `OldCheckSum` varchar(32) NOT NULL COMMENT '变动前用户账户校验码',
                                    `NewBalance` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '变动后账户余额',
                                    `NewUpdateTimeJava` bigint(20) NOT NULL COMMENT '变动后账户更新时间',
                                    `NewCheckSum` varchar(32) NOT NULL COMMENT '变动后用户账户校验码',
                                    `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`LogId`),
                                    KEY `IDX_UserId` (`UserId`),
                                    KEY `IDX_UserId_MchId` (`UserId`,`MchId`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb4 COMMENT='用户账户日志表';

DROP TABLE IF EXISTS t_user_account_change_detail;
CREATE TABLE `t_user_account_change_detail` (
                                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                              `UserId` varchar(64) NOT NULL COMMENT '用户ID',
                                              `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                              `ChangeDay` int(11) NOT NULL COMMENT '收入日期，格式：yyyymmdd',
                                              `ChangeType` smallint(6) NOT NULL DEFAULT '0' COMMENT '账户变动类型.0表示转入.1表示转出',
                                              `AccountType` smallint(6) NOT NULL DEFAULT '1' COMMENT '变动账户类型 0:账户 1:可用账户',
                                              `ChangeAmount` bigint(20) NOT NULL COMMENT '账户变动金额',
                                              `ChangeLogId` bigint(20) DEFAULT NULL COMMENT '变动日志记录ID',
                                              `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                              `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                              PRIMARY KEY (`id`),
                                              KEY `IDX_UserId_MchId` (`UserId`,`MchId`),
                                              KEY `IDX_UserId_MchId_Day` (`UserId`,`MchId`,`ChangeDay`),
                                              KEY `IDX_UserId_MchId_Type` (`UserId`,`MchId`,`ChangeType`),
                                              KEY `IDX_UserId_MchId_Day_Type` (`UserId`,`MchId`,`ChangeDay`,`ChangeType`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COMMENT='用户账户变动记录表';

### 代理商相关表
DROP TABLE IF EXISTS t_agent_info;
CREATE TABLE `t_agent_info` (
                              `AgentId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '代理商ID',
                              `ParentAgentId` bigint(20) DEFAULT 0 COMMENT '一级代理ID',
                              `AgentName` varchar(30) NOT NULL COMMENT '代理商名称',
                              `userName` varchar(30) NOT NULL COMMENT '用户登录名',
                              `realName` varchar(30) NOT NULL COMMENT '用户真实姓名',
                              `Password` varchar(128) DEFAULT NULL COMMENT '密码',
                              `PayPassword` varchar(32) DEFAULT NULL COMMENT '支付密码',
                              `IdCard` varchar(32) DEFAULT NULL COMMENT '身份证号',
                              `Mobile` bigint(20) DEFAULT NULL COMMENT '手机号',
                              `Tel` varchar(32) DEFAULT NULL COMMENT '电话号码',
                              `Qq` varchar(32) DEFAULT NULL COMMENT 'QQ号码',
                              `Email` varchar(128) DEFAULT NULL COMMENT '邮箱',
                              `Address` varchar(128) DEFAULT NULL COMMENT '通讯地址',
                              `AccountAttr` tinyint(6) DEFAULT 0 COMMENT '账户属性:0-对私,1-对公,默认对私',
                              `AccountType` tinyint(6) DEFAULT 1 COMMENT '账户类型:1-银行卡转账,2-微信转账,3-支付宝转账',
                              `BankName` varchar(128) DEFAULT NULL COMMENT '开户行名称',
                              `BankNetName` varchar(128) DEFAULT NULL COMMENT '开户网点名称',
                              `AccountName` varchar(64) DEFAULT NULL COMMENT '账户名',
                              `AccountNo` varchar(64) DEFAULT NULL COMMENT '账户号',
                              `Province` varchar(32) DEFAULT NULL COMMENT '开户行所在省',
                              `City` varchar(32) DEFAULT NULL COMMENT '开户行所在市',
                              `AgentLevel` tinyint(6) COMMENT '代理等级',
                              `FeeType` tinyint(6) NOT NULL DEFAULT 2 COMMENT '手续费类型,1-百分比收费,2-固定收费',
                              `FeeRate` decimal(20,6) DEFAULT 0 COMMENT '手续费百分比值',
                              `FeeLevel` varchar(256) DEFAULT 0 COMMENT '手续费等级值,使用json存储每一等级信息',
                              `RiskDay` int DEFAULT 0 COMMENT '风险预存期',
                              `SettConfigMode` tinyint(6) NOT NULL DEFAULT '1' COMMENT '结算配置模式,1-继承系统,2-自定义',
                              `DrawFlag` tinyint(6) NOT NULL DEFAULT '1' COMMENT '提现开关:0-关闭,1-开启',
                              `AllowDrawWeekDay` varchar(64) DEFAULT NULL COMMENT '每周周几允许提现,数字表示,多个逗号分隔',
                              `DrawDayStartTime` varchar(20) DEFAULT NULL COMMENT '每天提现开始时间',
                              `DrawDayEndTime` varchar(20) DEFAULT NULL COMMENT '每天提现结束时间',
                              `DrawMaxDayAmount` bigint(20) DEFAULT NULL COMMENT '每天提现最大金额,单位分',
                              `MaxDrawAmount` bigint(20) DEFAULT NULL COMMENT '最大提现金额,单位分',
                              `MinDrawAmount` bigint(20) DEFAULT NULL COMMENT '最小提现金额,单位分',
                              `DayDrawTimes` int DEFAULT 10 COMMENT '每日提现次数',
                              `SettType` tinyint(6) NOT NULL DEFAULT 0 COMMENT '结算类型,0-手动提现,1-自动结算',
                              `SettMode` tinyint(6) NOT NULL DEFAULT 2 COMMENT '结算方式,1-D0到账,2-D1到账,3-T0到账,4-T1到账',
                              `Status` tinyint(6) NOT NULL COMMENT '状态:0-停止使用,1-使用中',
                              `LoginSecurityType` tinyint(6) NOT NULL DEFAULT 1 COMMENT '登录安全类型,1-仅登录密码验证,2-登录密码+谷歌组合验证',
                              `PaySecurityType` tinyint(6) NOT NULL DEFAULT 1 COMMENT '支付安全类型,0-无需验证,1-仅支付密码验证,2-仅谷歌验证,3-支付密码+谷歌组合验证',
                              `GoogleAuthStatus` tinyint(6) NOT NULL DEFAULT 0 COMMENT '绑定谷歌验证状态,0-未绑定,1-已绑定',
                              `GoogleAuthSecretKey` varchar(32) DEFAULT NULL COMMENT '谷歌验证密钥',
                              `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                              `DrawFeeLimit` bigint(20) DEFAULT NULL COMMENT '每笔提现手续费上限,单位分',
                              `LastLoginIp` varchar(128) DEFAULT NULL COMMENT '最后一次登录IP',
                              `LastLoginTime` datetime DEFAULT NULL COMMENT '最后一次登录时间',
                              `LastPasswordResetTime` datetime DEFAULT NULL COMMENT '最后一次重置密码时间',
                              `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `offRechargeRate` decimal(20,6) DEFAULT 0 COMMENT '线下充值费率,百分比',
                              `PrivateKey` varchar(128) DEFAULT '' COMMENT '私钥',
                              PRIMARY KEY (`AgentId`),
                              UNIQUE KEY `IDX_UserName` (`userName`),
                              UNIQUE KEY `IDX_Mobile` (`Mobile`),
                              UNIQUE KEY `IDX_Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=10000000 DEFAULT CHARSET=utf8mb4 COMMENT='代理商信息表';


DROP TABLE IF EXISTS t_agent_account;
CREATE TABLE `t_agent_account` (
                                 `AgentId` bigint(20) NOT NULL COMMENT '代理商ID',
                                 `Balance` bigint(20) NOT NULL COMMENT '账户余额',
                                 `UnBalance` bigint(20) NOT NULL COMMENT '不可用余额',
                                 `SecurityMoney` bigint(20) NOT NULL COMMENT '保证金',
                                 `TotalIncome` bigint(20) NOT NULL COMMENT '总收益',
                                 `TotalExpend` bigint(20) NOT NULL COMMENT '总支出',
                                 `TodayIncome` bigint(20) NOT NULL COMMENT '今日收益',
                                 `TodayExpend` bigint(20) NOT NULL COMMENT '今日支出',
                                 `SettAmount` bigint(20) NOT NULL COMMENT '可结算金额',
                                 `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '账户状态,1-可用,0-停止使用',
                                 `AccountUpdateTime` datetime DEFAULT NULL COMMENT '账户修改时间',
                                 `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`AgentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代理商账户表';

DROP TABLE IF EXISTS t_agent_account_history;
CREATE TABLE `t_agent_account_history` (
                                         `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                         `AgentId` bigint(20) NOT NULL COMMENT '代理商ID',
                                         `Amount` bigint(20) NOT NULL COMMENT '变动金额',
                                         `Balance` bigint(20) NOT NULL COMMENT '账户余额',
                                         `AfterBalance` bigint(20) NOT NULL COMMENT '变更后账户余额',
                                         `FundDirection` tinyint(6) NOT NULL COMMENT '资金变动方向,1-加款,2-减款',
                                         `BizType` tinyint(6) NOT NULL DEFAULT '1' COMMENT '业务类型,1-分润,2-提现,3-调账',
                                         `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         `BizItem` varchar(2) NOT NULL DEFAULT '' COMMENT '业务类目:10-余额,11-代付余额,12-冻结金额,13-保证金,20-支付,21-代付,22-线下充值,23-线上充值',
                                         `OrderId` varchar(64) DEFAULT NULL COMMENT '平台订单号',
                                         PRIMARY KEY (`Id`),
                                         UNIQUE KEY `IDX_AgentId_OrderId` (AgentId, OrderId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代理商资金账户流水表';

DROP TABLE IF EXISTS t_agent_sett_daily_collect;
CREATE TABLE `t_agent_sett_daily_collect` (
                                            `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                            `AgentId` bigint(20) NOT NULL COMMENT '代理商ID',
                                            `CollectDate` date NOT NULL COMMENT '汇总日期',
                                            `CollectType` tinyint(6) NOT NULL COMMENT '汇总类型:1-存入/减少汇总,2-临时汇总,3-遗留汇总',
                                            `TotalAmount` bigint(20) NOT NULL COMMENT '交易金额',
                                            `TotalCount` INT NOT NULL COMMENT '交易总笔数',
                                            `TotalAgentProfit` bigint(20) DEFAULT 0 COMMENT '代理商利润,单位分',
                                            `SettStatus` tinyint(6) NOT NULL COMMENT '结算状态,0-未结算,1-已结算',
                                            `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                            `RiskDay` int NOT NULL COMMENT '风险预存期天数',
                                            `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                            `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                            `CollectItem` varchar(2) NOT NULL DEFAULT '' COMMENT '结算类目:20-支付,21-代付,22-线下充值,23-线上充值',
                                            PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代理商每日结算汇总';

### 支付通道重构相关表
DROP TABLE IF EXISTS t_pay_type;
CREATE TABLE `t_pay_type` (
                            `payTypeCode` varchar(32) NOT NULL COMMENT '支付类型编码',
                            `payTypeName` varchar(64) NOT NULL COMMENT '支付类型名称',
                            `Type` varchar(2) NOT NULL DEFAULT '1' COMMENT '类型,1:支付,2:代付',
                            `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '状态,0-关闭,1-开启',
                            `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`payTypeCode`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='支付类型表';

DROP TABLE IF EXISTS t_pay_interface_type;
CREATE TABLE `t_pay_interface_type` (
                                      `IfTypeCode` varchar(30) NOT NULL COMMENT '接口类型代码',
                                      `IfTypeName` varchar(30) NOT NULL COMMENT '接口类型名称',
                                      `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '状态,0-关闭,1-开启',
                                      `Param` varchar(4096) NOT NULL COMMENT '接口配置定义描述,json字符串',
                                      `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                      `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`IfTypeCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付接口类型表';

DROP TABLE IF EXISTS t_pay_interface;
CREATE TABLE `t_pay_interface` (
                                 `IfCode` varchar(30) NOT NULL COMMENT '接口代码',
                                 `IfName` varchar(30) NOT NULL COMMENT '接口名称',
                                 `IfTypeCode` varchar(30) NOT NULL COMMENT '接口类型代码',
                                 `PayType` varchar(2) NOT NULL COMMENT '支付类型',
                                 `Scene` tinyint(6) DEFAULT NULL COMMENT '应用场景,1:移动APP,2:移动网页,3:PC网页,4:微信公众平台,5:手机扫码',
                                 `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '接口状态,0-关闭,1-开启',
                                 `Param` varchar(4096) DEFAULT NULL COMMENT '配置参数,json字符串',
                                 `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                 `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `Extra` varchar(1024) DEFAULT NULL COMMENT '扩展参数',
                                 PRIMARY KEY (`IfCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付接口表';

DROP TABLE IF EXISTS t_pay_passage;
CREATE TABLE `t_pay_passage` (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '支付通道ID',
                               `PassageName` varchar(30) NOT NULL COMMENT '通道名称',
                               `IfCode` varchar(30) NOT NULL COMMENT '接口代码',
                               `IfTypeCode` varchar(30) NOT NULL COMMENT '接口类型代码',
                               `PayType` varchar(2) NOT NULL COMMENT '支付类型',
                               `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '通道状态,0-关闭,1-开启',
                               `PassageRate` decimal(20,6) DEFAULT NULL COMMENT '通道费率百分比',
                               `MaxDayAmount` bigint(20) DEFAULT NULL COMMENT '当天交易金额,单位分',
                               `MaxEveryAmount` bigint(20) DEFAULT NULL COMMENT '单笔最大金额,单位分',
                               `MinEveryAmount` bigint(20) DEFAULT NULL COMMENT '单笔最小金额,单位分',
                               `TradeStartTime` varchar(20) DEFAULT NULL COMMENT '交易开始时间',
                               `TradeEndTime` varchar(20) DEFAULT NULL COMMENT '交易结束时间',
                               `RiskStatus` tinyint(6) NOT NULL DEFAULT '0' COMMENT '风控状态,0-关闭,1-开启',
                               `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                               `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='支付通道表';

DROP TABLE IF EXISTS t_pay_passage_account;
CREATE TABLE `t_pay_passage_account` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户ID',
                                       `AccountName` varchar(30) NOT NULL COMMENT '账户名称',
                                       `PayPassageId` int(11) NOT NULL COMMENT '支付通道ID',
                                       `IfCode` varchar(30) NOT NULL COMMENT '接口代码',
                                       `IfTypeCode` varchar(30) NOT NULL COMMENT '接口类型代码',
                                       `Param` varchar(4096) NOT NULL COMMENT '账户配置参数,json字符串',
                                       `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '账户状态,0-停止,1-开启',
                                       `PollWeight` INT NOT NULL DEFAULT '1' COMMENT '轮询权重',
                                       `PassageMchId` varchar(64) NOT NULL COMMENT '通道商户ID',
                                       `PassageRate` decimal(20,6) DEFAULT NULL COMMENT '通道费率百分比',
                                       `RiskMode` tinyint(6) NOT NULL DEFAULT '1' COMMENT '风控模式,1-继承,2-自定义',
                                       `MaxDayAmount` bigint(20) DEFAULT NULL COMMENT '当天交易金额,单位分',
                                       `MaxEveryAmount` bigint(20) DEFAULT NULL COMMENT '单笔最大金额,单位分',
                                       `MinEveryAmount` bigint(20) DEFAULT NULL COMMENT '单笔最小金额,单位分',
                                       `TradeStartTime` varchar(20) DEFAULT NULL COMMENT '交易开始时间',
                                       `TradeEndTime` varchar(20) DEFAULT NULL COMMENT '交易结束时间',
                                       `RiskStatus` tinyint(6) NOT NULL DEFAULT '0' COMMENT '风控状态,0-关闭,1-开启',
                                       `CashCollStatus` tinyint(6) NOT NULL DEFAULT '0' COMMENT '资金归集开关,0-关闭,1-开启',
                                       `CashCollMode` tinyint(6) NOT NULL DEFAULT '1' COMMENT '资金归集配置,1-继承全局配置,2-自定义',
                                       `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                       `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       PRIMARY KEY (`ID`),
                                       UNIQUE KEY `IDX_PayPassageId_PassageMchId` (`PayPassageId`, `PassageMchId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000100 DEFAULT CHARSET=utf8mb4 COMMENT='支付通道账户表';

DROP TABLE IF EXISTS t_pay_product;
CREATE TABLE `t_pay_product` (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品ID',
                               `ProductName` varchar(64) NOT NULL COMMENT '产品名称',
                               `PayType` varchar(2) NOT NULL COMMENT '支付类型',
                               `AgentRate` decimal(20,6) DEFAULT NULL COMMENT '代理商费率,百分比',
                               `MchRate` decimal(20,6) DEFAULT NULL COMMENT '商户费率,百分比',
                               `IfMode` tinyint(6) NOT NULL DEFAULT 1 COMMENT '接口模式,1-单独,2-轮询',
                               `PayPassageId` int(11) DEFAULT NULL COMMENT '支付通道ID',
                               `PayPassageAccountId` int(11) DEFAULT NULL COMMENT '支付通道账户ID',
                               `PollParam` varchar(4096) DEFAULT NULL COMMENT '轮询配置参数,json字符串',
                               `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '状态,0-关闭,1-开启',
                               `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `ProductType` tinyint(6) DEFAULT 1 COMMENT '产品类型:1-收款,2-充值',
                               PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8000 DEFAULT CHARSET=utf8mb4 COMMENT='支付产品表';

DROP TABLE IF EXISTS t_mch_pay_passage;
CREATE TABLE `t_mch_pay_passage` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                   `ProductId` int(11) NOT NULL COMMENT '产品ID',
                                   `MchRate` decimal(20,6) DEFAULT NULL COMMENT '商户费率,百分比',
                                   `Status` tinyint(6) NOT NULL DEFAULT 1 COMMENT '状态,0-关闭,1-开启',
                                   `IfMode` tinyint(6) NOT NULL DEFAULT 1 COMMENT '接口模式,1-单独,2-轮询',
                                   `PayPassageId` int(11) DEFAULT NULL COMMENT '支付通道ID',
                                   `PayPassageAccountId` int(11) DEFAULT NULL COMMENT '支付通道账户ID',
                                   `PollParam` varchar(4096) DEFAULT NULL COMMENT '轮询配置参数,json字符串',
                                   `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `ProductType` tinyint(6) DEFAULT 1 COMMENT '产品类型:1-收款,2-充值',
                                   PRIMARY KEY (`ID`),
                                   UNIQUE KEY `IDX_MchId_ProductId` (`MchId`, `ProductId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户支付通道表';

DROP TABLE IF EXISTS t_agentpay_passage;
CREATE TABLE `t_agentpay_passage` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代付通道ID',
                                    `PassageName` varchar(30) NOT NULL COMMENT '通道名称',
                                    `IfCode` varchar(30) NOT NULL COMMENT '接口代码',
                                    `IfTypeCode` varchar(30) NOT NULL COMMENT '接口类型代码',
                                    `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '通道状态,0-关闭,1-开启',
                                    `FeeType` tinyint(6) NOT NULL DEFAULT 1 COMMENT '手续费类型,1-百分比收费,2-固定收费',
                                    `FeeRate` decimal(20,6) DEFAULT NULL COMMENT '手续费费率,百分比',
                                    `FeeEvery` bigint(20) DEFAULT NULL COMMENT '手续费每笔金额,单位分',
                                    `MaxDayAmount` bigint(20) DEFAULT NULL COMMENT '当天交易金额,单位分',
                                    `TradeStartTime` varchar(20) DEFAULT NULL COMMENT '交易开始时间',
                                    `TradeEndTime` varchar(20) DEFAULT NULL COMMENT '交易结束时间',
                                    `RiskStatus` tinyint(6) NOT NULL DEFAULT '0' COMMENT '风控状态,0-关闭,1-开启',
                                    `IsDefault` tinyint(6) NOT NULL DEFAULT '0' COMMENT '是否默认,0-否,1-是',
                                    `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                    `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8mb4 COMMENT='代付通道表';

DROP TABLE IF EXISTS t_agentpay_passage_account;
CREATE TABLE `t_agentpay_passage_account` (
                                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户ID',
                                            `AccountName` varchar(30) NOT NULL COMMENT '账户名称',
                                            `AgentpayPassageId` int(11) NOT NULL COMMENT '代付通道ID',
                                            `IfCode` varchar(30) NOT NULL COMMENT '接口代码',
                                            `IfTypeCode` varchar(30) NOT NULL COMMENT '接口类型代码',
                                            `Param` varchar(4096) NOT NULL COMMENT '账户配置参数,json字符串',
                                            `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '账户状态,0-停止,1-开启',
                                            `PollWeight` INT NOT NULL DEFAULT '1' COMMENT '轮询权重',
                                            `PassageMchId` varchar(64) NOT NULL COMMENT '通道商户ID',
                                            `RiskMode` tinyint(6) NOT NULL DEFAULT '1' COMMENT '风控模式,1-继承,2-自定义',
                                            `MaxDayAmount` bigint(20) DEFAULT NULL COMMENT '当天交易金额,单位分',
                                            `TradeStartTime` varchar(20) DEFAULT NULL COMMENT '交易开始时间',
                                            `TradeEndTime` varchar(20) DEFAULT NULL COMMENT '交易结束时间',
                                            `RiskStatus` tinyint(6) NOT NULL DEFAULT '0' COMMENT '风控状态,0-关闭,1-开启',
                                            `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                                            `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                            `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                            PRIMARY KEY (`ID`),
                                            UNIQUE KEY `IDX_AgentpayPassageId_PassageMchId` (`AgentpayPassageId`, `PassageMchId`)
) ENGINE=InnoDB AUTO_INCREMENT=2000100 DEFAULT CHARSET=utf8mb4 COMMENT='代付通道账户表';

DROP TABLE IF EXISTS t_mch_agentpay_passage;
CREATE TABLE `t_mch_agentpay_passage` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                        `MchId` bigint(20) NOT NULL COMMENT '商户ID',
                                        `AgentpayPassageId` int(11) DEFAULT NULL COMMENT '代付通道ID',
                                        `AgentpayPassageAccountId` int(11) DEFAULT NULL COMMENT '代付通道账户ID',
                                        `MaxEveryAmount` bigint(20) DEFAULT NULL COMMENT '单笔代付转出最大金额,单位分',
                                        `MchFeeType` tinyint(6) NOT NULL DEFAULT 2 COMMENT '手续费类型,1-百分比收费,2-固定收费',
                                        `MchFeeRate` decimal(20,6) DEFAULT NULL COMMENT '手续费费率,百分比',
                                        `MchFeeEvery` bigint(20) DEFAULT NULL COMMENT '手续费每笔金额,单位分',
                                        `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '状态,0-关闭,1-开启',
                                        `IsDefault` tinyint(6) NOT NULL DEFAULT '0' COMMENT '是否默认,0-否,1-是',
                                        `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        PRIMARY KEY (`ID`),
                                        UNIQUE KEY `IDX_MchId_AgentpayPassageId` (`MchId`, `AgentpayPassageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户代付通道表';

DROP TABLE IF EXISTS t_agnet_passage;
CREATE TABLE `t_agnet_passage` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `AgentId` bigint(20) NOT NULL COMMENT '代理商ID',
                                 `ProductId` int(11) NOT NULL COMMENT '产品ID',
                                 `AgentRate` decimal(20,6) DEFAULT NULL COMMENT '代理商费率,百分比',
                                 `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '状态,0-关闭,1-开启',
                                 `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`ID`),
                                 UNIQUE KEY `IDX_AgentId_ProductId` (`AgentId`, `ProductId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代理商通道表';

DROP TABLE IF EXISTS t_agent_agentpay_passage;
CREATE TABLE `t_agent_agentpay_passage` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                          `AgentId` bigint(20) NOT NULL COMMENT '代理商ID',
                                          `AgentpayPassageId` int(11) DEFAULT NULL COMMENT '代付通道ID',
                                          `FeeEvery` bigint(20) DEFAULT NULL COMMENT '手续费每笔金额,单位分',
                                          `Status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '状态,0-关闭,1-开启',
                                          `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `IDX_AgentId_AgentpayPassageId` (`AgentId`,`AgentpayPassageId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='代理商代付通道表';

### 结算记录表
DROP TABLE IF EXISTS t_sett_record;
CREATE TABLE `t_sett_record` (
                               `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                               `InfoId` bigint(20) NOT NULL COMMENT '代理商或商户ID',
                               `InfoType` tinyint(6) NOT NULL COMMENT '结算商类型:1-代理商,2-商户',
                               `SettType` tinyint(6) NOT NULL COMMENT '结算发起类型:1-手工结算,2-自动结算',
                               `SettDate` date NOT NULL COMMENT '结算日期',
                               `SettAmount` bigint(20) NOT NULL COMMENT '申请结算金额',
                               `SettFee` bigint(20) NOT NULL COMMENT '结算手续费',
                               `RemitAmount` bigint(20) NOT NULL COMMENT '结算打款金额',
                               `AccountAttr` tinyint(6) DEFAULT 0 COMMENT '账户属性:0-对私,1-对公,默认对私',
                               `AccountType` tinyint(6) DEFAULT 1 COMMENT '账户类型:1-银行卡转账,2-微信转账,3-支付宝转账',
                               `BankName` varchar(128) DEFAULT NULL COMMENT '开户行名称',
                               `BankNetName` varchar(128) DEFAULT NULL COMMENT '开户网点名称',
                               `AccountName` varchar(64) DEFAULT NULL COMMENT '账户名',
                               `AccountNo` varchar(64) DEFAULT NULL COMMENT '账户号',
                               `Province` varchar(32) DEFAULT NULL COMMENT '开户行所在省',
                               `City` varchar(32) DEFAULT NULL COMMENT '开户行所在市',
                               `SettStatus` tinyint(6) NOT NULL COMMENT '结算状态:1-等待审核,2-已审核,3-审核不通过,4-打款中,5-打款成功,6-打款失败',
                               `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                               `RemitRemark` varchar(128) DEFAULT NULL COMMENT '打款备注',
                               `OperatorId` bigint(20) DEFAULT NULL COMMENT '操作用户ID',
                               `SettOrderId` varchar(30) DEFAULT NULL COMMENT '结算单号',
                               `TransOrderId` varchar(30) DEFAULT NULL COMMENT '转账订单号',
                               `TransMsg` varchar(128) DEFAULT NULL COMMENT '转账返回消息',
                               `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`Id`),
                               UNIQUE KEY `IDX_SettOrderId` (`SettOrderId`),
                               UNIQUE KEY `IDX_TransOrderId` (`TransOrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='结算记录表';

### 银行卡Bin信息
DROP TABLE IF EXISTS t_bank_card_bin;
CREATE TABLE `t_bank_card_bin` (
                                 `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `IfTypeCode` varchar(30) DEFAULT NULL COMMENT '通道类型代码',
                                 `CardBin` varchar(8) NOT NULL COMMENT '卡bin',
                                 `BankName` varchar(50) DEFAULT NULL COMMENT '银行名称',
                                 `BankCode` varchar(10) DEFAULT NULL COMMENT '银行编码',
                                 `CardName` varchar(50) DEFAULT NULL COMMENT '卡名',
                                 `CardType` varchar(15) DEFAULT NULL COMMENT '银行卡类型',
                                 `CardLength` int(11) DEFAULT NULL COMMENT '卡号长度',
                                 `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`Id`),
                                 UNIQUE KEY `IDX_CardBin` (`CardBin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='银行卡Bin信息';

### 系统配置表
DROP TABLE IF EXISTS t_sys_config;
CREATE TABLE `t_sys_config` (
                              `code` varchar(64) NOT NULL COMMENT '配置主键',
                              `name` varchar(64) NOT NULL COMMENT '配置名称',
                              `value` varchar(1024) NOT NULL COMMENT '配置内容',
                              `remark` varchar(128) NOT NULL COMMENT '配置备注',
                              `type` varchar(128) NOT NULL COMMENT '配置类别',
                              `orderNum` float DEFAULT 1.0 COMMENT '排序值',
                              `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

### 系统操作日志表
DROP TABLE IF EXISTS t_mgr_sys_log;
CREATE TABLE `t_mgr_sys_log` (
                               `Id` INT (11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                               `UserId` BIGINT NOT NULL COMMENT '用户ID',
                               `UserName` VARCHAR (50) NOT NULL COMMENT '用户名',
                               `UserIp` VARCHAR (64) NOT NULL DEFAULT '' COMMENT '用户IP',
                               `System` tinyint(6) NOT NULL COMMENT ' 系统 1：运营平台, 2：商户平台, 3:代理商',
                               `MethodName` VARCHAR (128) NOT NULL DEFAULT '' COMMENT '方法名',
                               `MethodRemark` VARCHAR (128) NOT NULL DEFAULT '' COMMENT '方法描述',
                               `OptReqParam` VARCHAR (2048) NOT NULL DEFAULT '' COMMENT '操作请求参数',
                               `OptResInfo` VARCHAR (2048) NOT NULL DEFAULT '' COMMENT '操作响应结果',
                               `CreateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `UpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '系统操作日志表';

### 资金归集配置表
drop table if exists t_pay_cash_coll_config;
CREATE TABLE `t_pay_cash_coll_config` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                        `TransInUserName` varchar(50) NOT NULL COMMENT '分账收入方姓名',
                                        `TransInUserAccount` varchar(50) NOT NULL COMMENT '分账收入方账号',
                                        `TransInUserId` varchar(50) NOT NULL COMMENT '分账收入方用户ID (支付宝PID)',
                                        `TransInPercentage` decimal(20,2) DEFAULT NULL COMMENT '分账比例,百分比',
                                        `Status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '状态,0-关闭,1-开启',
                                        `BelongPayAccountId` int(11) NOT NULL COMMENT '所属通道子账户ID, 系统全局配置为0 ',
                                        `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        PRIMARY KEY (`id`),
                                        UNIQUE KEY `IDX_Trans_Account_Id` (`TransInUserId`,`BelongPayAccountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付资金归集配置表';

### 订单资金归集记录
drop table if exists t_pay_order_cash_coll_record;
CREATE TABLE `t_pay_order_cash_coll_record` (
                                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                              `PayOrderId` varchar(30) NOT NULL COMMENT '支付订单号',
                                              `ChannelOrderNo` varchar(64) DEFAULT NULL COMMENT '渠道订单号',
                                              `RequestNo` varchar(64) DEFAULT NULL COMMENT '请求序列号（上送渠道序列）',
                                              `TransInUserName` varchar(50) NOT NULL COMMENT '分账收入方姓名快照',
                                              `TransInUserAccount` varchar(50) NOT NULL COMMENT '分账收入方账号快照',
                                              `TransInUserId` varchar(50) NOT NULL COMMENT '分账收入方用户ID 快照(支付宝PID)',
                                              `TransInPercentage` decimal(20,2) DEFAULT NULL COMMENT '分账比例快照,百分比',
                                              `TransInAmount` bigint(20) NOT NULL COMMENT '分账计算金额',
                                              `Status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '状态,0-失败 ,1-成功',
                                              `Remark` varchar(200) COMMENT '信息备注',
                                              `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                              `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                              PRIMARY KEY (`id`),
                                              UNIQUE KEY `IDX_Order_Uid` (`PayOrderId`,`TransInUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单资金归集记录';

### 微信用户表
drop table if exists t_wx_user;
CREATE TABLE `t_wx_user` (
                           `UserId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                           `WxId` varchar(30) DEFAULT '' COMMENT '微信ID',
                           `Account` varchar(30) NOT NULL COMMENT '登录账号',
                           `Password` varchar(128) DEFAULT NULL COMMENT '密码',
                           `NickName` varchar(128) DEFAULT '' COMMENT '昵称',
                           `Settings` TEXT DEFAULT NULL COMMENT '配置信息',
                           `WxDat` MEDIUMTEXT DEFAULT NULL COMMENT '微信数据',
                           `ServerId` varchar(128) NULL NULL COMMENT '服务器ID',
                           `RandomId` varchar(128) NOT NULL DEFAULT ''  COMMENT '随机ID',
                           `DayInAmount` bigint(20) DEFAULT 0 COMMENT '日收款金额,单位分',
                           `DayInNumber` bigint(20) DEFAULT 0 COMMENT '日收款笔数',
                           `Weight` decimal(20,2) DEFAULT 1 COMMENT '权重',
                           `InStatus` tinyint(6) DEFAULT 1 COMMENT '收款状态,0:停止收款,1:可以收款,2:正在收款',
                           `StartPayUser` varchar(512) DEFAULT '' COMMENT '正在付款用户信息',
                           `StartPayTime` datetime DEFAULT NULL COMMENT '开始支付时间',
                           `LoginStatus` int DEFAULT -1 COMMENT '登录状态,与微信服务端一致.-1:未登录,0:等待扫码登录,1:已扫码,未确认,2:已扫码,已确认,等待登录,3:已登录',
                           `LoginResult` varchar(256) DEFAULT NULL COMMENT '登录结果',
                           `LoginSyncTime` datetime DEFAULT NULL COMMENT '登录同步时间',
                           `DayUpdateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '今日数据更新时间',
                           `LastInTime` datetime DEFAULT NULL COMMENT '最后一次收款时间',
                           `Status` tinyint(6) DEFAULT 1 COMMENT '用户状态,0:停止使用,1:可以使用',
                           `Remark` varchar(128) DEFAULT NULL COMMENT '备注',
                           `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `UpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`UserId`),
                           UNIQUE KEY `IDX_Account` (`Account`),
                           UNIQUE KEY `IDX_RandomId` (`RandomId`),
                           UNIQUE KEY `IDX_ServerId_Account` (`ServerId`, `Account`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='微信用户表';

# 初始化系统用户
INSERT INTO t_mgr_sys_user (userName, passWord, nickName, email, mobile, status, createUserId, lastPasswordResetTime, createTime, updateTime, isSuperAdmin) VALUES ('yeepaymgr', '$2a$10$kFafDqYtRcifYbXujyzxnetsKJAQw5oofIYzugwWD34aQmrp3/AN6', '超级管理员', 'yeepay@126.com', '18866666666', 1, null, '2018-01-08 11:38:25', '2018-01-02 22:30:45', '2018-01-08 11:38:25', 1);
# 初始化系统资源
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (10, 'merchant', '商户管理', '', '', '', 1, 1, 'layui-icon-user', 101, null, 1, '', '2018-01-23 21:59:38', '2018-01-23 22:09:17');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (11, 'list', '所有商户', '', 'ROLE_MCH', '/mch_info/**,/agent_info/getParentAgentId', 1, 1, 'layui-icon-user', 102, 10, 1, '', '2018-01-23 22:01:23', '2018-05-31 22:27:09');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (12, 'audit', '待审商户', '', 'ROLE_MCH', '/mch_audit/**', 1, 1, '', 103, 10, 1, '', '2018-01-23 22:02:34', '2018-01-25 13:43:00');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (15, 'channel', '支付渠道', '', 'ROLE_CHANNEL', '/channel/**', 1, 1, '', 43, 41, 0, '', '2018-01-23 22:59:06', '2018-05-22 11:38:51');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (16, 'order', '订单管理', '', '', '', 1, 1, 'layui-icon-rmb', 301, null, 1, '', '2018-01-23 23:00:59', '2018-05-31 22:32:53');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (17, 'pay', '支付订单', '', 'ROLE_PAY', '/pay_order/**', 1, 1, '', 302, 16, 1, '', '2018-01-23 23:02:34', '2018-01-23 23:02:34');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (18, 'trans', '转账订单', '', 'ROLE_TRANS', '/trans_order/**', 1, 1, '', 303, 16, 1, '', '2018-01-23 23:03:25', '2018-01-23 23:03:25');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (19, 'refund', '退款订单', '', 'ROLE_REFUND', '/refund_order/**', 1, 1, '', 304, 16, 0, '', '2018-01-23 23:04:06', '2018-06-10 22:34:14');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (20, 'trade', '交易记录', '', 'ROLE_TRADE', '/trade_order/**', 1, 1, '', 305, 16, 0, '', '2018-01-23 23:04:57', '2018-06-10 22:34:29');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (21, 'notify', '商户通知', '', 'ROLE_NOTIFY', '/mch_notify/**', 1, 1, '', 306, 16, 1, '', '2018-01-23 23:05:54', '2018-01-23 23:05:54');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (22, 'sett', '结算管理', '', '', '', 1, 1, 'layui-icon-diamond', 401, null, 1, '', '2018-01-23 23:07:08', '2018-05-31 22:41:05');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (23, 'audit', '结算审核', '', 'ROLE_SETT', '/sett/**', 1, 1, '', 402, 22, 1, '', '2018-01-23 23:08:15', '2018-01-24 20:32:46');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (24, 'remit', '打款管理', '', 'ROLE_SETT', '/sett/**', 1, 1, '', 403, 22, 1, '', '2018-01-23 23:09:08', '2018-01-23 23:09:08');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (25, 'list', '结算记录', '', 'ROLE_SETT', '/sett/**', 1, 1, '', 404, 22, 1, '', '2018-01-23 23:09:42', '2018-01-23 23:09:42');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (26, 'bank', '银行账号', '', 'ROLE_BANK', '/bank_account/**', 1, 1, '', 105, 10, 1, '', '2018-01-23 23:10:41', '2018-08-08 15:58:45');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (27, 'reconciliation', '对账管理', '', '', '', 1, 1, 'layui-icon-survey', 501, null, 1, '', '2018-01-23 23:12:09', '2018-05-31 22:43:22');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (28, 'batch', '对账批次', '', 'ROLE_BILL_BATCH', '/bill/check_batch/**', 1, 1, '', 502, 27, 0, '', '2018-01-23 23:13:23', '2018-06-10 22:32:22');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (29, 'mistake', '差错处理', '', 'ROLE_BILL_MISTAKE', '/bill/check_mistake/**', 1, 1, '', 503, 27, 0, '', '2018-01-23 23:14:54', '2018-06-10 22:32:35');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (30, 'pool', '缓冲池数据', '', 'ROLE_BILL_POOL', '/bill/check_mistake_pool/**', 1, 1, '', 504, 27, 0, '', '2018-01-23 23:15:42', '2018-06-10 22:32:49');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (31, 'sys', '系统管理', '', '', '', 1, 1, 'layui-icon-set', 601, null, 1, '', '2018-01-23 23:16:41', '2018-01-23 23:16:41');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (32, 'user', '用户管理', '', 'ROLE_SYS_USER', '/sys/user/**', 1, 1, 'layui-icon-user', 602, 31, 1, '', '2018-01-23 23:18:24', '2018-02-10 03:45:20');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (33, 'role', '角色管理', '', 'ROLE_SYS_ROLE', '/sys/role/**', 1, 1, '', 603, 31, 1, '', '2018-01-23 23:18:59', '2018-01-23 23:18:59');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (34, 'resource', '资源管理', '', 'ROLE_SYS_RESOURCE', '/sys/resource/**', 1, 1, '', 604, 31, 1, '', '2018-01-23 23:19:29', '2018-02-27 18:39:37');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (35, 'message', '消息管理', '', 'ROLE_SYS_MESSAGE', '/sys/message/**', 1, 1, '', 605, 31, 1, '', '2018-01-23 23:20:21', '2018-01-23 23:20:21');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (38, 'history', '资金流水', '', 'ROLE_HISTORY', '/account/**', 1, 1, '', 104, 10, 1, '', '2018-01-25 13:44:54', '2018-01-25 13:44:54');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (39, 'mch_bill', '商户对账单', '', 'ROLE_BILL_MCH', '/bill/mch/**', 1, 1, '', 505, 27, 1, '', '2018-02-07 11:13:08', '2018-02-07 11:14:18');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (41, 'config', '支付配置', '', '', '', 1, 1, 'layui-icon-component', 311, null, 1, '', '2018-02-11 10:42:22', '2018-05-31 22:36:17');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (42, 'passage', '通道管理', '', 'ROLE_PAY_CONFIG', '/config/passage/**', 1, 1, '', 312, 41, 0, '1', '2018-02-11 10:43:50', '2018-05-22 11:39:04');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (43, 'account', '商户', '', '', '', 1, 2, 'layui-icon-user', 100, 0, 1, '', '2018-04-24 11:11:46', '2018-04-24 15:40:59');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (44, 'info', '基本信息', '', '', '', 1, 2, '', 110, 43, 1, '', '2018-04-24 11:18:05', '2018-04-24 11:20:28');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (45, 'history', '资金流水', '', '', '', 1, 2, '', 120, 43, 1, '', '2018-04-24 11:20:21', '2018-04-24 11:20:21');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (46, 'app', '应用管理', '', '', '', 1, 2, '', 130, 43, 1, '', '2018-04-24 11:21:33', '2018-04-24 11:21:33');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (49, 'pay_passage', '支付通道', '', '', '', 1, 2, '', 140, 43, 1, '', '2018-04-24 11:23:56', '2018-06-02 00:07:20');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (50, 'uaccount', '用户账户', '', '', '', 1, 2, '', 150, 43, 1, '', '2018-04-24 11:24:20', '2018-04-24 15:38:04');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (51, 'order', '订单', '', '', '', 1, 2, 'layui-icon-rmb', 200, 0, 1, '', '2018-04-24 11:25:41', '2018-06-01 23:48:30');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (52, 'pay', '支付订单', '', '', '', 1, 2, '', 210, 51, 1, '', '2018-04-24 11:26:16', '2018-04-24 11:26:16');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (54, 'refund', '退款订单', '', '', '', 1, 2, '', 230, 51, 0, '', '2018-04-24 11:26:52', '2018-06-10 22:34:06');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (55, 'trade', '交易记录', '', '', '', 1, 2, '', 240, 51, 0, '', '2018-04-24 11:27:09', '2018-06-10 22:34:22');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (56, 'sett', '结算', '', '', '', 1, 2, 'layui-icon-diamond', 300, 0, 1, '', '2018-04-24 11:27:55', '2018-06-01 22:31:54');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (58, 'list', '结算列表', '/sett/list', '', '', 1, 2, '', 311, 56, 1, '', '2018-04-24 11:29:09', '2018-08-08 11:00:49');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (59, 'create', '申请结算', '/sett/apply', '', '', 1, 2, '', 312, 56, 1, '', '2018-04-24 11:29:29', '2018-08-08 11:00:49');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (60, 'agentpay', '代付', '', '', '', 1, 2, 'layui-icon-cart-simple', 350, 0, 1, '', '2018-04-24 11:30:07', '2018-08-08 10:45:36');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (61, 'list', '代付列表', '/agentpay/list', '', '', 1, 2, '', 351, 60, 1, '', '2018-04-24 11:30:32', '2018-08-08 10:50:36');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (62, 'apply', '单笔代付', '/agentpay/apply', '', '', 1, 2, '', 352, 60, 1, '', '2018-04-24 11:30:52', '2018-08-08 10:52:59');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (63, 'batch', '批量代付', '/agentpay/batch', '', '', 1, 2, '', 353, 60, 1, '', '2018-04-24 11:31:37', '2018-08-08 10:52:59');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (64, 'bank', '银行账号', '', '', '', 1, 2, '', 145, 43, 1, '', '2018-04-24 11:32:07', '2018-08-08 11:05:25');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (65, 'reconciliation', '对账', '', '', '', 1, 2, 'layui-icon-survey', 400, 0, 1, '', '2018-04-24 11:32:41', '2018-06-01 23:48:54');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (66, 'down', '下载对账', '', '', '', 1, 2, '', 410, 65, 1, '', '2018-04-24 11:33:00', '2018-04-24 11:33:00');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (67, 'agent', '代理商管理', '', '', '', 1, 1, 'layui-icon-username', 121, 0, 1, '', '2018-04-27 16:08:14', '2018-05-31 22:30:15');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (68, 'list', '所有代理商', '', 'ROLE_AGENT', '/agent_info/**', 1, 1, '', 122, 67, 1, '', '2018-04-27 16:21:50', '2018-04-27 16:24:36');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (69, 'account', '代理商', '', '', '', 1, 3, 'layui-icon-username', 100, 0, 1, '', '2018-04-29 11:10:17', '2018-06-01 22:30:10');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (70, 'info', '基本信息', '', '', '', 1, 3, '', 110, 69, 1, '', '2018-04-29 11:14:48', '2018-04-29 11:14:48');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (71, 'rules', '代理守则', '', '', '', 1, 3, '', 120, 69, 0, '', '2018-04-29 11:18:56', '2018-05-09 10:31:34');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (72, 'register', '下线注册地址', '', '', '', 1, 3, '', 130, 69, 0, '', '2018-04-29 11:23:26', '2018-05-09 10:31:19');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (75, 'biz', '业务管理', '', '', '', 1, 3, 'layui-icon-app', 300, 0, 1, '', '2018-04-29 11:31:30', '2018-06-01 22:33:47');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (76, 'payorder', '商户订单', '', '', '', 1, 3, '', 310, 75, 1, '', '2018-04-29 11:32:38', '2018-04-29 11:32:38');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (78, 'recharge', '充值统计', '', '', '', 1, 3, '', 330, 75, 1, '', '2018-04-29 11:33:48', '2018-06-01 16:17:59');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (79, 'sett', '代理结算', '', '', '', 1, 3, 'layui-icon-diamond', 400, 0, 1, '', '2018-04-29 11:34:19', '2018-06-01 22:31:25');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (80, 'list', '结算记录', '', '', '', 1, 3, '', 410, 79, 1, '', '2018-04-29 11:34:51', '2018-04-29 11:34:51');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (81, 'apply', '申请结算', '', '', '', 1, 3, '', 420, 79, 1, '', '2018-04-29 11:35:16', '2018-04-29 11:35:16');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (82, 'pay_interface_type', '支付接口类型', '', 'ROLE_PAY_INTERFACE_TYPE', '/config/pay_interface_type/**', 1, 1, '', 313, 41, 1, '', '2018-05-03 20:12:50', '2018-05-03 20:12:50');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (83, 'pay_interface', '支付接口', '', 'ROLE_PAY_INTERFACE', '/config/pay_interface/**', 1, 1, '', 314, 41, 1, '', '2018-05-03 20:55:00', '2018-05-03 20:55:00');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (84, 'pay_passage', '支付通道', '', 'ROLE_PAY_PASSAGE', '/config/pay_passage/**', 1, 1, '', 315, 41, 1, '', '2018-05-04 10:15:24', '2018-05-04 10:15:24');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (85, 'pay_product', '支付产品', '', 'ROLE_PAY_PRODUCT', '/config/pay_product/**', 1, 1, '', 316, 41, 1, '', '2018-05-04 23:35:32', '2018-05-04 23:35:32');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (87, 'history', '资金流水', '', '', '', 1, 3, '', 111, 69, 1, '', '2018-05-09 10:09:11', '2018-05-09 10:09:11');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (88, 'passage', '支付通道', '', '', '', 1, 3, '', 112, 69, 1, '', '2018-05-09 17:30:06', '2018-05-09 17:30:06');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (93, 'agentpay', '代付管理', '', '', '', 1, 1, 'layui-icon-auz', 450, 0, 1, '', '2018-05-31 17:30:58', '2018-05-31 22:42:23');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (94, 'list', '代付订单', '', 'ROLE_AGENTPAY', '/agentpay/**', 1, 1, '', 451, 93, 1, '', '2018-05-31 17:32:20', '2018-05-31 23:01:35');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (95, 'app', '应用管理', '', 'ROLE_APP', '/app/**', 1, 1, '', 104, 10, 1, '', '2018-05-31 22:52:30', '2018-08-08 15:59:49');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (96, 'qrcode', '一码付管理', '', 'ROLE_QRCODE', '/mch_qrcode/**', 1, 1, '', 106, 10, 1, '', '2018-05-31 22:54:44', '2018-05-31 22:54:44');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (97, 'history', '资金流水', '', 'ROLE_AGENT_HISTORY', '/agent_account/**', 1, 1, '', 123, 67, 1, '', '2018-05-31 22:58:23', '2018-05-31 23:01:09');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (98, 'agentpay_passage', '代付通道', '', 'ROLE_AGENTPAY_PASSAGE', '/config/agentpay_passage/**', 1, 1, '', 452, 93, 1, '', '2018-05-31 23:00:58', '2018-05-31 23:00:58');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (99, 'data', '数据分析', '', '', '', 1, 1, 'layui-icon-console', 480, 0, 1, '', '2018-05-31 23:33:15', '2018-05-31 23:34:13');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (100, 'mch_top', '商户充值排行', '', 'ROLE_DATA_MCH_TOP', '/data/**', 1, 1, '', 482, 99, 1, '', '2018-05-31 23:39:34', '2018-05-31 23:43:34');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (101, 'agent_top', '代理商分润排行', '', 'ROLE_DATA_AGENT_TOP', '/data/**', 1, 1, '', 484, 99, 1, '', '2018-05-31 23:40:25', '2018-05-31 23:43:25');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (102, 'product', '支付产品统计', '', 'ROLE_DATA_PRODUCT', '/data/**', 1, 1, '', 486, 99, 1, '', '2018-05-31 23:42:39', '2018-05-31 23:43:15');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (103, 'merchant', '下级商户', '', '', '', 1, 3, '', 305, 75, 1, '', '2018-06-01 16:09:01', '2018-06-01 16:09:01');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (104, 'security', '安全中心', '', '', '', 1, 2, '', 142, 43, 1, '', '2018-06-02 06:40:43', '2018-07-17 18:30:54');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (106, 'agentpay_passage', '代付通道', '', '', '', 1, 2, '', 141, 43, 1, '', '2018-07-11 23:27:28', '2018-07-11 23:27:28');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (110, 'batch_upload', '批量上传', '/agentpay/batch_upload', '', '', 1, 2, '', 354, 60, 1, '', '2018-07-13 18:07:09', '2018-08-08 10:52:59');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (111, 'security', '安全中心', '', '', '', 1, 3, '', 113, 69, 1, '', '2018-07-18 16:54:40', '2018-07-18 16:54:40');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (112, 'card_bin', '卡Bin管理', '', 'ROLE_CAR_BIN', '/config/car_bin/**', 1, 1, '', 317, 41, 1, '', '2018-07-23 11:54:49', '2018-07-23 12:34:54');
INSERT INTO t_mgr_sys_resource (resourceId, name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES (118, 'dev', '对接', '', '', '', 1, 2, 'layui-icon-code-circle', 401, 0, 1, '', '2018-11-24 11:32:41', '2018-11-24 23:48:54');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES ('pay_demo', '通道测试', '', '', '', 1, 2, '', 1, 118, 1, '', '2018-11-24 11:24:20', '2018-11-24 15:38:04');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES ('pay_doc', '接口文档', '', '', '', 1, 2, '', 2, 118, 1, '', '2018-11-24 11:24:20', '2018-11-24 15:38:04');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES ('sett', '结算设置', '/sett/config', 'CONFIG_SETT', '/sys/config/**', 1, 1, '', 405, 22, 1, '', '2018-01-23 23:09:42', '2018-01-23 23:09:42');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES ('pay_type', '支付类型', '', 'ROLE_PAY_TYPE', '/config/pay_type/**', 1, 1, '', 312, 41, 1, '', '2018-05-03 20:12:50', '2018-05-03 20:12:50');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property) VALUES ('recharge_record', '充值记录', '/agentpay/recharge_record', '', '', 1, 2, '', 355, 60, 1, '');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property) VALUES ('recharge', '在线充值', '/agentpay/recharge', '', '', 1, 2, '', 356, 60, 1, '');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES ('syslog', '操作日志', '', 'ROLE_SYS_SYSLOG', '/sys/syslog/**', 1, 1, '', 606, 31, 1, '', '2018-01-23 23:20:21', '2018-01-23 23:20:21');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES ('pay_cash_coll', '资金归集配置', '', 'ROLE_PAY_CASH_COLL_CONFIG', '/config/pay_cash_coll/**', 1, 1, '', 318, 41, 1, '', '2018-12-20 11:35:32', '2018-12-20 11:35:32');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES ('utils', '支付小工具', '', '', '', 1, 1, '', 319, 41, 1, '', '2018-12-20 11:35:32', '2018-12-20 11:35:32');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES ('pay_cash_coll_record', '资金归集订单', '', 'ROLE_PAY_CASH_COLL_RECORD', '/pay_cash_coll_record/**', 1, 1, '', 307, 16, 1, '', '2018-12-20 11:35:32', '2018-12-20 11:35:32');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES ('payOrder_successRate', '成功率统计', '', 'ROLE_DATA_PAYORDER_SUCCESSRATE', '/data/**', 1, 1, 'layui-icon-user', 488, 99, 1, '', '2018-05-31 21:59:38', '2018-05-31 22:09:17');
INSERT INTO t_mgr_sys_resource (name, title, jump, permName, permUrl, type, system, icon, orderNum, parentId, status, property, createTime, updateTime) VALUES ('child_agent', '二级代理', '', '', '', 1, 3, '', 650, 69, 1, '1', '2018-01-23 22:01:23', '2018-05-31 22:27:09');

# 初始化角色表
INSERT INTO t_mgr_sys_role (roleId, roleName, createUserId, createTime, updateTime) VALUES (6, '运营管理', null, '2018-01-23 22:03:15', '2018-01-23 22:03:15');
INSERT INTO t_mgr_sys_role (roleId, roleName, createUserId, createTime, updateTime) VALUES (7, '财务管理', null, '2018-01-23 22:03:24', '2018-01-23 22:03:24');
INSERT INTO t_mgr_sys_role (roleId, roleName, createUserId, createTime, updateTime) VALUES (8, '系统管理', null, '2018-01-23 22:03:31', '2018-01-23 22:03:31');
#初始化化权限
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (482, 9, 10, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (483, 9, 11, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (484, 9, 12, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (485, 9, 38, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (486, 9, 95, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (487, 9, 26, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (488, 9, 96, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (489, 9, 16, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (490, 9, 17, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (491, 9, 18, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (492, 9, 21, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (493, 9, 22, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (494, 9, 23, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (495, 9, 24, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (496, 9, 25, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (497, 9, 27, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (498, 9, 39, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (499, 9, 31, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (500, 9, 35, '2018-08-08 21:52:48', '2018-08-08 21:52:48');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (501, 8, 10, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (502, 8, 11, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (503, 8, 12, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (504, 8, 38, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (505, 8, 95, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (506, 8, 26, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (507, 8, 96, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (508, 8, 67, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (509, 8, 68, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (510, 8, 97, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (511, 8, 16, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (512, 8, 17, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (513, 8, 18, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (514, 8, 21, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (515, 8, 41, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (516, 8, 82, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (517, 8, 83, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (518, 8, 84, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (519, 8, 85, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (520, 8, 112, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (521, 8, 22, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (522, 8, 23, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (523, 8, 24, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (524, 8, 25, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (525, 8, 93, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (526, 8, 94, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (527, 8, 98, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (528, 8, 99, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (529, 8, 100, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (530, 8, 101, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (531, 8, 102, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (532, 8, 27, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (533, 8, 39, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (534, 8, 31, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (535, 8, 32, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (536, 8, 33, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (537, 8, 34, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (538, 8, 35, '2018-08-08 21:53:03', '2018-08-08 21:53:03');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (539, 7, 10, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (540, 7, 38, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (541, 7, 26, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (542, 7, 67, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (543, 7, 97, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (544, 7, 16, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (545, 7, 17, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (546, 7, 18, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (547, 7, 21, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (548, 7, 22, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (549, 7, 23, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (550, 7, 24, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (551, 7, 25, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (552, 7, 93, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (553, 7, 94, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (554, 7, 99, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (555, 7, 100, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (556, 7, 101, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (557, 7, 102, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (558, 7, 27, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (559, 7, 39, '2018-08-08 21:53:24', '2018-08-08 21:53:24');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (560, 6, 10, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (561, 6, 11, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (562, 6, 12, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (563, 6, 38, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (564, 6, 95, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (565, 6, 16, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (566, 6, 17, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (567, 6, 18, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (568, 6, 21, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (569, 6, 31, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
INSERT INTO t_mgr_sys_permission (id, roleId, resourceId, createTime, updateTime) VALUES (570, 6, 35, '2018-08-08 21:53:35', '2018-08-08 21:53:35');
# 初始化支付类型
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('10', '网银支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('11', '快捷支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('12', '微信扫码支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('13', '微信H5支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('14', '微信公众号支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('15', '微信小程序支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('16', '支付宝扫码支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('17', '支付宝H5支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('18', '支付宝服务窗支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('19', 'QQ钱包扫码', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('20', 'QQ钱包H5支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('21', '京东扫码支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('22', '京东H5支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('23', '百度钱包', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('24', '银联二维码', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('25', '充值卡支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('26', '支付宝PC支付', '1', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');
INSERT INTO t_pay_type (payTypeCode, payTypeName, Type, Status, CreateTime, UpdateTime) VALUES ('90', '转账', '2', 1, '2018-08-29 00:05:10', '2018-08-29 00:05:10');

# 初始化支付接口表
INSERT INTO t_pay_interface_type (IfTypeCode, IfTypeName, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('alipay', '支付宝支付官方', 1, '[{"name":"pid","desc":"合作伙伴身份（PID）","type":"text","verify":"required"},{"name":"appId","desc":"应用App ID","type":"text","verify":"required"},{"name":"alipayAccount", "desc":"支付宝账户", "type": "text","verify":"required"},{"name":"privateKey", "desc":"应用私钥", "type": "textarea","verify":"required"},{"name":"alipayPublicKey", "desc":"支付宝公钥", "type": "textarea","verify":"required"},{"name":"reqUrl", "desc":"网关地址", "type": "text","verify":"required"}]', '支付宝官方支付', '2018-05-08 19:38:05', '2018-05-08 19:38:05');
INSERT INTO t_pay_interface_type (IfTypeCode, IfTypeName, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('swiftpay', '威富通支付', 1, '[{"name":"mchId","desc":"商户号","type":"text","verify":"required"},{"name":"key","desc":"密钥","type":"text","verify":"required"},{"name":"reqUrl", "desc":"接口地址", "type": "text","verify":"required"}]', '威富通支付', '2018-05-08 19:38:59', '2018-05-08 19:38:59');
INSERT INTO t_pay_interface_type (IfTypeCode, IfTypeName, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('wxpay', '微信支付官方', 1, '[{"name":"appId","desc":"应用App ID","type":"text","verify":"required"},{"name":"appSecret","desc":"应用AppSecret","type":"text","verify":"required"},{"name":"mchId", "desc":"微信支付商户号", "type": "text","verify":"required"},{"name":"key", "desc":"API密钥", "type": "textarea","verify":"required"},{"name":"certLocalPath", "desc":"API证书路径", "type": "text","verify":""}]', '微信官方支付接口', '2018-05-08 19:37:19', '2018-05-08 19:37:19');
INSERT INTO t_pay_interface_type (IfTypeCode, IfTypeName, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('gepay', '个付通免签', 1, '[{"name":"mchId","desc":"商户号","type":"text","verify":"required"},{"name":"key","desc":"密钥","type":"text","verify":"required"},{"name":"reqUrl", "desc":"接口地址", "type": "text","verify":"required"}]', '个付通免签支付接口', '2018-11-05 19:37:19', '2018-11-05 19:37:19');

# 初始化支付接口类型表
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('alipay_pc', '支付宝PC支付', 'alipay', '10', 3, 1, null, '', '2018-05-08 19:49:04', '2018-05-08 19:49:04');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('alipay_wap', '支付宝H5支付', 'alipay', '17', 2, 1, null, '', '2018-05-08 19:46:58', '2018-05-08 19:46:58');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('swiftpay_alipay_native', '威富通支付宝扫码', 'swiftpay', '16', 3, 1, null, '', '2018-05-08 19:45:12', '2018-05-08 19:45:12');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('swiftpay_wxpay_native', '威富通微信扫码', 'swiftpay', '12', 3, 1, null, '', '2018-05-08 19:44:25', '2018-05-08 19:44:25');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('wxpay_jsapi', '微信公众号支付', 'wxpay', '14', 4, 1, null, '', '2018-05-08 19:49:40', '2018-05-08 19:49:40');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('wxpay_mweb', '微信H5支付', 'wxpay', '13', 2, 1, null, '', '2018-05-08 19:50:40', '2018-05-08 19:50:40');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('wxpay_native', '微信扫码支付', 'wxpay', '12', 3, 1, null, '', '2018-05-08 19:50:05', '2018-05-08 19:50:05');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('gepay_alipay_h5', '个付通支付宝H5', 'gepay', '17', 2, 1, null, '', '2018-11-02 02:16:16', '2018-11-02 02:16:16');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('gepay_alipay_qr', '个付通支付宝扫码', 'gepay', '16', 3, 1, null, '', '2018-11-02 02:16:42', '2018-11-02 02:16:42');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('gepay_wxpay_qr', '个付通微信扫码', 'gepay', '12', 3, 1, null, '', '2018-11-02 02:17:12', '2018-11-02 02:17:12');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('gepay_alipay_pc', '个付通支付宝PC', 'gepay', '26', 3, 1, null, '', '2018-11-10 23:58:15', '2018-11-10 23:58:15');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('alipay_qr_h5', '支付宝当面付H5支付', 'alipay', '17', 1, 1, null, '', '2018-11-27 19:49:04', '2018-11-27 19:49:04');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime) VALUES ('alipay_qr_pc', '支付宝当面付PC支付', 'alipay', '26', 3, 1, null, '', '2018-11-27 19:49:04', '2018-11-27 19:49:04');

# 支付通道
INSERT INTO `t_pay_passage` VALUES (1008, '支付宝当面付H5支付通道', 'alipay_qr_h5', 'alipay', '17', 1, 0.600000, NULL, NULL, NULL, NULL, NULL, 0, '', '2018-11-27 19:11:35', '2018-11-27 19:11:35');
INSERT INTO `t_pay_passage` VALUES (1009, '支付宝当面付PC支付通道', 'alipay_qr_pc', 'alipay', '26', 1, 0.600000, NULL, NULL, NULL, NULL, NULL, 0, '', '2018-11-27 19:11:35', '2018-11-27 19:11:35');
# 初始化支付产品
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8000, '网银支付', '10', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8001, '快捷支付', '11', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8002, '微信扫码支付', '12', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8003, '微信H5支付', '13', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8004, '微信公众号支付', '14', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8005, '微信小程序支付', '15', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8006, '支付宝扫码支付', '16', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8007, '支付宝H5支付', '17', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8008, '支付宝服务窗支付', '18', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8009, 'QQ钱包扫码', '19', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8010, 'QQ钱包H5支付', '20', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8011, '京东扫码支付', '21', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8012, '京东H5支付', '22', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8013, '百度钱包', '23', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8014, '银联二维码', '24', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8015, '充值卡支付', '25', 1);
INSERT INTO t_pay_product (id, ProductName, PayType, Status, ProductType) VALUES (8016, '网银B2B', '25', 1, 2);
INSERT INTO t_pay_product (id, ProductName, PayType, Status, ProductType) VALUES (8017, '网银B2C', '25', 1, 2);
INSERT INTO t_pay_product (id, ProductName, PayType, Status) VALUES (8018, '支付宝PC支付', '26', 1);

# 初始化系统配置
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('drawFlag', '提现开关', '1', '提现开关:0-关闭,1-开启', 'sett', 1.0);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('allowDrawWeekDay', '允许星期几', '1,2,3,4,5,6,7', '每周周几允许提现,数字表示,多个逗号分隔', 'sett', 1.1);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('drawDayStartTime', '开始时间','00:00:00', '每天提现开始时间', 'sett', 1.2);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('drawDayEndTime', '结束时间', '23:59:59', '每天提现结束时间', 'sett', 1.3);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('dayDrawTimes', '提现次数', '10', '每日提现次数', 'sett', 1.4);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('drawMaxDayAmount', '日提现最大金额' ,'50000000', '每天提现最大金额,单位分', 'sett', 1.5);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('maxDrawAmount', '单笔最大金额', '5000000', '单笔最大金额,单位分', 'sett', 1.6);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('minDrawAmount', '单笔最小金额', '100', '单笔最小金额,单位分', 'sett', 1.7);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('feeType', '手续费类型', '2', '手续费类型,1-百分比收费,2-固定收费', 'sett', 1.8);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('feeRate', '手续费百分比值','1', '手续费百分比值', 'sett', 1.9);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('feeLevel', '每笔手续费','100', '每笔提现手续费', 'sett', 2.0);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('drawFeeLimit', '手续费上限','1000', '每笔提现手续费上限,单位分', 'sett', 2.1);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('settType', '结算类型', '0', '结算类型,0-手动提现,1-自动结算', 'sett', 2.2);
INSERT INTO t_sys_config (code, name, value, remark, type, orderNum) VALUES ('settMode', '结算方式', '2', '结算方式,1-D0到账,2-D1到账,3-T0到账,4-T1到账', 'sett', 2.3);

# 以下为初始化测试数据
INSERT INTO t_agent_info (AgentId, AgentName, userName, realName, Password, PayPassword, IdCard, Mobile, Tel, Qq, Email, Address, AccountAttr, AccountType, BankName, BankNetName, AccountName, AccountNo, Province, City, AgentLevel, FeeType, FeeRate, FeeLevel, RiskDay, SettConfigMode, DrawFlag, AllowDrawWeekDay, DrawDayStartTime, DrawDayEndTime, DrawMaxDayAmount, MaxDrawAmount, MinDrawAmount, DayDrawTimes, SettType, SettMode, Status, LoginSecurityType, PaySecurityType, GoogleAuthStatus, GoogleAuthSecretKey, Remark, DrawFeeLimit, LastLoginIp, LastLoginTime, LastPasswordResetTime, CreateTime, UpdateTime, offRechargeRate, PrivateKey) VALUES (10000000, '华北总代', 'agent1', '王一鸣', '$2a$10$4pJu54SGhBMzBC/LBzwnV.A/vyg3VKaKdTylnBWEz.C1bdGH9U/TG', 'a60c5d01716b2069ec989311ac62b08f', '220283198302122288', 13811112222, '010-822123', '32423424', 'wyim@126.com', '北上广88号大厦12层', 0, 1, '招商银行', '万寿路支行', '王一鸣', '622313000332323333', '北京', '北京', 2, 2, 0.000000, '0', 0, 1, 1, null, null, null, null, null, null, 10, 0, 2, 1, 1, 1, 0, null, null, null, null, null, '2018-11-06 09:20:26', '2018-11-05 19:35:33', '2018-11-06 09:20:26', 0.000000, '');
INSERT INTO t_agent_account (AgentId, Balance, UnBalance, SecurityMoney, TotalIncome, TotalExpend, TodayIncome, TodayExpend, SettAmount, Status, AccountUpdateTime, CreateTime, UpdateTime) VALUES (10000000, 0, 0, 0, 0, 0, 0, 0, 0, 1, null, '2018-11-05 19:35:33', '2018-11-05 19:35:33');
INSERT INTO t_mch_info (MchId, Name, userName, realName, Type, AgentId, Mobile, Email, idCard, qq, siteName, siteUrl, Password, PayPassword, Role, MchRate, PrivateKey, Address, AccountAttr, AccountType, BankName, BankNetName, AccountName, AccountNo, Province, City, SettConfigMode, DrawFlag, AllowDrawWeekDay, DrawDayStartTime, DrawDayEndTime, DrawMaxDayAmount, FeeType, FeeRate, FeeLevel, DrawFeeLimit, RiskDay, MaxDrawAmount, MinDrawAmount, DayDrawTimes, SettType, SettMode, Status, LoginSecurityType, PaySecurityType, GoogleAuthStatus, GoogleAuthSecretKey, Remark, Tag, LastLoginIp, LastLoginTime, LastPasswordResetTime, LoginWhiteIp, LoginBlackIp, PayWhiteIp, PayBlackIp, AgentpayWhiteIp, AgentpayBlackIp, CreateTime, UpdateTime, offRechargeRate) VALUES (20000000, '测试商户', 'ouyangfeng', '欧阳丰', 1, 10000000, 13211112222, 'ouyang@126.com', '2202831922123212', '12332123', '百度', 'baidu.com', '$2a$10$bl2gh6uCrKN3StFiiAuYS.XAEhLFA7scd0/g5IsDxje84QIj3CfGq', 'a60c5d01716b2069ec989311ac62b08f', 'ROLE_MCH_NORMAL', null, 'D4SZ8TQK1Z8UPYMOLSKQQPMWYKVXW8IAHBMNJEFXJLCYPF7AWKCTKN1SXWS82ZNPMOBRFEGCK5TOGOQKPC59LP0FHIP6TU5GZ5TZXHHJ7YDGHSWP2URHZX1YUKPUMPAM', '深圳十度大厦11层209', 0, 1, '中国银行', '上地支行', '十渡', '544599831232110332', '北京', '北京', 1, 1, null, null, null, null, 2, 0.000000, '0', null, 0, null, null, 10, 0, 2, 1, 1, 1, 0, null, null, '111222333', null, null, '2018-11-06 09:20:06', '', '', '', '', '', '', '2018-11-05 20:50:11', '2018-11-06 09:20:05', 0.000000);
INSERT INTO t_mch_account (MchId, Name, Type, Balance, UnBalance, SecurityMoney, TotalIncome, TotalExpend, TodayIncome, TodayExpend, SettAmount, Status, AccountUpdateTime, CreateTime, UpdateTime, FrozenMoney, AgentpayBalance, UnAgentpayBalance) VALUES (20000000, '测试商户', 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, null, '2018-11-05 20:50:11', '2018-11-05 20:50:11', 0, 0, 0);
INSERT INTO t_mch_app (AppId, AppName, MchId, MchType, Status, Remark, CreateTime, UpdateTime) VALUES ('7ca36fb15e8943b79d098ce8a36aec0a', '测试应用', 20000000, 1, 1, null, '2018-11-05 21:22:13', '2018-11-05 21:22:13');
INSERT INTO t_pay_passage (id, PassageName, IfCode, IfTypeCode, PayType, Status, PassageRate, MaxDayAmount, MaxEveryAmount, MinEveryAmount, TradeStartTime, TradeEndTime, RiskStatus, Remark, CreateTime, UpdateTime) VALUES (1000, '个付通支付宝扫码', 'gepay_alipay_qr', 'gepay', '16', 1, 2.000000, null, null, null, null, null, 0, '个付通支付宝扫码测试', '2018-11-05 21:10:34', '2018-11-05 21:10:34');
INSERT INTO t_pay_passage (id, PassageName, IfCode, IfTypeCode, PayType, Status, PassageRate, MaxDayAmount, MaxEveryAmount, MinEveryAmount, TradeStartTime, TradeEndTime, RiskStatus, Remark, CreateTime, UpdateTime) VALUES (1001, '个付通支付宝H5支付', 'gepay_alipay_h5', 'gepay', '17', 1, 2.000000, null, null, null, null, null, 0, '个付通支付宝H5测试', '2018-11-05 21:11:18', '2018-11-06 09:27:00');
INSERT INTO t_pay_passage (id, PassageName, IfCode, IfTypeCode, PayType, Status, PassageRate, MaxDayAmount, MaxEveryAmount, MinEveryAmount, TradeStartTime, TradeEndTime, RiskStatus, Remark, CreateTime, UpdateTime) VALUES (1002, '个付通支付宝PC支付', 'gepay_alipay_pc', 'gepay', '26', 1, 2.000000, null, null, null, null, null, 0, '个付通支付宝PC测试', '2018-11-05 21:14:44', '2018-11-05 21:14:44');
INSERT INTO t_pay_passage (id, PassageName, IfCode, IfTypeCode, PayType, Status, PassageRate, MaxDayAmount, MaxEveryAmount, MinEveryAmount, TradeStartTime, TradeEndTime, RiskStatus, Remark, CreateTime, UpdateTime) VALUES (1003, '个付通微信扫码支付', 'gepay_wxpay_qr', 'gepay', '12', 1, 2.000000, null, null, null, null, null, 0, '个付通微信扫码测试', '2018-11-05 21:15:31', '2018-11-05 21:15:31');
INSERT INTO t_pay_passage_account (id, AccountName, PayPassageId, IfCode, IfTypeCode, Param, Status, PollWeight, PassageMchId, PassageRate, RiskMode, MaxDayAmount, MaxEveryAmount, MinEveryAmount, TradeStartTime, TradeEndTime, RiskStatus, Remark, CreateTime, UpdateTime) VALUES (1000100, '个付通测试账号1', 1003, 'gepay_wxpay_qr', 'gepay', '{"mchId":"test01","key":"zUzU6qtVpb7RA7EKyqhL40jbjPt7kF20","reqUrl":"http://p.gepay.yeepay.vip"}', 1, 1, 'test01', 2.000000, 1, null, null, null, null, null, 0, '', '2018-11-05 21:17:23', '2018-11-05 21:17:23');
INSERT INTO t_pay_passage_account (id, AccountName, PayPassageId, IfCode, IfTypeCode, Param, Status, PollWeight, PassageMchId, PassageRate, RiskMode, MaxDayAmount, MaxEveryAmount, MinEveryAmount, TradeStartTime, TradeEndTime, RiskStatus, Remark, CreateTime, UpdateTime) VALUES (1000101, '个付通测试账号1', 1002, 'gepay_alipay_pc', 'gepay', '{"mchId":"test01","key":"zUzU6qtVpb7RA7EKyqhL40jbjPt7kF20","reqUrl":"http://p.gepay.yeepay.vip"}', 1, 1, 'test01', 2.000000, 1, null, null, null, null, null, 0, '', '2018-11-05 21:18:55', '2018-11-05 21:18:55');
INSERT INTO t_pay_passage_account (id, AccountName, PayPassageId, IfCode, IfTypeCode, Param, Status, PollWeight, PassageMchId, PassageRate, RiskMode, MaxDayAmount, MaxEveryAmount, MinEveryAmount, TradeStartTime, TradeEndTime, RiskStatus, Remark, CreateTime, UpdateTime) VALUES (1000102, '个付通测试账号1', 1001, 'gepay_alipay_h5', 'gepay', '{"mchId":"test01","key":"zUzU6qtVpb7RA7EKyqhL40jbjPt7kF20","reqUrl":"http://p.gepay.yeepay.vip"}', 1, 1, 'test01', 2.000000, 1, null, null, null, null, null, 0, '', '2018-11-05 21:19:25', '2018-11-05 21:19:25');
INSERT INTO t_pay_passage_account (id, AccountName, PayPassageId, IfCode, IfTypeCode, Param, Status, PollWeight, PassageMchId, PassageRate, RiskMode, MaxDayAmount, MaxEveryAmount, MinEveryAmount, TradeStartTime, TradeEndTime, RiskStatus, Remark, CreateTime, UpdateTime) VALUES (1000103, '个付通测试账号1', 1000, 'gepay_alipay_qr', 'gepay', '{"mchId":"test01","key":"zUzU6qtVpb7RA7EKyqhL40jbjPt7kF20","reqUrl":"http://p.gepay.yeepay.vip"}', 1, 1, 'test01', 2.000000, 1, null, null, null, null, null, 0, '', '2018-11-05 21:19:58', '2018-11-05 21:19:58');
INSERT INTO t_mch_pay_passage (id, MchId, ProductId, MchRate, Status, IfMode, PayPassageId, PayPassageAccountId, PollParam, CreateTime, UpdateTime, ProductType) VALUES (1, 20000000, 8002, 3.000000, 1, 1, 1003, null, null, '2018-11-06 09:26:26', '2018-11-06 09:26:26', 1);
INSERT INTO t_mch_pay_passage (id, MchId, ProductId, MchRate, Status, IfMode, PayPassageId, PayPassageAccountId, PollParam, CreateTime, UpdateTime, ProductType) VALUES (2, 20000000, 8006, 3.000000, 1, 1, 1000, null, null, '2018-11-06 09:27:13', '2018-11-06 09:27:13', 1);
INSERT INTO t_mch_pay_passage (id, MchId, ProductId, MchRate, Status, IfMode, PayPassageId, PayPassageAccountId, PollParam, CreateTime, UpdateTime, ProductType) VALUES (3, 20000000, 8007, 3.000000, 1, 2, null, null, '[{"payPassageId":1001,"weight":1}]', '2018-11-06 09:27:22', '2018-11-06 09:27:22', 1);
INSERT INTO t_mch_pay_passage (id, MchId, ProductId, MchRate, Status, IfMode, PayPassageId, PayPassageAccountId, PollParam, CreateTime, UpdateTime, ProductType) VALUES (4, 20000000, 8018, 3.000000, 1, 1, 1002, null, null, '2018-11-06 09:27:31', '2018-11-06 09:27:31', 1);

# 支付宝红包
insert into t_pay_interface_type (IfTypeCode, IfTypeName, Status, Param, Remark, CreateTime, UpdateTime) values ('redpay', '免签红包支付', 1, '[{"name":"alipayAccount","desc":"支付宝账号","type":"text","verify":"required"},{"name":"alipayUserId","desc":"支付宝用户ID","type":"text","verify":"required"},{"name":"key","desc":"签名key","type":"text","verify":"required"},{"name":"autoJump","desc":"H5是否自动跳转,true为跳转,false为不自动跳转","type":"text","verify":"required"}]', '', '2019-02-13 17:14:06', '2019-02-15 20:32:55');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime, Extra) VALUES ('redpay_alipay_h5', '支付宝免签红包H5', 'redpay', '17', 2, 1, null, '', '2019-02-13 17:18:28', '2019-02-13 17:18:28', '');
INSERT INTO t_pay_interface (IfCode, IfName, IfTypeCode, PayType, Scene, Status, Param, Remark, CreateTime, UpdateTime, Extra) VALUES ('redpay_alipay_pc', '支付宝免签红包PC', 'redpay', '26', 3, 1, null, '', '2019-02-15 13:57:04', '2019-02-15 13:57:04', '');