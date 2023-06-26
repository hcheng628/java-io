package us.hcheng.javaio.learnhspedu.mhl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    private Integer id;
    private String billId;
    private Integer menuId;
    private Integer nums;
    private Double money;
    private Integer diningTableId;
    private Date billDate;
    private String state;
}

/*
create table bill (
    id int primary key auto_increment, #自增主键
    billId varchar(50) not null default '',#账单号可以按照自己规则生成 UUID
    menuId int not null default 0,#菜品的编号, 也可以使用外键
    nums int not null default 0,#份数
    money double not null default 0, #金额
    diningTableId int not null default 0, #餐桌
    billDate datetime not null ,#订单日期
    state varchar(50) not null default '' # 状态 '未结账' , '已经结账', '挂单'
) character set utf8;
*/
