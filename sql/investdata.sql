/** 创建数据库**/
drop database if exists investdata;
create database investdata;
use investdata;

drop table if exists t_user;

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   username             char(30) not null comment '用户名',
   password             char(50) not null comment '密码',
   email                char(30) not null comment '邮箱',
   ispayer				int not null comment '是否付费用户 0-否 1-是',
   paydate				timestamp comment '付费时间',
   enddate				timestamp comment '截止时间',
   flag                 int not null comment '标志位 0-停用 1-启用',
   intime               timestamp not null comment '入库时间',
   primary key (username)
);

alter table t_user comment '用户信息表'; 

drop table if exists t_industry_category;

/*==============================================================*/
/* Table: t_industry_category                                   */
/*==============================================================*/
create table t_industry_category
(
   id                   int not null comment '流水号',
   name                 char(20) not null comment '行业名称',
   flag                 int not null comment '标志位 0-停用 1-启用',
   intime               timestamp not null comment '入库时间',
   primary key (id)
);

alter table t_industry_category comment '股票所属行业表';

/*初始化行业表数据 */
insert into t_industry_category values (1,'采掘',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (2,'传媒',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (3,'电气设备',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (4,'电子',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (5,'房地产',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (6,'纺织服装',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (7,'非银金融',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (8,'钢铁',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (9,'公用事业',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (10,'国防军工',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (11,'化工',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (12,'机械设备',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (13,'计算机',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (14,'家用电器',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (15,'建筑材料',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (16,'建筑装饰',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (17,'交通运输',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (18,'农林牧渔',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (19,'汽车',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (20,'轻工制造',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (21,'商业贸易',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (22,'食品饮料',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (23,'通信',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (24,'休闲服务',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (25,'医药生物',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (26,'银行',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (27,'有色金属',1,CURRENT_TIMESTAMP);
insert into t_industry_category values (28,'综合',1,CURRENT_TIMESTAMP);

