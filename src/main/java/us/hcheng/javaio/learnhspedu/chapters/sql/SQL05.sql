show databases;
use hspedu;
-- 合并查询
-- 查询 工资大于2500 或者是经理的员工
-- union all 就是将两个查询结果合 (不会去重)
select * from emp where sal > 2500
union all
select * from emp where job = 'manager';

-- union  就是将两个查询结果合并 (会去重)
select * from emp where sal > 2500
union
select * from emp where job = 'manager';
-- where + or
select * from emp where sal > 2500 or job = 'manager';

-- 外连接

-- 比如：列出部门名称和这些部门的员工名称和工作，
-- 同时要求 显示出那些没有员工的部门。
-- 使用我们学习过的多表查询的SQL， 看看效果如何?
select * from dept
where deptno not in (select distinct deptno from emp);

-- 创建 stu + exam
CREATE TABLE stu (id INT, `name` VARCHAR(32));
CREATE TABLE exam(id INT, grade INT);
INSERT INTO stu VALUES(1, 'jack'),(2,'tom'),(3, 'kity'),(4, 'nono');
INSERT INTO exam VALUES(1, 56),(2,76),(11, 8);
SELECT * FROM exam;
SELECT * FROM stu;

-- 使用左连接
-- （显示所有人的成绩，如果没有成绩，也要显示该人的姓名和id号,成绩显示为空
select `name`, stu.id, grade from stu, exam where stu.id = exam.id;
-- 改成左外连接
select stu.*, grade from stu left join exam on stu.id = exam.id;

-- 使用右外连接（显示所有成绩，如果没有名字匹配，显示空)
-- 即：右边的表(exam) 和左表没有匹配的记录，也会把右表的记录显示出来
select stu.*, grade from stu right join exam on stu.id = exam.id;

-- 列出部门名称和这些部门的员工信息(名字和工作)，
-- 同时列出那些没有员工的部门名。5min
-- 使用左外连接实现
select dept.deptno, dname, empno, ename from dept left join emp on dept.deptno = emp.deptno order by dept.deptno;

-- 使用右外连接实现
select dept.deptno, dname, empno, ename from emp right join dept on dept.deptno = emp.deptno order by dept.deptno;

-- 主键使用
CREATE TABLE t17 (id INT PRIMARY KEY, -- 表示id列是主键
`name` VARCHAR(32), email VARCHAR(32));

-- 主键列的值是不可以重复
INSERT INTO t17 VALUES(1, 'jack', 'jack@sohu.com');
INSERT INTO t17 VALUES(2, 'tom', 'tom@sohu.com');
SELECT * FROM t17;

-- 主键使用的细节讨论
-- 使用desc 表名，可以看到primary key的情况
-- primary key不能重复而且不能为 null。
INSERT INTO t17 VALUES(1, 'hsp', 'hsp@sohu.com');
INSERT INTO t17 VALUES(NULL, 'hsp', 'hsp@sohu.com');

-- 一张表最多只能有一个主键, 但可以是复合主键(比如 id+name)
CREATE TABLE t18(id INT PRIMARY KEY, -- 表示id列是主键
 `name` VARCHAR(32) PRIMARY KEY, -- 错误的
 email VARCHAR(32));

-- 演示复合主键 (id 和 name 做成复合主键)
create table t18 (
    id int, `name` varchar(32), email varchar(100),
    primary key (id, name)
);

INSERT INTO t18 VALUES(1, 'tom', 'tom@sohu.com'), (1, 'jack', 'jack@sohu.com'); -- names are different
INSERT INTO t18 VALUES(1, 'tom', 'xx@sohu.com'); -- 这里就违反了复合主键
insert into t18 values(3, null, 'xx@sohu.com'); -- if this field is a pk or part of pk it cannot be null
desc t18;
SELECT * FROM t18;
drop table t18;

-- 主键的指定方式 有两种
-- 1. 直接在字段名后指定：字段名  primakry key
-- 2. 在表定义最后写 primary key(列名);
CREATE TABLE t19 (
    id INT,
    `name` VARCHAR(32) PRIMARY KEY,
    email VARCHAR(32)
);

CREATE TABLE t20 (
    id INT ,
    `name` VARCHAR(32),
    email VARCHAR(32),
    PRIMARY KEY(`name`) -- 在表定义最后写 primary key(列名)
);

