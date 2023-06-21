# create database hspedu character set utf8 collate utf8_general_ci;
# show databases;
# show create database hspedu;

# use hspedu;
# create table t05 (
#     num bit(16)
# );
# select * from t05;
# insert into t05 values(3);
# insert into t05 values(16);
# -- insert into t05 values(65536); too large max 65535 as of 16 bits
# -- insert into t05 values(-1); this will fail
# select * from t05 where num >= 3; -- needs to use number for query cannot be string

-- max size 21844 because (2^16 - 3) / 3 since utf8 uses 3 bits and that '-3' is used internally for field length
# create table utf_t1 (
#     name varchar(21844)
# ) character set utf8;

-- max size 21844 because (2^16 - 3) / 3 since gbk uses 2 bits and that '-3' is used internally for field length
# create table gbk_t1 (
#     name varchar(32766)
# ) charset gbk;

-- text cannot set default value
# create table content_text (
#     t1 text,
#     t2 mediumtext,
#     t3 longtext
# );
# insert into content_text values('韩顺平教育', '韩顺平教育100', '韩顺平教育1000~~');
# select * from content_text;

# select * from t14;
# create table t14 (
#     birthday date,
#     class_time datetime,
#     login_time timestamp on update current_timestamp
# );
#
# insert into t14 (birthday, class_time) values ('1980-5-15', '1990-05-16 11:11:11');
# update t14 set class_time = '1990-05-16 12:12:11' where birthday = '1980-5-15';
# insert into t14 values ('1980-5-22', '1990-05-22 22:11:11', current_timestamp);

# EXERCISE V748
# create table v748 (
#     id int,
#     name varchar(32),
#     sex char(1),
#     birthday date,
#     hire_date datetime,
#     job varchar(32),
#     salary double,
#     resume text
# ) charset utf8 collate utf8mb3_general_ci engine innodb;

# show tables;
# alter table v748 add image varchar(100) not null default '' after resume;
# alter table v748 modify job varchar(60);
# alter table v748 drop sex;
# rename table v748 to v748_exec;
# alter table v748_exec charset utf8;
# alter table v748_exec change user_name username varchar(32);
# select * from v748_exec;
# desc v748_exec;

# create table people (
#     name varchar(30),
#     age int
# );
# select * from people;
# insert into people values('moka', 3), ('apple', 20), ('yeti', 15);
# select name from people
# order by age desc;

desc emp;

-- 演示字符串相关函数的使用  ， 使用emp表来演示
-- CHARSET(str)	返回字串字符集
select CHARSET(ename) from emp;
-- CONCAT (string2 [,... ])	连接字串, 将多个列拼接成一列
select concat('moka', 'pot');
select concat('moka', 'pot') from dual;
-- INSTR (string ,substring )	返回substring在string中出现的位置,没有返回0
-- dual 亚元表, 系统表 可以作为测试表使用
select instr('moka', 'mo'); -- 1
select instr('moka', 'z'); -- 0
-- UCASE (string2 )	转换成大写
select ucase('moka');
-- LCASE (string2 )	转换成小写
select lcase('MOKA');
-- LEFT (string2 ,length )	从string2中的左边起取length个字符
select left('moka is the best', 4);
-- RIGHT (string2 ,length )	从string2中的右边起取length个字符
select right('moka is the best', 4);
-- LENGTH (string )	string长度[按照字节]
select right('moka', length('moka'));
-- REPLACE (str ,search_str ,replace_str )
-- 在str中用replace_str替换search_str
select replace('moka', 'mo', 'zz');
-- 如果是manager 就替换成 经理
select replace(job, 'MANAGER', '经理') from emp;
-- STRCMP (string1 ,string2 )	逐字符比较两字串大小
select strcmp('moka', 'mokb');
select strcmp('mokz', 'mokb');
select strcmp('aaa', 'aaa');
-- SUBSTRING (str , position [,length ])
-- 从str的position开始【从1开始计算】,取length个字符
-- 从ename 列的第一个位置开始取出2个字符
select substring(ename, 1, 2) from emp;
-- LTRIM (string2 ) RTRIM (string2 )  TRIM(string)
-- 去除前端空格或后端空格
select ltrim(' moka ');
select rtrim(' moka ');
select trim(' moka ');

-- 练习: 以首字母小写的方式显示所有员工emp表的姓名
select concat(lcase(left(ename, 1)), ucase(substring(ename, 2)))
from emp;
select concat(lcase(substring(ename, 1, 1)), ucase(substring(ename, 2)))
from emp;