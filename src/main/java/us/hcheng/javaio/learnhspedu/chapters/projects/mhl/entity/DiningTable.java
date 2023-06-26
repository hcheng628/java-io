package us.hcheng.javaio.learnhspedu.chapters.projects.mhl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiningTable {
    private Integer id;
    private String state;
    private String bookedBy;
    private String bookedByTel;
}

/*
create table dining_table(
    id int primary key auto_increment, #自增, 表示餐桌编号
    state varchar(20) not null default '',#餐桌的状态
    bookedby varchar(50) not null default '',#预订人的名字
    bookedbytel varchar(20) not null default ''
) character set utf8;
*/
