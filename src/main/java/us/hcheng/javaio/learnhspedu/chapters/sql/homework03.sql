-- homework03 (01 and 02 are skipped)
use hspedu;
-- ------1.选择部门30中的所有员工.
select * from emp where deptno = 30;
-- ------2.列出所有办事员(CLERK)的姓名，编号和部门编号.
select ename, empno, deptno from emp where job = 'CLERK';
-- ------3.找出佣金高于薪金的员工.
select * from emp where ifnull(comm, 0.0) > ifnull(sal, 0.0);
-- ------4.找出佣金高于薪金60%的员工.
select * from emp where ifnull(comm,0.0) > ifnull(sal, 0.0) * 0.6;
-- ------5.找出部门10中所有经理(MANAGER)和部门20中所有办事员(CLERK)的详细资料.
select * from emp where (job = 'MANAGER' and deptno = 10) or (job = 'CLERK' and deptno = 20);
-- ------6.找出部门10中所有经理(MANAGER),部门20中所有办事员(CLERK), 还有既不是经理又不是办事员但其薪金大于或等于2000的所有员工的详细资料.
select * from emp where (job = 'MANAGER' and deptno = 10) or (job = 'CLERK' and deptno = 20) or (job not in ('MANAGER', 'CLERK') and sal >= 2000);
-- ------7.找出收取佣金的员工的不同工作.
select job from emp where comm is not null group by job;
select distinct job from emp where comm is not null;
-- ------8.找出不收取佣金或收取的佣金低于100的员工.
select * from emp where comm is null or ifnull(comm, 0.0) < 100;
-- ------9.找出各月倒数第3天受雇的所有员工.
select * from emp where last_day(hiredate) - 2 = hiredate;  -- date - 2 is sub 2 days
select * from emp where date_sub(last_day(hiredate), interval 2 day) = hiredate;
select * from emp where datediff(last_day(hiredate), hiredate) = 2;
-- ------10.找出早于12年前受雇的员工.（即： 入职时间超过12年）
select * from emp where hiredate <= date_sub(now(), INTERVAL 12 YEAR);
SELECT * FROM emp WHERE DATE_ADD(hiredate, INTERVAL 12 YEAR) < NOW();
-- ------11.以首字母小写的方式显示所有员工的姓名.
select concat(lcase(left(ename, 1)), substring(ename, 2)) from emp;
-- ------12.显示正好为5个字符的员工的姓名.
select * from emp where length(ename) = 5;
-- ------13.显示不带有"R"的员工的姓名.
select * from emp where empno not like '%R%';
-- ------14.显示所有员工姓名的前三个字符.
select substring(ename, 1, 3) from emp;
select left(ename, 3) from emp;
-- ------15.显示所有员工的姓名,用a替换所有"A"
select replace(ename, 'A', 'a') from emp;
-- ------16.显示满10年服务年限的员工的姓名和受雇日期.
select ename, hiredate from emp where hiredate <= date_sub(now(), interval 10 year);
SELECT ename, hiredate FROM emp WHERE DATE_ADD(hiredate, INTERVAL 10 YEAR) <= NOW();
-- ------17.显示员工的详细资料,按姓名排序.
select * from emp order by ename;
-- ------18.显示员工的姓名和受雇日期,根据其服务年限,将最老的员工排在最前面.
select ename, hiredate from emp order by hiredate;
-- ------19.显示所有员工的姓名、工作和薪金,按工作降序排序,若工作相同则按薪金排序.
select ename, job, sal from emp order by job desc, sal;
-- ------20.显示所有员工的姓名、加入公司的年份和月份,按受雇日期所在月排序, 若月份相同则将最早年份的员工排在最前面.
select ename, year(hiredate) as year, month(hiredate) as month from emp order by month, year;
-- ------21.显示在一个月为30天的情况所有员工的日薪金,忽略余数.
select ifnull(sal, 0.0) / 30, ename from emp;
-- ------22.找出在(任何年份的)2月受聘的所有员工。
select * from emp where month(hiredate) = 2;
-- ------23.对于每个员工,显示其加入公司的天数.
select ename, datediff(now(), hiredate) from emp;
-- ------24.显示姓名字段的任何位置包含"A"的所有员工的姓名.
select * from emp where ename like '%A%';
-- ------25.以年月日的方式显示所有员工的服务年限.   (大概)
select floor(datediff(now(), hiredate) / 365) as years,
       floor(floor(datediff(now(), hiredate) % 365) / 31) as months,
       floor(datediff(now(), hiredate) % 31) as days, ename from emp;