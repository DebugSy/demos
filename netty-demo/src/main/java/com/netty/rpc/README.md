# 自定义轻量级的RPC框架

## 框架
框架代码：annotation,client,common,service
### service
com.netty.rpc.service.RpcServer 创建Netty Rpc Service
com.netty.rpc.service.RpcHandler    定义ChannelHandler
com.netty.rpc.service.ContextHolder 扫描@RpcService注解

### client
com.netty.rpc.client.RpcClient  创建Netty Rpc Client
com.netty.rpc.client.RpcProxy   构造一个代理

### common
com.netty.rpc.common.RpcDecoder 解码器
com.netty.rpc.common.RpcEncoder 编码器
com.netty.rpc.common.RpcRequest 封装RPC请求
com.netty.rpc.common.RpcResponse    封装RPC响应
com.netty.rpc.common.SerializationUtil  序列化工具

## 用户程序
用户程序代码:app

## 调试
1.运行app/server下的主函数
2.运行app/cleint下的测试类，成功的话，会收到相应的数据