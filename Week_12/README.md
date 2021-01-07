## 第十二周学习笔记
- Redis主从复制
  - 拉取redis  
    
    `docker pull redis`

- 准备配置文件，分别启动启动在6379/6380/6381,6379为主

` port 6379
bind 192.168.11.202
requirepass "myredis"
daemonize yes
logfile "6379.log"
dbfilename "dump-6379.rdb"
dir "/opt/soft/redis/data" `

  - 运行redis镜像  

`
docker run -dit --name redis1 -p 6379:6379 -p 16381:16381 redis
docker run -dit --name redis2 -p 6380:6379 -p 16382:16382 redis
docker run -dit --name redis3 -p 6381:6379 -p 16383:16383 redis
`  
- 使用如下命令查看容器内网的ip地址等信息

`docker inspect containerid`
  - 三个redis内网IP地址为
    
    `redis-6379：172.17.0.2:6379
    redis-6380：172.17.0.3:6379
    redis-6381：172.17.0.4:6379`

- 使用redis-cli命令修改redis-6380、redis-6381的主机为172.17.0.2:6379

 - docker exec -ti redis2 redis-cli

127.0.0.1:6379> REPLICAOF 172.17.0.2 6379
OK

127.0.0.1:6379> info replication
# Replication
role:slave
master_host:172.17.0.2
master_port:6379
master_link_status:up
master_last_io_seconds_ago:2
master_sync_in_progress:0
slave_repl_offset:6833
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:ff69c32759e5f6ec405eb6966738cd8da88d07f1
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:6833
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:6833


docker exec -ti redis3 redis-cli

127.0.0.1:6379> REPLICAOF 172.17.0.2 6379
OK

127.0.0.1:6379> info replication
# Replication
role:slave
master_host:172.17.0.2
master_port:6379
master_link_status:up
master_last_io_seconds_ago:1
master_sync_in_progress:0
slave_repl_offset:7057
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:ff69c32759e5f6ec405eb6966738cd8da88d07f1
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:7057
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:29
repl_backlog_histlen:7029


docker exec -ti redis1 redis-cli

127.0.0.1:6379> info replication
# Replication
role:master
connected_slaves:2
slave0:ip=172.17.0.3,port=6379,state=online,offset=7099,lag=0
slave1:ip=172.17.0.4,port=6379,state=online,offset=7099,lag=0
master_replid:ff69c32759e5f6ec405eb6966738cd8da88d07f1
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:7099
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:7099
## 配置 Sentinel 哨兵
sentinel配置文件如下：

port 16381
daemonize no
pidfile /var/run/redis-sentinel.pid
logfile ""
dir /tmp
sentinel monitor mymaster 192.168.101.104 6381 2
sentinel down-after-milliseconds mymaster 10000
sentinel failover-timeout mymaster 30000
sentinel parallel-syncs mymaster 1