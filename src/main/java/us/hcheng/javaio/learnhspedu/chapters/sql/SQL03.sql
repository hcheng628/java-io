-- 子查询的演示 单列(单行和多行)
-- 请思考：如何显示与SMITH同一部门的所有员工?
/*
	1. 先查询到 SMITH的部门号得到
	2. 把上面的select 语句当做一个子查询来使用
*/
-- 下面的答案.
select * from emp where deptno = (select deptno from emp where ename = 'SMITH') and ename <> 'SMITH';

-- 课堂练习:如何查询和部门10的工作相同的雇员的
-- 名字、岗位、工资、部门号, 但是不含10号部门自己的雇员.
/*
	1. 查询到10号部门有哪些工作
	2. 把上面查询的结果当做子查询使用
*/
select ename, job, sal, deptno from emp
where job in (select distinct job from emp where deptno = 10) and deptno <> 10;

-- 子查询的演示 多列(多行)
-- 查询 ecshop 中各个类别中，价格最高的商品
-- 查询 商品表
-- 先得到 各个类别中，价格最高的商品 max + group by cat_id, 当做临时表
-- 把子查询当做一张临时表可以解决很多很多复杂的查询
-- 这个最后答案
use ecshop;
select goods_id, ecs_goods.cat_id, goods_name, ecs_goods.shop_price
from ecs_goods, (select max(shop_price) as shop_price, cat_id from ecs_goods group by cat_id) as tmp
where ecs_goods.shop_price = tmp.shop_price and ecs_goods.cat_id = tmp.cat_id;

-- all 和 any的使用
-- 请思考:显示工资比部门30的所有员工的工资高的员工的姓名、工资和部门号
select * from emp where sal > all(select sal from emp where deptno = 30);
select * from emp where sal > (select max(sal) from emp where deptno = 30);
-- 请思考:如何显示工资比部门30的其中一个员工的工资高的员工的姓名、工资和部门号
select * from emp where sal > any (select sal from emp where deptno = 30);
select * from emp where sal > (select min(sal) from emp where deptno = 30);

-- 多列子查询
-- 请思考如何查询与allen的部门和岗位完全相同的所有雇员(并且不含allen本人)
-- (字段1， 字段2 ...) = (select 字段 1，字段2 from 。。。。)

-- 分析: 1. 得到smith的部门和岗位
-- 分析: 2  把上面的查询当做子查询来使用，并且使用多列子查询的语法进行匹配
use hspedu;
select * from emp
where (deptno, job) = (select deptno, job from emp where ename = 'ALLEN') and ename <> 'ALLEN'


-- 请查询 和宋江数学，英语，语文
-- 成绩 完全相同的学生
-- subquery cannot return more than one row!!!
use ecshop;
select * from student
where (chinese, english, math) = (select chinese, english, math from student where name = '宋江')

-- 子查询练习
use hspedu;
-- 请思考：查找每个部门工资高于本部门平均工资的人的资料
-- 这里要用到数据查询的小技巧，把一个子查询当作一个临时表使用
select emp.* from emp, (select avg(sal) as avg_sal, deptno from emp group by deptno) as tmp
where emp.deptno = tmp.deptno and emp.sal > avg_sal;
-- 查找每个部门工资最高的人的详细资料
select emp.* from emp, (select max(sal) as max_sal, deptno from emp group by deptno) as tmp
where emp.deptno = tmp.deptno and emp.sal = tmp.max_sal;

-- 查询每个部门的信息(包括：部门名,编号,地址)和人员数量,我们一起完成。
select dname, dept.deptno, loc, emp_count from dept, (select count(*) as emp_count, deptno from emp group by deptno) as tmp
where dept.deptno = tmp.deptno;

-- 还有一种写法 表.* 表示将该表所有列都显示出来, 可以简化sql语句
-- 在多表查询中，当多个表的列不重复时，才可以直接写列名
select dept.*, emp_count from dept, (select count(*) as emp_count, deptno from emp group by deptno) as tmp
where dept.deptno = tmp.deptno;

-- 表的复制
-- 为了对某个sql语句进行效率测试，我们需要海量数据时，可以使用此法为表创建海量数据
CREATE TABLE my_tab01(
    id INT,
	`name` VARCHAR(32),
	sal DOUBLE,
	job VARCHAR(32),
	deptno INT
);
DESC my_tab01;
SELECT * FROM my_tab01;

-- 演示如何自我复制
-- 1. 先把emp 表的记录复制到 my_tab01
insert into my_tab01 select empno, ename, sal, job, deptno from emp;
insert into my_tab01 (id, `name`, sal, job,deptno) select empno, ename, sal, job, deptno from emp;
-- 2. 自我复制
insert into my_tab01 select * from my_tab01;
SELECT count(*) FROM my_tab01;
-- 如何删除掉一张表重复记录
-- 先创建一张表 my_tab02 并让 my_tab02 有重复的记录
-- 考虑去重 my_tab02的记录
create table my_tab02 like emp;
desc my_tab02;
select * from my_tab02;
insert into my_tab02 select * from my_tab02;
-- 思路
-- (1) 先创建一张临时表 my_tmp , 该表的结构和 my_tab02一样
-- (2) 把my_tmp 的记录 通过 distinct 关键字 处理后 把记录复制到 my_tmp
-- (3) 清除掉 my_tab02 记录
-- (4) 把 my_tmp 表的记录复制到 my_tab02
-- (5) drop 掉 临时表my_tmp

create table my_tab02_tmp like my_tab02;
insert into my_tab02_tmp select distinct * from my_tab02;
select * from my_tab02_tmp;
delete from my_tab02;
insert into my_tab02 select * from my_tab02_tmp;
drop table my_tab02_tmp;

