第一周学习笔记
- 一、Java语言特性
    - 1、java是一种面向对象，静态类型、编译执行、有VM/GC运行时、跨平台的高级语言
    - 2、二进制跨平台
    - 3、Java所有内存的生命周期都由JVM运行时统一管理

- 二、字节码、类加载器、虚拟机
- 2 Java字节码技术
    - 1 什么是字节码？ 
    - Java bytecode 由单字节（byte）的指令组成，理论上最多支持 256 个操作码（opcode）；
    - 根据指令的性质，主要分为四个大类：
      1. 栈操作指令，包括与局部变量交互的指令
      2. 程序流程控制指令
      3. 对象操作指令，包括方法调用指令
      4. 算术运算以及类型转换指令
      
     - 生成字节码
        - 编译：javac demo/jvm0104/HelloByteCode.java
        - 查看字节码：javap -c demo.jvm0104.HelloByteCode
     - 字节码的运行时结构
     
        - JVM 是一台基于栈的计算机器。
        - 每个线程都有一个独属于自己的线程栈（JVM Stack），用于存储
        栈帧（Frame）。
        - 每一次方法调用，JVM 都会自动创建一个栈帧。
        - 栈帧由操作数栈， 局部变量数组以及一个 Class 引用组成。
        - Class 引用 指向当前方法在运行时常量池中对应的 Class。
    - 常见指令
        - new 创类的对象
        - dup 复制栈顶引用值
        - invokespecial 执行对象初始化
        - astore_1 指令将引用地址值(addr.)存储(store)到编号为 1的局部变量中，
        astore_1 中的1 指代LocalVariableTable中 ma 对应的槽位编号
        - iconst_1，iconst_2 用来将常量值 1，2 加载到栈
          里面，并分别由指令istore_2 和istore_3 将它们存储到在
          LocalVariableTable的槽位2和槽位3中
