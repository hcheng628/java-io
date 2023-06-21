# -- 日期时间相关函数
# select current_date;
# select current_date();
# select current_time;
# select current_time();
# select current_timestamp from dual;
# select current_timestamp() from dual;
select now();

# create table mes(
#     id int,
#     content varchar(30),
#     send_time datetime
# );
select * from mes;
insert into mes values(1, '北京新闻', current_timestamp), (2, '上海新闻', now()), (3, '天津新闻', current_timestamp());
-- 请查询在10分钟内发布的新闻, 思路一定要梳理一下.

select * from mes where send_time >= date_sub(now(), interval 10 minute);
select * from mes where date_add(send_time, interval 10 minute) >= now();

-- 请在mysql 的sql语句中求出 2011-11-11 和 1990-1-1 相差多少天
select datediff('2011-11-11', '1990-1-1');
select datediff('1990-1-1', '2011-11-11');
select datediff('1990-1-1 23:11:11', '2011-11-11');   -- still the same days
-- 请用mysql 的sql语句求出你活了多少天? [练习] 1986-11-11 出生
select datediff(now(), '1990-6-28');
-- 如果你能活80岁，求出你还能活多少天.[练习] 1986-11-11 出生
select datediff(date_add('1988-11-11', interval 80 year ), now()) as 'days left';

select timediff('0:0:15', '23:45:28');
select timediff('23:45:28', '0:0:15');

select year('1909-06-26');
select year(now());
select month(now());
select day(now());

select unix_timestamp();
select from_unixtime(unix_timestamp(), '%Y-%m_%d %H:%i:%s');

-- 演示加密函数和系统函数

-- USER()	查询用户
select user(); -- 可以查看登录到mysql的有哪些用户，以及登录的IP
-- DATABASE()	查询当前使用数据库名称
select database() from dual;

-- MD5(str)	为字符串算出一个 MD5 32的字符串，常用(用户密码)加密
-- root 密码是 hsp -> 加密md5 -> 在数据库中存放的是加密后的密码
select md5('hsp');
select length(md5(('hsp')));

-- 演示用户表，存放密码时，是md5
CREATE TABLE hsp_user (
    id INT ,
	`name` VARCHAR(32) NOT NULL DEFAULT '',
	pwd CHAR(32) NOT NULL DEFAULT '');
INSERT INTO hsp_user VALUES(100, '韩顺平', MD5('hsp'));
select * from hsp_user where `name` = '韩顺平' and pwd = MD5('hsp');
select * from hsp_user where `name` = '韩顺平' and pwd = 'c435a9810009800cafef7dce3b7844a6';
-- PASSWORD(str) -- 加密函数, MySQL数据库的用户密码就是 PASSWORD函数加密
select md5('hsp'), password('hsp');
-- mysql.user 表示 数据库.表

# 演示流程控制语句

# IF(expr1,expr2,expr3)	如果expr1为True ,则返回 expr2 否则返回 expr3
select if ('moka' = 'moka', 'moka is moka', 'moka is not moka');
# IFNULL(expr1,expr2)	如果expr1不为空NULL,则返回expr1,否则返回expr2
select ifnull(null, 'that guy is null...');
# SELECT CASE WHEN expr1 THEN expr2 WHEN expr3 THEN expr4 ELSE expr5 END; [类似多重分支.]
# 如果expr1 为TRUE,则返回expr2,如果expr2 为t, 返回 expr4, 否则返回 expr5
select case
    when true and 1 + 1 < 2 then 'this is true'
    when true and 1 + 1 = 2 then 'the second is true'
    else 'else ...'
end;

-- 1. 查询emp 表, 如果 comm 是null , 则显示0.0
--    老师说明，判断是否为null 要使用 is null, 判断不为空 使用 is not
select ename, job, ifnull(comm, 0.0) from emp;
select ename, job, if(comm is null, 0.0, comm) from emp;
-- 2. 如果emp 表的 job 是 CLERK 则显示 职员， 如果是 MANAGER 则显示经理
--     如果是 SALESMAN 则显示 销售人员，其它正常显示
select ename, (select case
                    when job = 'CLERK' then '职员'
                    when job = 'MANAGER' then '经理'
                    when job = 'SALESMAN' then '销售人员'
                    else job
                end)
from emp;

select ename,
       case
            when job = 'CLERK' then '职员'
            when job = 'MANAGER' then '经理'
            when job = 'SALESMAN' then '销售人员'
            else job
        end
from emp;

-- 查询加强
-- ■ 使用where子句
-- 	?如何查找1992.1.1后入职的员工
-- 老师说明： 在mysql中,日期类型可以直接比较, 需要注意格式
select * from emp where hiredate > '1992-1-1';