-- unique的使用
CREATE TABLE t21 (
    id INT UNIQUE ,  -- 表示 id 列是不可以重复的.
    `name` VARCHAR(32) ,
    email VARCHAR(32)
);

INSERT INTO t21 VALUES(1, 'jack', 'jack@sohu.com');
INSERT INTO t21 VALUES(1, 'tom', 'tom@sohu.com');

-- unqiue使用细节
-- 1. 如果没有指定 not null, 则 unique 字段可以有多个null
-- 如果一个列(字段), 是 unique not null 使用效果类似 primary key
INSERT INTO t21 VALUES(NULL, 'tom', 'tom@sohu.com'), (NULL, 'tom', 'tom@sohu.com');
SELECT * FROM t21;
-- 2. 一张表可以有多个unique字段

CREATE TABLE t22 (
    id INT UNIQUE,  -- 表示 id 列是不可以重复的.
    `name` VARCHAR(32) UNIQUE, -- 表示name不可以重复
    email VARCHAR(32)
);
DESC t22;

-- 外键演示
-- 创建 主表 my_class + 从表 my_stu
CREATE TABLE my_class (
    id INT PRIMARY KEY,
    `name` VARCHAR(32) NOT NULL DEFAULT ''
);
CREATE TABLE my_stu (
    id INT PRIMARY KEY,
    `name` VARCHAR(32) NOT NULL DEFAULT '',
    class_id INT,
    foreign key (class_id) references my_class(id)
);

SELECT * FROM my_class;
select * from my_stu;
-- 测试数据
INSERT INTO my_class VALUES(100, 'java'), (200, 'web'), (300, 'php');
INSERT INTO my_stu VALUES(1, 'tom', 100), (2, 'jack', 200), (3, 'hsp', 300);
INSERT INTO my_stu VALUES(4, 'mary', 400); -- 这里会失败...因为400班级不存在
INSERT INTO my_stu VALUES(5, 'king', NULL); -- 可以, 外键 没有写 not null

-- 一旦建立主外键的关系，数据不能随意删除了
DELETE FROM my_class WHERE id = 100;

CREATE TABLE my_class_moka (
    id INT,
    `name` VARCHAR(32) NOT NULL DEFAULT '',
    PRIMARY KEY(id, `name`)
);
CREATE TABLE my_stu_moka (
    id INT PRIMARY KEY,
    `name` VARCHAR(32) NOT NULL DEFAULT '',
    class_id INT,
    c_name varchar(32),
    foreign key (class_id, c_name) references my_class_moka(id, `name`)
);
desc my_class_moka;
select * from my_class_moka;
desc my_stu_moka;
select * from my_stu_moka;

INSERT INTO my_class_moka VALUES(100, 'java'), (200, 'web'), (300, 'php');

INSERT INTO my_stu_moka VALUES(1, 'tom', 100, 'haha');  -- failed
INSERT INTO my_stu_moka VALUES(1, 'tom', 100, 'java');  -- succeed
INSERT INTO my_stu_moka VALUES(2, 'tom', 100, null);  -- succeed but when update it has to be a valid c_name
INSERT INTO my_stu_moka VALUES(3, 'hsp', 300, null);

delete from my_class_moka where id = 100;   -- failed since there is an reference
delete from my_class_moka where id = 300;   -- succeed since the partial reference does not count (3, 'hsp', 300, null)

-- check的使用
-- mysql5.7目前还不支持check ,只做语法校验，但不会生效
-- 了解和学习 oracle, sql server, 这两个数据库是真的生效.
CREATE TABLE t23 (
    id INT PRIMARY KEY,
    `name` VARCHAR(32),
    sex char(1) check (sex in ('M', 'F')),
    sal double check (sal between 0 and 100000)
);
desc t23;
SELECT * FROM t23;

-- 添加数据
INSERT INTO t23 VALUES(1, 'jack', 'f', 1);
INSERT INTO t23 VALUES(1, 'jack', 'f', 100001);
INSERT INTO t23 VALUES(1, 'jack', 'k', 100000);

-- 使用约束的课堂练习

-- 现有一个商店的数据库shop_db，记录客户及其购物情况，由下面三个表组成：
create database shop_db;
use shop_db;

