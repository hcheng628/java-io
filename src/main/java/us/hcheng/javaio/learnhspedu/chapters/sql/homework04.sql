-- homework04

-- (1). 列出至少有一个员工的所有部门
select count(*) as c, dname from dept, emp where emp.deptno = dept.deptno group by emp.deptno having c > 0;
-- (2). 列出薪金比"SMITH"多的所有员工
select * from emp where sal > (select ifnull(sal, 0.0) from emp where ename = 'smith' limit 0, 1);
-- (3). 列出受雇日期晚于其直接上级的所有员工
select * from emp as e where
    e.mgr is not null
    and
    e.hiredate > (
        select ifnull(boss.hiredate, '1970-1-1') from emp as boss
        where boss.empno = e.mgr limit 0, 1
);

select e.ename, e.hiredate, 'vs', boss.ename, boss.hiredate from emp as e, emp as boss
         where
             e.mgr = boss.empno
           and
             e.hiredate is not null
           and
             boss.hiredate is not null
            and
             e.hiredate > boss.hiredate;

-- (4). 列出部门名称和这些部门的员工信息，同时列出那些没有员工的部门
select dname, e.ename from dept left join emp e on dept.deptno = e.deptno;
-- (5). 列出所有"CLERK"（办事员）的姓名及其部门名称
select ename, d.dname, job from emp left join dept d on emp.deptno = d.deptno where job = 'CLERK';
-- BELOW IS BETTER!!! THE ONE ABOVE HAS ISSUE IF THERE IS NO MATCH ON deptno
SELECT ename, dname , job FROM emp, dept WHERE job = 'CLERK' AND emp.deptno = dept.deptno;
select * from emp;
-- (6). 列出最低薪金大于1500的各种工作.
select job, min(sal) as min_sal from emp group by job having min_sal > 1500;
-- (7). 列出在部门"SALES"（销售部）工作的员工的姓名.
select emp.* from emp, dept d where emp.deptno = d.deptno and dname = 'SALES';
-- (8). 列出薪金高于公司平均薪金的所有员工.
select * from emp where sal > (select ifnull(avg(sal), 0.0) from emp);
-- (9). 列出与"SCOTT"从事相同工作的所有员工.
select * from emp where job = (select job from emp where ename = 'SCOTT') and ename <> 'SCOTT';
-- (10)．列出薪金高于所在部门30的工作的所有员工的薪金的员工姓名和薪金。
select ename, sal from emp where sal > all(select sal from emp where deptno = 30);
select ename, sal from emp where sal > (select max(sal) from emp where deptno = 30);
-- (11). 列出在每个部门工作的员工数量, 平均工资和平均服务期限(时间单位).
-- ???
select dname, count(*), avg(sal), avg(datediff(now(), hiredate)) from emp, dept where emp.deptno = dept.deptno
group by dept.deptno;
-- (12). 列出所有员工的姓名, 部门名称和工资.
select ename, dname, sal from emp, dept where emp.deptno = dept.deptno;
-- (13). 列出所有部门的详细信息和部门人数。
select dept.*, count(*) from dept, emp where dept.deptno = emp.deptno group by dept.deptno;
-- (14). 列出各种工作的最低工资.
select job, min(sal) from emp group by job;
-- (15). 列出MANAGER的最低薪金.
select min(sal) from emp where job = 'MANAGER';
-- (16). 列出所有员工的年工资, 按年薪从低到高排序.
select ename, (ifnull(sal, 0.0) + ifnull(comm, 0.0)) * 12 as `annual salary` from emp order by `annual salary` desc;
