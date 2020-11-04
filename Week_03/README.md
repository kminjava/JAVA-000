第三周学习笔记
## 什么是高性能
    - 高并发用户
    - 高吞吐量
    - 低延迟
- 高性能的副作用
    - 系统复杂度上升
    - 建设维护成本增加
    - 故障破坏性增加
- 应对策略
    - 容量预估
    - 爆炸半径
    - 工程方面累积与改进
## Netty如何实现高性能
- Netty概览  
    - 网络应用开发框架      
        - 异步
        - 事件驱动
        - 基于NIO
    - 适用于
        - 服务端
        - 客户端
        - TCP/UDP
    - 从事件处理机制到Reactor模型
        - Reactor 模式首先是事件驱动
          的，有一个或者多个并发输入
          源，有一个 Serve ice Handler
          和多个 Event Handlers
        - Service Handler 会同步
          的将输入的请求多路复用的分
          发给相应的 Event Handler；
    - 从Rector模型到Netty NIO
        - 关键对象
            - Bootstrap:开启线程，开启socket
            - EventLoopGroup:线程池
            - EventLoop：线程
            - SocketChannel:连接
            - ChannelInitializer：初始化
            - ChannelPipeline:处理器链
            - ChannelHandler:处理器
    - Event & Handler
        - 入站事件
            - 通道激活和停用
            - 读操作事件
            - 异常事件
            - 用户事件 
    - 出站事件
        - 打开连接
        - 关闭连接
        - 写入数据
        - 刷新数据
    - 事件处理程序接口
        - ChannelHandler
        - ChannelOutboundHandler
        - ChannelInboundHandler
    - 适配器
        - ChannelInboundHandlerAdapter
        - ChannelOutboundHandlerAdapter
    - Netty应用组成
        - 网络事件
        - 应用程序逻辑事件
        - 事件处理程序
- Netty网络程序优化
    - 粘包与拆包
        - ByteToMessageDecoder 提供的一些常见的实现
          类：
          1. FixedLengthFrameDecoder：定长协议解码
          器，我们可以指定固定的字节数算一个完整的
          报文
          2. LineBasedFrameDecoder：行分隔符解码器，
          遇到\n 或者\r\n，则认为是一个完整的报文
          3. DelimiterBasedFrameDecoder：分隔符解码
          器，分隔符可以自己指定
          4. LengthFieldBasedFrameDecoder：长度编码
          解码器，将报文划分为报文头/报文体
          5. JsonObjectDecoder：json 格式解码器，当检
          测到匹配数量的“{” 、”}”或”[””]”时，则认为是
          一个完整的 json 对象或者 json 数组
        - Nagle 与 TCP_NODELAY
            - Nagle 算法优化触发条件：
                - 缓冲区满
                - 达到超时 
        - 网络拥堵与 e Nagle 算法优化
            - TCP_NODELAY
            - MTU: Maxitum Transmission Unit
      最大传输单元
            - MSS: Maxitum Segment Size 最大
      分段大小
    - TCP三次握手与四次挥手
    - Netty优化
          - 1 、不要阻塞 EventLoop
          - 2 、系统参数优化
          ulimit - -a a /proc/sys/net/ipv4/ tcp_fin_timeout, TcpTimedWaitDelay
          - 3、缓冲区优化
          SO_RCVBUF /SO_SNDBUF/SO_BACKLOG/ REUSEXXX
          - 4 、心跳频率周期优化
          心跳机制与断线重连
          - 5 、内存与 r ByteBuffer 优化
          DirectBuffer 与 HeapBuffer
          - 6 、其他优化
            - ioRatio: :
            - Watermark
            - TrafficShaping
## API网关
    - 网关的四大职能
        - 请求接入：作为所有API接口服务请求的接入点
        - 业务聚合：作为所有后端业务服务的接入点
        - 中介策略：实现安全、验证、路由、过滤、流控等策略
        - 统一管理：对所有API服务和策略统一管理
    - 网关的分类
        - 流量网关
            - 关注稳定与安全
                - 全局性流控
                - 日志统计
                - 防止SQL注入
                - 防止web攻击
                - 屏蔽工具扫描
                - 黑白IP名单
                - 证书加密解密
        - 业务网关
            - 提供更好的服务
                - 服务级别流控
                - 服务降级与熔断
                - 路由与负载均衡、灰度策略
                - 服务过滤、聚合与发现
                - 权限验证与用户等级策略
                - 业务规则与参数校验
                - 多级缓存策略
    - 典型应用：API网关
        - Zuul
            - Zuul 是 是 Netflix 开源的 API 网关系统，它
              的主要设计目标是动态路由、监控、弹性
              和安全;
            - Zuul 的内部原理可以简单看做是很多不同
              功能 filter 的集合 ，最主要的就是 pre 、
              routing 、 post 这三种过滤器，分别作用
              于调用业务服务 API 之前的请求处理、直
              接响应、调用业务服务 API 之后的响应处
              理;
        - Zuul2
            - Zuul 2.x 是基于 Netty 内核重构的版本
            - 核心功能
                1.Service Discovery
                2.Load Balancing
                3.Connection Pooling
                4.Status Categories
                5.Retries
                6.Request Passport
                7.Request Attempts
                8.Origin Concurrency Protection
                9.HTTP/2
                10.Mutual TLS
                11.Proxy Protocol
                12.GZip
                13.WebSockets
        - Spring Cloud Gateway
            - 扩展性好，适合业务网关，二次开发
    
         