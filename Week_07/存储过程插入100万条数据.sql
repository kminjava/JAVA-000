show variables like 'log_bin_trust_function_creators';
set global log_bin_trust_function_creators=1;

-- 创建随机生成字符串的函数
delimiter $$ 
drop function if exists rand_string; 
create function rand_string(n int) returns varchar(255) begin declare chars_str varchar(52) default 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
declare return_str varchar(255) default '';
declare i int default 0;
while i<n do set return_str=concat(return_str,substring(chars_str,floor(1+rand()*52),1));
set i=i+1;
 end while;
 return return_str;
 end $$
 
 -- 创建随机生成编号的函数
 delimiter $$ 
 drop function if exists rand_num;
 create function rand_num() returns int(5) begin declare i int default 0;
 set i=floor(100+rand()*100);
 return i;
 end $$
 
 -- 创建往tb_dept_bigdata表中插入数据的存储过程
 delimiter $$ 
 drop procedure if exists insert_dept; 
 create procedure insert_dept(in start int(10),in max_num int(10)) begin declare i int default 0; 
 set autocommit=0; 
 repeat set i=i+1; 
 insert into summer_user (username,password,phone,email) values(rand_string(5),'123456','13412344567',rand_string(8)); 
 until i=max_num end repeat; 
 commit; 
 end $$
 
 -- 查看函数执行状态
 show function status;
 -- 调用查询存储过程
  call insert_dept(1,1000000);
 -- 删除函数与存储过程
 drop function rand_num; 
 drop function rand_string;
 -- 删除存储过程
 drop procedure insert_dept; 


 

 