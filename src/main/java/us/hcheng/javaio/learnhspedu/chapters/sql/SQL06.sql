-- 事务的一个重要的概念和具体操作
use hspedu;

-- 1. 创建一张测试表
CREATE TABLE t27( id INT, `name` VARCHAR(32));
SELECT * FROM t27;
-- 2. 开始事务
start transaction;
-- 3. 设置保存点
savepoint p1;
-- 执行dml 操作
INSERT INTO t27 VALUES(100, 'tom');

savepoint p2;
-- 执行dml操作
INSERT INTO t27 VALUES(200, 'jack');

-- 回退到 p2
ROLLBACK TO p2;
-- 继续回退 a
ROLLBACK TO p1;
-- 如果这样, 表示直接回退到事务开始的状态.
ROLLBACK;
COMMIT;

-- 事务细节
-- 1. 如果不开始事务，默认情况下，dml操作是自动提交的，不能回滚
INSERT INTO t27 VALUES(300, 'milan'); -- 自动提交 commit

-- 2. 如果开始一个事务, 但没创建保存点. 也可以执行rollback 默认是回退到事务开始状态
START TRANSACTION;
INSERT INTO t27 VALUES(400, 'king');
INSERT INTO t27 VALUES(500, 'scott');
ROLLBACK; -- 表示直接回退到事务开始的的状态
COMMIT;

-- 3. 可以在一个事务中(还没有提交时), 创建多个保存点. 比如: savepoint a; savepoint b; ...
-- 4. 可以在事务没提交前, 选择回退到哪个保存点
-- 5. InnoDB 存储引擎支持事务, MyISAM 不支持
-- 6. 开始一个事务 start transaction; set autocommit=off;
set autocommit = off;
INSERT INTO t27 VALUES(400, 'king');
commit;

INSERT INTO t27 VALUES(500, 'king2');
rollback;
commit;
set autocommit = on;
INSERT INTO t27 VALUES(400, 'king');


select @@tx_isolation;
-- *** another end: read uncommitted *** start
start transaction;
create table acct (id int, `name` varchar(32), monney int);
select * from acct;
insert into acct values(100, 'tom', 600);        -- 没commit 如果read uncommitted会脏读
update acct set monney = 800 where name = 'tom'; -- 没commit 如果read uncommitted会不可重复读
insert into acct values(200, 'jack', 999);
commit;                                          -- commit了 如果read uncommitted幻读问题
-- *** another end: read uncommitted *** end

-- *** another end: read committed *** start
start transaction;
insert into acct values (123, 'scott', 888);    -- 没commit 如果read committed不会脏读 ;-)
update acct set monney = 123 where id = 100;
commit;                                         -- commit了 如果read committed有会 不可重复读 + 幻读问题
-- *** another end: read committed *** end

-- *** another end: repeatable read *** start
start transaction;
insert into acct values (666, 'abc', 6000);
update acct set monney = 1 where id = 123;
commit;                                         -- commit了 如果repeatable read没有脏读,幻读,和不可重复读的问题
select * from acct;                             -- 另一端(repeatable read)看不到这边的改变
-- *** another end: repeatable read *** end

-- *** another end: serializable *** start
start transaction;
insert into acct values (500, 'terry', 10000);
update acct set monney = 666 where id = 123;
commit;                                         -- 另一端(serializable)会卡住(读表或碰了同一个数据)直到这个commit了
-- *** another end: serializable *** end

-- MySQL事务隔离级别
-- 1. 开了两个mysql的控制台
-- 2. 查看当前mysql的隔离级别
select @@tx_isolation;              -- 当前会话隔离级别
select @@global.tx_isolation;       -- 系统当前隔离级别
-- 3. 设置隔离级别
set session transaction isolation level read uncommitted;
set session transaction isolation level read committed;
set session transaction isolation level repeatable read;
set session transaction isolation level serializable;

-- 表类型和存储引擎
-- 查看所有的存储引擎
show engines;
-- InnoDB存储引擎: 1. 支持事务 2. 支持外键 3. 支持行级锁
-- MyISAM存储引擎: 1. 添加速度快 2. 不支持外键和事务 3. 支持表级锁
CREATE TABLE t28 (id INT, `name` VARCHAR(32)) engine MyISAM;

start transaction;
savepoint p1;
insert into t28 values (2, 'jacks');
select * from t28;
rollback to p1;             -- no errors but record has been inserted still
commit;

-- MEMORY存储引擎: 1. 存在内存[关闭|重启MySQL服务 数据丢失但表结构还在] 2. 执行速度快(没IO读写) 3. 默认支持索引(hash表)
CREATE TABLE t29 (id INT, `name` VARCHAR(32)) engine MEMORY;
INSERT INTO t29 VALUES(1,'tom'), (2,'jack'), (3, 'hsp');
SELECT * FROM t29;      -- data is gone after mysql restart

