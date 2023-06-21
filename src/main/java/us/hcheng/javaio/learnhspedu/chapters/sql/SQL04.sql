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
