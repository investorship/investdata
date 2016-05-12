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