-- 修改存储引擎
alter table t28 engine = InnoDB;

-- 事务的一个重要的概念和具体操作
use hspedu;

-- 1. 创建一张测试表
CREATE TABLE t27( id INT, `name` VARCHAR(32));
SELECT * FROM t27;
-- 2. 开始事务
start transaction;
-- 3. 设置保存点
savepoint p1;
-- 执行dml 操作
INSERT INTO t27 VALUES(100, 'tom');

savepoint p2;
-- 执行dml操作
INSERT INTO t27 VALUES(200, 'jack');

-- 回退到 p2
ROLLBACK TO p2;
-- 继续回退 a
ROLLBACK TO p1;
-- 如果这样, 表示直接回退到事务开始的状态.
ROLLBACK;
COMMIT;

-- 事务细节
-- 1. 如果不开始事务，默认情况下，dml操作是自动提交的，不能回滚
INSERT INTO t27 VALUES(300, 'milan'); -- 自动提交 commit

-- 2. 如果开始一个事务, 但没创建保存点. 也可以执行rollback 默认是回退到事务开始状态
START TRANSACTION;
INSERT INTO t27 VALUES(400, 'king');
INSERT INTO t27 VALUES(500, 'scott');
ROLLBACK; -- 表示直接回退到事务开始的的状态
COMMIT;

-- 3. 可以在一个事务中(还没有提交时), 创建多个保存点. 比如: savepoint a; savepoint b; ...
-- 4. 可以在事务没提交前, 选择回退到哪个保存点
-- 5. InnoDB 存储引擎支持事务, MyISAM 不支持
-- 6. 开始一个事务 start transaction; set autocommit=off;
set autocommit = off;
INSERT INTO t27 VALUES(400, 'king');
commit;

INSERT INTO t27 VALUES(500, 'king2');
rollback;
commit;
set autocommit = on;
INSERT INTO t27 VALUES(400, 'king');


select @@tx_isolation;
-- *** another end: read uncommitted *** start
start transaction;
create table acct (id int, `name` varchar(32), monney int);
select * from acct;
insert into acct values(100, 'tom', 600);        -- 没commit 如果read uncommitted会脏读
update acct set monney = 800 where name = 'tom'; -- 没commit 如果read uncommitted会不可重复读
insert into acct values(200, 'jack', 999);
commit;                                          -- commit了 如果read uncommitted幻读问题
-- *** another end: read uncommitted *** end

-- *** another end: read committed *** start
start transaction;
insert into acct values (123, 'scott', 888);    -- 没commit 如果read committed不会脏读 ;-)
update acct set monney = 123 where id = 100;
commit;                                         -- commit了 如果read committed有会 不可重复读 + 幻读问题
-- *** another end: read committed *** end

-- *** another end: repeatable read *** start
start transaction;
insert into acct values (666, 'abc', 6000);
update acct set monney = 1 where id = 123;
commit;                                         -- commit了 如果repeatable read没有脏读,幻读,和不可重复读的问题
select * from acct;                             -- 另一端(repeatable read)看不到这边的改变
-- *** another end: repeatable read *** end

-- *** another end: serializable *** start
start transaction;
insert into acct values (500, 'terry', 10000);
update acct set monney = 666 where id = 123;
commit;                                         -- 另一端(serializable)会卡住(读表或碰了同一个数据)直到这个commit了
-- *** another end: serializable *** end

-- MySQL事务隔离级别
-- 1. 开了两个mysql的控制台
-- 2. 查看当前mysql的隔离级别
select @@tx_isolation;              -- 当前会话隔离级别
select @@global.tx_isolation;       -- 系统当前隔离级别
-- 3. 设置隔离级别
set session transaction isolation level read uncommitted;
set session transaction isolation level read committed;
set session transaction isolation level repeatable read;
set session transaction isolation level serializable;

-- 表类型和存储引擎
-- 查看所有的存储引擎
show engines;
-- InnoDB存储引擎: 1. 支持事务 2. 支持外键 3. 支持行级锁
-- MyISAM存储引擎: 1. 添加速度快 2. 不支持外键和事务 3. 支持表级锁
CREATE TABLE t28 (id INT, `name` VARCHAR(32)) engine MyISAM;

start transaction;
savepoint p1;
insert into t28 values (2, 'jacks');
select * from t28;
rollback to p1;             -- no errors but record has been inserted still
commit;

-- MEMORY存储引擎: 1. 存在内存[关闭|重启MySQL服务 数据丢失但表结构还在] 2. 执行速度快(没IO读写) 3. 默认支持索引(hash表)
CREATE TABLE t29 (id INT, `name` VARCHAR(32)) engine MEMORY;
INSERT INTO t29 VALUES(1,'tom'), (2,'jack'), (3, 'hsp');
SELECT * FROM t29;      -- data is gone after mysql restart

-- 修改存储引擎
alter table t28 engine = InnoDB;