-- 商品goods (商品号goods_id, 商品名goods_name, 单价unit_price, 商品类别category, 供应商provider);
-- 客户customer (客户号customer_id,姓名name, 住址address, 电邮email性别sex, 身份证card_Id);
-- 购买purchase (购买订单号order_id，客户号customer_id, 商品号goods_id, 购买数量nums);
-- 1 建表，在定义中要求声明 [进行合理设计]：
-- (1)每个表的主外键；
-- (2)客户的姓名不能为空值；
-- (3)电邮不能够重复;
-- (4)客户的性别[男|女] check 枚举..
-- (5)单价unit_price 在 1.0 - 9999.99 之间 check

create table goods (
    goods_id int primary key,
    goods_name varchar(50) not null default '',
    unit_price double check ( unit_price between 1.0 and 9999.99),
    category int not null default 0,
    provider varchar(50) not null default ''
);

-- 客户customer（客户号customer_id,姓名name,住址address,电邮email性别sex, 身份证card_Id);
create table customer (
    customer_id int primary key,
    `name` varchar(50) not null default '',
    address varchar(50) not null default '',
    email VARCHAR(50) UNIQUE not null ,
    sex enum('F', 'M') not null,
    card_Id CHAR(18)
);
-- 购买purchase（购买订单号order_id，客户号customer_id,商品号goods_id, 购买数量nums);
create table purchase (
    order_id int unsigned primary key,
    customer_id int not null,
    goods_id int not null,
    nums int not null,
    foreign key (goods_id) references goods(goods_id),
    foreign key (customer_id) references customer(customer_id)
);
DESC goods;
DESC customer;
DESC purchase;

use hspedu;

-- 自增长的使用
-- 创建表
CREATE TABLE t24 (
    id INT PRIMARY KEY auto_increment,
    email VARCHAR(32) NOT NULL DEFAULT '',
    `name` VARCHAR(32) NOT NULL DEFAULT ''
);
DESC t24;
SELECT * FROM t24;

INSERT INTO t24 VALUES(NULL, 'tom@qq.com', 'tom');              -- use null
INSERT INTO t24 (email, `name`) VALUES('hsp@sohu.com', 'hsp');  -- not mention that field

-- 修改默认的自增长开始值
CREATE TABLE t25 (
    id INT unique auto_increment,   -- auto_increment must used with a key like pk or unique
    email VARCHAR(32)NOT NULL DEFAULT '',
    `name` VARCHAR(32)NOT NULL DEFAULT ''
);
SELECT * FROM t25;
drop table t25;
-- a key in DB that is greater than this value it will do nothing,\
-- it will always get the next value of the greatest value of the db
ALTER TABLE t25 AUTO_INCREMENT = 1;
INSERT INTO t25 VALUES(NULL, 'mary@qq.com', 'mary');
INSERT INTO t25 VALUES(666, 'hsp@qq.com', 'hsp'); -- normally dont do this it will mess up the whole thing

-- 创建测试数据库 tmp
CREATE DATABASE tmp;
CREATE TABLE dept (
    deptno MEDIUMINT UNSIGNED NOT NULL DEFAULT 0,
    dname VARCHAR(20) NOT NULL DEFAULT '',
    loc VARCHAR(13) NOT NULL DEFAULT ''
);
CREATE TABLE emp (
    empno  MEDIUMINT UNSIGNED  NOT NULL  DEFAULT 0,
    ename VARCHAR(20) NOT NULL DEFAULT '',
    job VARCHAR(9) NOT NULL DEFAULT '',
    mgr MEDIUMINT UNSIGNED NOT NULL DEFAULT 0,
    hiredate DATE NOT NULL,
    sal DECIMAL(7,2)  NOT NULL,
    comm DECIMAL(7,2) NOT NULL,
    deptno MEDIUMINT UNSIGNED NOT NULL DEFAULT 0
);
CREATE TABLE salgrade (
    grade MEDIUMINT UNSIGNED NOT NULL DEFAULT 0,
    losal DECIMAL(17,2)  NOT NULL,
    hisal DECIMAL(17,2)  NOT NULL
);

INSERT INTO salgrade VALUES (1, 700, 1200);
INSERT INTO salgrade VALUES (2, 1201, 1400);
INSERT INTO salgrade VALUES (3, 1401, 2000);
INSERT INTO salgrade VALUES (4, 2001, 3000);
INSERT INTO salgrade VALUES (5, 3001, 9999);

DELIMITER $$
#创建一个函数，名字 rand_string，可以随机返回我指定的个数字符串
CREATE FUNCTION rand_string(n INT)
    RETURNS VARCHAR(255) #该函数会返回一个字符串
