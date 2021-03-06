第二周学习笔记
 - 串行 GC（Serial GC）/ParNewGC
    - 串行 GC 对年轻代使用 mark-copy（标记-复制） 算法，对老年代使用 mark-sweep-compact
      （标记-清除-整理）算法。
    - 两者都是单线程的垃圾收集器，不能进行并行处理，所以都会触发全线暂停（STW），停止所
      有的应用线程。
    - CPU 利用率高，暂停时间长；
 - 并行 GC（Parallel GC）
    - 年轻代和老年代的垃圾回收都会触发 STW 事件；
    - 在年轻代使用 标记-复制（mark-copy）算法，在老年代使用 标记-清除-整理（mark-sweep-
      compact）算法；
    - 并行垃圾收集器适用于多核服务器，主要目标是增加吞吐量。因为对系统资源的有效使用，能达到
      更高的吞吐量:
    - 在 GC 期间，所有 CPU 内核都在并行清理垃圾，所以总暂停时间更短；
    - 在两次 GC 周期的间隔期，没有 GC 线程在运行，不会消耗任何系统资源；
 - CMS GC
    - 其对年轻代采用并行 STW 方式的 mark-copy (标记-复制)算法，对老年代主要使用并发 mark-
      sweep (标记-清除)算法；
    - 不对老年代进行整理，而是使用空闲列表（free-lists）来管理内存空间的回收；
    - 在 mark-and-sweep （标记-清除） 阶段的大部分工作和应用线程一起并发执行；
    - 默认情况下，CMS 使用的并发线程数等于 CPU 核心数的 1/4；
    - 如果服务器是多核 CPU，并且主要调优目标是降低 GC 停顿导致的系统延迟，那么使用 CMS 是
      个很明智的选择。进行老年代的并发回收时，可能会伴随着多次年轻代的 minor GC；
 - G1 GC
    - G1 GC 最主要的设计目标是：将 STW 停顿的时间
      和分布，变成可预期且可配置的；
    - 堆不再分成年轻代和老年代，而是划分为多
      个（通常是 2048 个）可以存放对象的 小块堆区域
      (smaller heap regions)。每个小块，可能一会被
      定义成 Eden 区，一会被指定为 Survivor区或者
      Old 区。在逻辑上，所有的 Eden 区和 Survivor
      区合起来就是年轻代，所有的 Old 区拼在一起那
      就是老年代；
    - 每
      次只处理一部分内存块，称为此次 GC 的回收
      集(collection set)。每次 GC 暂停都会收集所
      有年轻代的内存块，但一般只包含部分老年代
      的内存块；
    - 在并发阶段估算每个小
      堆块存活对象的总数。构建回收集的原则是：
      垃圾最多的小块会被优先收集；
 - ZGC
      1. GC 最大停顿时间不超过 10ms
      2. 堆内存支持范围广，小至几百 MB 的堆空间，大至 4TB 的超大堆
      内存（JDK13 升至 16TB）
      3. 与 G1 相比，应用吞吐量下降不超过 15%；
 - GC总结
    - 1. 串行 GC（Serial GC）: 单线程执行，应用需要暂停；
      2. 并行 GC（ParNew、Parallel Scavenge、Parallel Old）: 多线程并行地执行垃圾回收，
      关注与高吞吐；
      3. CMS（Concurrent Mark-Sweep）: 多线程并发标记和清除，关注与降低延迟；
      4. G1（G First）: 通过划分多个内存区域做增量整理和回收，进一步降低延迟；
      5. ZGC（Z Garbage Collector）: 通过着色指针和读屏障，实现几乎全部的并发执行，几毫
      秒级别的延迟，线性可扩展；
      
 - Java Sockt编程
    - 阻塞市IO BIO
        - 一般通过在while(true) 循环中服务端
          会调用 accept() 方法等待接收客户端
          的连接的方式监听请求，请求一旦接
          收到一个连接请求，就可以建立通信
          套接字在这个通信套接字上进行读写
          操作，此时不能再接收其他客户端连
          接请求，只能等待同当前连接的客户
          端的操作执行完成， 不过可以通过多
          线程来支持多个客户端的连接；
    - 非阻塞式IO
        - 内核会立即返回，返
          回后获得足够的CPU时间继续做其它
          的事情；
        - 用户进程第一个阶段不是阻塞的,需要
          不断的主动询问kernel数据好了没有；
          第二个阶段依然总是阻塞的；
    - IO多路复用(IO multiplexing)
        - 也称事
          件驱动IO(event-driven IO)，就是在单
          个线程里同时监控多个套接字，通过
          select 或 poll 轮询所负责的所有socket
          ，当某个socket有数据到达了，就通知
          用户进程；
        - 利用
          了新的select系统调用，由内核来负责本
          来是请求进程该做的轮询操作。看似比非
          阻塞IO还多了一个系统调用开销，不过
          因为可以支持多路IO，才算提高了效率；
        - 进程先是阻塞在 select/poll 上，再是阻
          塞在读操作的第二个阶段上；
        - select/poll的几大缺点：
            （1）每次调用select，都需要把fd集合从用户态拷贝到内
            核态，这个开销在fd很多时会很大
            （2）同时每次调用select都需要在内核遍历传递进来的所
            有fd，这个开销在fd很多时也很大
            （3）select支持的文件描述符数量太小了，默认是1024；
        - epoll（Linux 2.5.44内核中引入,2.6内核正式引入,可被用
          于代替POSIX select 和 poll 系统调用）：
          （1）内核与用户空间共享一块内存；
          （2）通过回调解决遍历问题；
          （3）fd没有限制，可以支撑10万连接；
    - 信号驱动I/O
        - 信号驱动IO与BIO和NIO最大的区别就
          在于，在IO执行的数据准备阶段，不
          会阻塞用户进程；
    - 异步式IO
        - 用户进程发出系统调用后立即返回，
          内核等待数据准备完成，然后将数据
          拷贝到用户进程缓冲区，然后发送信
          号告诉用户进程IO操作执行完毕（与
          SIGIO相比，一个是发送信号告诉用户
          进程数据准备完毕，一个是IO执行完
          毕）；
 - Netty
    - 网络应用开发框架
      1. 异步
      2. 事件驱动
      3. 基于NIO
      适用于:
      -  服务端
      - 客户端
      - TCP/UDP