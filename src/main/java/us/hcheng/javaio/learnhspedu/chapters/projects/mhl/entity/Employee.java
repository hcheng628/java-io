package us.hcheng.javaio.learnhspedu.chapters.projects.mhl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String empId;
    private String pwd;
    private String name;
    private String job;
}

/*
create table employee (
    id int primary key auto_increment, #自增
    empId varchar(50) not null default '',#员工号
    pwd char(41) not null default '',#密码password()
    name varchar(50) not null default '',#姓名
    job varchar(50) not null default '' #岗位
) character set utf8;
*/