-- 视图的使用: 创建一个视图 emp_view01, 只能查询emp表的(empno, ename, job 和 deptno)信息
-- 创建视图
create view emp_view01 as select empno, ename, job, deptno from emp;
-- 查看视图
DESC emp_view01;
-- 查看创建视图的指令
show create view emp_view01;

select * from emp_view01;
select empno, ename, job from emp_view01;

-- 删除视图
drop view emp_view01;

-- 视图的细节
-- 1. 创建视图后，到数据库去看，对应视图只有一个视图结构文件(形式: 视图名.frm)
-- 2. 视图的数据变化会影响到基表，基表的数据变化也会影响到视图[insert update delete ]
-- 3. 视图中可以再使用视图 , 比如从emp_view01 视图中，选出empno,和ename做出新视图

-- 修改视图 会影响到基表
update emp_view01 set ename = 'Kingsss' where empno = 7839; -- success
update emp_view01 set sal = 0 where empno = 7839;        -- failed due to unknown sal in field list(a good thing: no back door)

-- 修改基本表， 会影响到视图
update emp set sal = 0 where empno = 7839;
update emp set ename = 'King' where empno = 7839;

-- 视图再视图
create view emp_names_view01 as (select ename from emp);
DESC emp_names_view01;
select * from emp_names_view01;
drop view emp_names_view01;

update emp_names_view01 set ename = 'SMITH哈哈哈' where ename = 'smith';
select * from emp_view01;
select * from emp;

-- 视图的课堂练习
-- 对 emp, dept, sal, grade 建个视图 emp_view03: 显示雇员编号 雇员名 部门名称 薪水 级别[即使用三张表，构建一个视图]
create view emp_view03 as select e.empno, e.ename, d.dname, e.sal, s.grade
from emp as e, dept as d, salgrade as s
where e.deptno = d.deptno and e.sal between s.losal and s.hisal;

SELECT * FROM emp_view03;
-- be careful with the update on view
update emp_view03 set grade = 11 where grade = 1;
update emp_view03 set grade = 1 where grade = 11;

-- MySQL用户管理
-- 原因: 当我们做项目开发时, 可以根据不同的开发人员, 赋给他相应的MySQL操作权限
-- 所以, MySQL数据库管理人员(root), 根据需要创建不同的用户, 赋给相应的权限 供不同人员使用

-- 1. 创建新的用户
-- 解读 (1) 'hsp_edu'@'localhost' 表示用户的完整信息 'hsp_edu' 用户名 'localhost' 登录的IP
--     (2) 123456 密码, 但是注意 存放到 mysql.user表时，是password('123456') 加密后的密码
--     *6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9
create user 'hsp_edu'@'localhost' identified by '123456';
select host, user, Password, authentication_string from mysql.user;
rename user 'hsp_edu'@'localhost' to 'hsp_edu'@'172.17.0.%';
-- 2. 删除用户
drop user 'hsp_edu'@'172.17.0.%';
-- 3. 登录 use client app to login

-- root 用户修改 hsp_edu@localhost 密码, 是可以成功.
alter user 'hsp_edu'@'172.17.0.%' identified by '123';
set password = password('admin');   -- 给自己改密码
set password for 'hsp_edu'@'172.17.0.%' = password('123');

-- 用户权限管理
-- 使用 root 建 testdb 数据库和 news 表
create database testdb;
use testdb;
create table news (id int, content varchar(32));
create table post(c int);
INSERT INTO news VALUES(100, '北京新闻');   -- 添加一条测试数据
insert into post values(1);
SELECT * FROM news;

-- 给 hsp_edu select, update, and insert rights
grant select, insert, update on testdb.news to 'hsp_edu'@'172.17.0.%';
-- 可以增加 delete 权限
grant select, delete on testdb.* to 'hsp_edu'@'172.17.0.%';

-- 回收 hsp_edu 用户在 testdb.news 表的所有权限
revoke insert, select, update, delete on testdb.news from 'hsp_edu'@'172.17.0.%';

REVOKE ALL ON testdb.news FROM 'hsp_edu'@'172.17.0.%';
REVOKE ALL ON testdb.* FROM 'hsp_edu'@'172.17.0.%';

-- 删除 hsp_edu
drop user 'hsp_edu'@'172.17.0.%';
drop database testdb;

-- 用户管理的细节
-- 在创建用户的时候，如果不指定Host, 则为%, %表示表示所有IP都有连接权限
create user moka;
SELECT `host`, `user` FROM mysql.user;

-- 你也可以这样指定'xxx'@'192.168.1.%'  表示 xxx用户在 192.168.1.*的ip可以登录MySQL
create user moka@'172.17.0.%';

-- 在删除用户的时候，如果 host 不是 %, 需要明确指定  '用户'@'host值'
drop user moka;                 -- user %,moka is deleted
drop user moka@'172.17.0.%';    -- 172.17.0.%,moka