BEGIN
#定义了一个变量 chars_str， 类型  varchar(100)
#默认给 chars_str 初始值   'abcdefghijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ'
    DECLARE chars_str VARCHAR(100) DEFAULT 'abcdefghijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ';
    DECLARE return_str VARCHAR(255) DEFAULT '';
    DECLARE i INT DEFAULT 0;
    WHILE i < n DO
            # concat 函数 : 连接函数mysql函数
            SET return_str = CONCAT(return_str,SUBSTRING(chars_str,FLOOR(1+RAND()*52),1));
            SET i = i + 1;
        END WHILE;
    RETURN return_str;
END $$


#这里我们又自定了一个函数,返回一个随机的部门号
CREATE FUNCTION rand_num( )
    RETURNS INT(5)
BEGIN
    DECLARE i INT DEFAULT 0;
    SET i = FLOOR(10+RAND()*500);
    RETURN i;
END $$

#创建一个存储过程， 可以添加雇员
CREATE PROCEDURE insert_emp(IN START INT(10),IN max_num INT(10))
BEGIN
    DECLARE i INT DEFAULT 0;
    #set autocommit =0 把autocommit设置成0
    #autocommit = 0 含义: 不要自动提交
    SET autocommit = 0; #默认不提交sql语句
    REPEAT
        SET i = i + 1;
        #通过前面写的函数随机产生字符串和部门编号，然后加入到emp表
        INSERT INTO emp VALUES ((START+i) ,rand_string(6),'SALESMAN',0001,CURDATE(),2000,400,rand_num());
    UNTIL i = max_num
        END REPEAT;
    #commit整体提交所有sql语句，提高效率
    COMMIT;
END $$

#添加8000000数据
CALL insert_emp(100001,8000000)$$
#命令结束符，再重新设置为;
DELIMITER ;

desc emp;
select * from emp limit 0, 20;
-- 在没有创建索引时，我们的查询一条记录
select * from emp where ename = 'jKYNSb';   -- took 12s
select * from emp where ename = 'jKYNSb' limit  0, 3;   -- with limit will be a bit faster especially like 1 and took 5s
-- 使用索引来优化一下， 体验索引的牛 创建索引后， 查询的速度如何
create index ename_idx on emp (ename);
select * from emp where ename = 'jKYNSb';   -- took 184ms
select * from emp where ename = 'jKYNSb' limit  0, 3;   -- took 107 ms
-- 在没有创建索引前, emp.ibd文件大小524m 创建索引后文件大小655m [索引本身也会占用空间.]
-- 创建ename列索引, emp.ibd 文件大小827m
alter table emp add index (empno);

select * from emp where empno = 8100001; -- took 118 ms
select * from emp where empno + 0 = 8100001; -- took 14s
select * from emp where empno + 0 = 8100001 limit 0, 1; -- took 14s

select * from emp where empno = (select max(empno) from emp); -- took 126 ms
select * from emp where (select max(empno) from emp) = empno; -- took 126 ms
-- DONT put function or such there, it will ignore index
select * from emp where round(empno) = (select max(empno) from emp); -- took 14 ms

-- MySQL索引的使用
-- 创建索引
CREATE TABLE t25 (
    id INT,
    `name` VARCHAR(32)
);
-- 查询表是否有索引
show index from t25;
show indexes from t25;
show keys from t25;

-- 唯一索引
-- 方式1
create unique index unique_id on t25 (id);   -- field does not have to be unique
create unique index id_index on t25 (id);   -- field can help more than one idxes
-- 方式2
alter table t25 add unique index (`name`);
-- 普通索引
-- 方式1
create index regular_idx on t25 (id);
-- 方式2
alter table t25 add index (id);

CREATE TABLE t26 (id INT , `name` VARCHAR(32));
desc t26;
-- 添加主键索引
alter table t26 add primary key (id);   -- one stone two birds

-- 删除索引
drop index unique_id on t25;
drop index name_2 on t25;
-- 删除主键索引
alter table t26 drop primary key;   -- one stone two birds but that pk field is still not null
show keys from t26;
-- Tips:
-- 1. 唯一索引 VS 普通索引 -> 如果某列的值, 是不会重复的, 则优先考虑使用unique索引, 否则用普通索引
-- 2. 修改索引, 先删除再添加新的索引

show tables;

delete from emp;