- 3 类加载器
    - 类的生命周期
        1. 加载（Loading）：找 Class 文件
        2. 验证（Verification）：验证格式、依赖
        3. 准备（Preparation）：静态字段、方法表
        4. 解析（Resolution）：符号解析为引用
        5. 初始化（Initialization）：构造器、静态变
        量赋值、静态代码块
        6. 使用（Using）
        7. 卸载（Unloading）
        
    - 类的加载时机
        1. 当虚拟机启动时，初始化用户指定的主类，就是启动执行的 main 方法所在的类；
        2. 当遇到用以新建目标类实例的 new 指令时，初始化 new 指令的目标类，就是 new
        一个类的时候要初始化；
        3. 当遇到调用静态方法的指令时，初始化该静态方法所在的类；
        4. 当遇到访问静态字段的指令时，初始化该静态字段所在的类；
        5. 子类的初始化会触发父类的初始化；
        6. 如果一个接口定义了 default 方法，那么直接实现或者间接实现该接口的类的初始化，
        会触发该接口的初始化；
        7. 使用反射 API 对某个类进行反射调用时，初始化这个类，其实跟前面一样，反射调用
        要么是已经有实例了，要么是静态方法，都需要初始化；
        8. 当初次调用 MethodHandle 实例时，初始化该 MethodHandle 指向的方法所在的类
    - 不会初始化（可能会加载）
        1. 通过子类引用父类的静态字段，只会触发父类的初始化，而不会触发子类的初始化。
        2. 定义对象数组，不会触发该类的初始化。
        3. 常量在编译期间会存入调用类的常量池中，本质上并没有直接引用定义常量的类，不
        会触发定义常量所在的类。
        4. 通过类名获取 Class 对象，不会触发类的初始化，Hello.class 不会让 Hello 类初始
        化。
        5. 通过 Class.forName 加载指定类时，如果指定参数 initialize 为 false 时，也不会触
        发类初始化，其实这个参数是告诉虚拟机，是否要对类进行初始化。Class.forName
        （“jvm.Hello”）默认会加载 Hello 类。
        6. 通过 ClassLoader 默认的 loadClass 方法，也不会触发初始化动作（加载了，但是
        不初始化）。
        
      - 类加载器
        1. 启动类加载器（BootstrapClassLoader）
        2. 扩展类加载器（ExtClassLoader）
        3. 应用类加载器（AppClassLoader）
        
        - 加载器特点：
        1. 双亲委托
        2. 负责依赖
        3. 缓存加载
      - 添加引用类的集中方式
        1. 放到 JDK 的 lib/ext 下，或者-Djava.ext.dirs
        2. java –cp/classpath 或者 class 文件放到当前路径
        3. 自定义 ClassLoader 加载
        4. 拿到当前执行类的 ClassLoader，反射调用 addUrl 方法添加 Jar 或路径(JDK9 无效)
    - 4 JVM内存模型
        - 每个线程都只能访问自己的线程栈。
        - 每个线程都不能访问（看不见）其他线程的局部变
    量。
        - 所有原生类型的局部变量都存储在线程栈中，因此
    对其他线程是不可见的。
        - 线程可以将一个原生变量值的副本传给另一个线程，
    但不能共享原生局部变量本身。
        - 堆内存中包含了 Java 代码中创建的所有对象，不
    管是哪个线程创建的。 其中也涵盖了包装类型
    （例如 Byte，Integer，Long 等）。
        - 不管是创建一个对象并将其赋值给局部变量， 还
    是赋值给另一个对象的成员变量， 创建的对象都
    会被保存到堆内存中
        - 方法中使用的原生数据
    类型和对象引用地址在栈上存储；
    对象、对象成员与类定义、静态变
    量在堆上。
        - 堆内存又称为“共享堆”，堆中的
    所有对象，可以被所有线程访问, 只
    要他们能拿到对象的引用地址。
        - 如果一个线程可以访问某个对象时，
    也就可以访问该对象的成员变量。
        - 如果两个线程同时调用某个对象的
    同一方法，则它们都可以访问到这
    个对象的成员变量，但每个线程的
    局部变量副本是独立的
    
  - JVM整体内存结构
    - 每启动一个线程，JVM 就会在栈空间栈分
    配对应的 线程栈, 比如 1MB 的空间（-
    Xss1m）
    - 线程栈也叫做 Java 方法栈。 如果使用了
    JNI 方法，则会分配一个单独的本地方法栈
    (Native Stack)
    - 线程执行过程中，一般会有多个方法组成调
      用栈（Stack Trace）, 比如 A 调用 B，B
      调用 C。。。每执行到一个方法，就会创建
      对应的 栈帧（Frame）
  - JVM栈内存结构
    - 栈帧是一个逻辑上的概念，具体的大小在
      一个方法编写完成后基本上就能确定
  - JVM堆内存结构
    - 堆内存是所有线程共用的内存空间，JVM 将
    Heap 内存分为年轻代（Young generation）和
    老年代（Old generation, 也叫 Tenured）两部分。
    - 年轻代还划分为 3 个内存池，新生代（Eden
    space）和存活区（Survivor space）, 在大部分
    GC 算法中有 2 个存活区（S0, S1），在我们可
    以观察到的任何时刻，S0 和 S1 总有一个是空的,
    但一般较小，也不浪费多少空间。
    - Non-Heap 本质上还是 Heap，只是一般不归 GC
    管理，里面划分为 3 个内存池。
    - Metaspace, 以前叫持久代（永久代, Permanent
    generation）, Java8 换了个名字叫 Metaspace.
    CCS, Compressed Class Space, 存放 class 信
    息的，和 Metaspace 有交叉。
    - Code Cache, 存放 JIT 编译器编译后的本地机器
    代码
  - CPU与内存行为
    - CPU 乱序执行
    - volatile 关键字
    - 原子性操作
    - 内存屏障
 - 5 JVM启动参数 
    - 以-开头为标准参数，所有的 JVM 都要实现这些参
    数，并且向后兼容。
    - -D 设置系统属性。
    - 以 -X 开头为非标准参数， 基本都是传给 JVM 的，
    默认 JVM 实现这些参数的功能，但是并不保证所
    有 JVM 实现都满足，且不保证向后兼容。 可以使
    用 java -X 命令来查看当前 JVM 支持的非标准参
    数。
    - 以 –XX：开头为非稳定参数, 专门用于控制 JVM
    的行为，跟具体的 JVM 实现有关，随时可能会在
    下个版本取消。
    -XX：+-Flags 形式, +- 是对布尔值进行开关。
    -XX：key=value 形式, 指定某个选项的值
   - JVM启动参数-堆内存
       - -Xmx, 指定最大堆内存。 如 -Xmx4g. 这只是限制了 Heap 部分的最大值为4g。
      这个内存不包括栈内存，也不包括堆外使用的内存。
      - -Xms, 指定堆内存空间的初始大小。 如 -Xms4g。 而且指定的内存大小，并
      不是操作系统实际分配的初始值，而是GC先规划好，用到才分配。 专用服务
      器上需要保持 –Xms 和 –Xmx 一致，否则应用刚启动可能就有好几个 FullGC。
      当两者配置不一致时，堆内存扩容可能会导致性能抖动。
      - -Xmn, 等价于 -XX:NewSize，使用 G1 垃圾收集器 不应该 设置该选项，在其
      他的某些业务场景下可以设置。官方建议设置为 -Xmx 的 1/2 ~ 1/4.
      - -XX：MaxPermSize=size, 这是 JDK1.7 之前使用的。Java8 默认允许的
      Meta空间无限大，此参数无效。
      - -XX：MaxMetaspaceSize=size, Java8 默认不限制 Meta 空间, 一般不允许设
      置该选项。
      - -XX：MaxDirectMemorySize=size，系统可以使用的最大堆外内存，这个参
      数跟 -Dsun.nio.MaxDirectMemorySize 效果相同。
      - -Xss, 设置每个线程栈的字节数。 例如 -Xss1m 指定线程栈为 1MB，与-
      XX:ThreadStackSize=1m 等价
   - - JVM启动参数-GC
    - -XX：+UseG1GC：使用 G1 垃圾回收器
    - -XX：+UseConcMarkSweepGC：使用 CMS 垃圾回收器
    - -XX：+UseSerialGC：使用串行垃圾回收器
    - -XX：+UseParallelGC：使用并行垃圾回收器
   - JVM启动参数-分析诊断
        - -XX：+-HeapDumpOnOutOfMemoryError 选项, 当 OutOfMemoryError 产生，即内存溢出(堆内存或持久代)时，
      自动 Dump 堆内存。
      示例用法： java -XX:+HeapDumpOnOutOfMemoryError -Xmx256m ConsumeHeap
      - -XX：HeapDumpPath 选项, 与 HeapDumpOnOutOfMemoryError 搭配使用, 指定内存溢出时 Dump 文件的目
      录。
      如果没有指定则默认为启动 Java 程序的工作目录。
      示例用法： java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/local/ ConsumeHeap
      自动 Dump 的 hprof 文件会存储到 /usr/local/ 目录下。
      - -XX：OnError 选项, 发生致命错误时（fatal error）执行的脚本。
      例如, 写一个脚本来记录出错时间, 执行一些命令, 或者 curl 一下某个在线报警的 url.
      示例用法：java -XX:OnError="gdb - %p" MyApp
      可以发现有一个 %p 的格式化字符串，表示进程 PID。
      - -XX：OnOutOfMemoryError 选项, 抛出 OutOfMemoryError 错误时执行的脚本。
      - -XX：ErrorFile=filename 选项, 致命错误的日志文件名,绝对路径或者相对路径。
      - -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1506，远程调试
   - JVM启动参数-JavaAgent
    - Agent 是 JVM 中的一项黑科技, 可以通过无侵入方式来做很多事情，比如注入 AOP 代码，执行统
      计等等，权限非常大。
      设置 agent 的语法如下:
      -agentlib:libname[=options] 启用 native 方式的 agent, 参考 LD_LIBRARY_PATH 路径。
      -agentpath:pathname[=options] 启用 native 方式的 agent。
      -javaagent:jarpath[=options] 启用外部的 agent 库, 比如 pinpoint.jar 等等。
      -Xnoagent 则是禁用所有 agent。
      以下示例开启 CPU 使用时间抽样分析:
      JAVA_OPTS="-agentlib:hprof=cpu=samples,file=cpu.samples.log"
