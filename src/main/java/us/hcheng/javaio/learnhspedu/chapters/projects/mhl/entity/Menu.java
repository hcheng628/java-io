package us.hcheng.javaio.learnhspedu.chapters.projects.mhl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private Integer id;
    private String name;
    private String type;
    private Double price;
    public String menuText() {
        return id + "\t\t\t" + name + "\t\t" + type + "\t\t" + price;
    }
}

/*
create table menu (
    id int primary key auto_increment, #自增主键，作为菜谱编号(唯一)
    name varchar(50) not null default '',#菜品名称
    type varchar(50) not null default '', #菜品种类
    price double not null default 0#价格
) character set utf8;
*/