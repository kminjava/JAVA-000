第七周学习笔记
- 用PreparedStatement的addBatch()方法执行插入100万条数据，用时2312秒;
- 用jdbcTemplate的execute()方法执行插入100万条数据，用时2737秒;
- 用mysql存储过程，插入100万条数据，用时245.609秒;
- 用PreparedStatement的addBatch()方法加多线程（100个线程，每个线程插入10000）插入100万条数据，用时425秒;