-- ■ 如何使用like操作符(模糊)
-- 	%: 表示0到多个任意字符 _: 表示单个任意字符
-- 	?如何显示首字符为S的员工姓名和工资
select * from emp where emp.ename like 'S%';
-- 	?如何显示第三个字符为大写O的所有员工的姓名和工资
select * from emp where emp.ename like '__O%';
-- ■ 如何显示没有上级的雇员的情况
select * from emp where emp.mgr IS NULL;
-- ■ 查询表结构
DESC emp;

-- 使用order by子句
--   ?如何按照工资的从低到高的顺序[升序]，显示雇员的信息
select * from emp order by sal;
--   ?按照部门号升序而雇员的工资降序排列 , 显示雇员信息
select * from emp order by deptno, sal desc;

-- 分页查询
-- 按雇员的id号升序取出， 每页显示3条记录，请分别显示 第1页，第2页，第3页
select * from emp order by empno;
-- 第1页
select * from emp order by empno limit 0, 3;
-- 第2页
select * from emp order by empno limit 3, 3;
-- 第3页
select * from emp order by empno limit 6, 3;
-- 推导一个公式
# limit (per_page_count * (page_num - 1) ), per_page_count;

-- answer:
select * from emp order by empno limit 10, 5;
select * from emp order by empno limit 20, 5;

-- 测试
-- 显示雇员总数，以及获得补助的雇员数
select count(*), count(comm) from emp;

-- 增强group by 的使用
-- (1) 显示每种岗位的雇员总数、平均工资。
select count(*), avg(sal), job from emp group by job;
-- (2) 显示雇员总数，以及获得补助的雇员数。
--  思路: 获得补助的雇员数 就是 comm 列为非null, 就是count(列)，如果该列的值为null, 是
--  不会统计 , SQL 非常灵活，需要我们动脑筋.
select count(*), count(comm) from emp;
--  老师的扩展要求：统计没有获得补助的雇员数
select count(*), count(if(comm is null, 1, null)) from emp;
select count(*), count(*) - count(comm) from emp;
-- (3) 显示管理者的总人数。小技巧:尝试写->修改->尝试[正确的]
select count(distinct mgr) from emp;
select count(tmp.mgr) from (select mgr from emp group by mgr) as tmp;
-- (4) 显示雇员工资的最大差额。
-- 思路： max(sal) - min(sal)
select max(sal) - min(sal) from emp;

-- 应用案例：请统计各个部门group by 的平均工资 avg，
-- 并且是大于1000的 having，并且按照平均工资从高到低排序， order by
-- 取出前两行记录 limit 0, 2
select deptno, avg(sal) from emp
group by deptno having avg(sal) > 1000
order by avg(sal) desc limit 0 , 2;
-- Column sal must be either aggregated, or mentioned in GROUP BY clause or use alias
select deptno, avg(sal) as sal_avg from emp
group by deptno having sal_avg > 1000
order by sal_avg desc limit 0 , 2;


-- 多表查询
-- ?显示雇员名,雇员工资及所在部门的名字 【笛卡尔集】
/*
	老韩分析
	1. 雇员名,雇员工资 来自 emp表
	2. 部门的名字 来自 dept表
	3. 需求对 emp 和 dept查询  ename,sal,dname,deptno
	4. 当我们需要指定显示某个表的列是，需要 表.列表
*/

select ename, sal, dname, emp.deptno from emp, dept where emp.deptno = dept.deptno;
-- 老韩小技巧：多表查询的条件不能少于 表的个数-1, 否则会出现笛卡尔集
-- ?如何显示部门号为10的部门名、员工名和工资
select ename, sal, dname, emp.deptno
from emp, dept
where emp.deptno = dept.deptno and emp.deptno = 10;
-- ?显示各个员工的姓名，工资，及其工资的级别
select ename, sal, grade from emp, salgrade
where sal between losal and hisal;
-- answer
-- 1
select ename, sal, dname from emp, dept
where emp.deptno = dept.deptno
order by emp.deptno desc;

-- 多表查询的 自连接
select * from emp;
-- 思考题: 显示公司员工名字和他的上级的名字
-- 老韩分析： 员工名字 在emp, 上级的名字的名字 emp
-- 员工和上级是通过 emp表的 mgr 列关联
-- 这里老师小结：
-- 自连接的特点 1. 把同一张表当做两张表使用
--            2. 需要给表取别名 表名  表别名
--		      3. 列名不明确，可以指定列的别名 列名 as 列的别名
select e.ename as emp_name, boss.ename as mgmt_name
from emp as e, emp as boss
where e.mgr = boss.empno;

select e.ename as emp_name, ifnull(boss.ename, '我是老大我怕谁') as mgmt_name
from emp as e left join emp as boss on e.mgr = boss.empno;