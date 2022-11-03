/*
 Navicat Premium Data Transfer

 Source Server         : wz-rds
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : rm-bp1o147ihi96mpvl5o.mysql.rds.aliyuncs.com:3306
 Source Schema         : platform_linker

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 23/09/2022 19:47:59
*/

use `platform_linker`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
 --------------------------v0.15 Oracle start -------------------------------------

-- 参数注释
alter table active_directory_param modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table active_directory_result modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table outbound_api_param modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table outbound_api_result modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table redis_param modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table redis_result modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table relational_database_param modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table relational_database_result modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table repository_mysql_param modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table repository_mysql_result modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table simple_param modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table simple_result modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table subflow_param modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table subflow_result modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table object_fields modify label_name varchar(20) null COMMENT '字段展示中文名称';
alter table platform_common_param modify label_name varchar(20) null COMMENT '字段展示中文名称';

drop index uniq_lable_name on active_directory_param ;
drop index uniq_label_name on active_directory_result ;
drop index uniq_label_name on outbound_api_param ;
drop index uniq_label_name on outbound_api_result ;
drop index uniq_lable_name on redis_param ;
drop index uniq_label_name on redis_result ;
drop index uniq_lable_name on relational_database_param ;
drop index uniq_label_name on relational_database_result ;
drop index uniq_lable_name on repository_mysql_param ;
drop index uniq_label_name on repository_mysql_result ;
drop index uniq_lable_name on simple_param ;
drop index uniq_label_name on simple_result ;
drop index uniq_lable_name on subflow_param ;
drop index uniq_label_name on subflow_result ;
drop index uniq_field_name on object_fields ;
commit;


-- 删除对象类型
alter table objects drop object_type;
-- 对象管理 Object
alter table object_fields add column field_obj varchar(50) NULL comment 'Object类型对象名' after field_code;
alter table object_fields add column field_level varchar(50) NULL comment '字段层级' after basic;
alter table object_fields add column belongs_to varchar(50) NULL comment '所属于哪个字段' after field_length;
commit;
update object_fields set belongs_to = '0';
update object_fields set field_level = '1';

-- 数据源分组
alter table db_server add column group_id varchar(20) NOT NULL comment '分组Id' after platform_id;

INSERT INTO `inbound_api_group` ( `group_id`, `group_name`, `group_prefix`, `enabled`, `remark`, `created_at`, `created_by`, `updated_at`, `updated_by`, `deleted`) VALUES (1549652647643115521, '数据库默认分组', 'rdb', 1, NULL, '2022-07-20 15:09:17', 'admin', '2022-07-20 15:09:17', 'admin', 0);
commit;
update db_server set group_id = '1549652647643115521';


-- rdb 增加url
alter table relational_database add column url varchar(2000) NOT NULL comment '暴露Url' after relational_database_name;
commit;

update relational_database set url = CONCAT('/database/query/', right(cast(relational_database_id  as char(20)),8)) where url ='';
commit;

-- outbound_api
update outbound_api set status = 20 where status = 40;

--删除索引
DROP INDEX uniq_subflow_component_name ON subflow_component;
DROP INDEX uniq_inbound_api_param_id ON inbound_api_param;
DROP INDEX uniq_inbound_api_result_id ON inbound_api_result;
commit;

create unique index uniq_inbound_api_param_id on inbound_api_param(inbound_api_id, inbound_api_param_id);
create unique index uniq_inbound_api_result_id on inbound_api_result(inbound_api_id, inbound_api_result_id);

--字典
INSERT INTO  `sys_dict_type` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_type_id`, `name`, `type`, `enabled`, `sort`, `deleted`) VALUES ('2022-09-19 09:57:02', 'admin', '2022-09-19 09:57:02', 'admin', '快速录入DB资源', 1571679727608090624, '数据库操作类型', 'DML_TYPE', 1, 1, 0);

INSERT INTO  `sys_dict_data` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ('2022-09-19 09:57:52', 'admin', '2022-09-29 11:26:36', 'admin', '查询DB资源', 1571679938661273600, 1, '查询字段', 3, 1, 0, 'DML_TYPE');
INSERT INTO  `sys_dict_data` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ('2022-09-19 09:58:12', 'admin', '2022-09-29 11:26:19', 'admin', '新增DB资源', 1571680019695226880, 2, '新增字段', 1, 1, 0, 'DML_TYPE');
INSERT INTO  `sys_dict_data` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ('2022-09-19 09:58:19', 'admin', '2022-09-29 11:26:26', 'admin', '修改DB资源', 1571680051332861952, 3, '修改字段', 2, 1, 0, 'DML_TYPE');
commit;

DROP INDEX uniq_field ON object_fields;
commit;
create unique index uniq_field_code on object_fields(object_id,field_code,belongs_to, deleted);
commit;
---------------------------------------------------------------


--------------------------FTP-------------------------------------

-- FTP
CREATE TABLE `ftp_server` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` bigint(20) NOT NULL COMMENT '分组Id',
  `server_id` bigint(20) NOT NULL COMMENT 'FTP ID',
  `server_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'FTP名称',
  `server_type` tinyint(4) NOT NULL COMMENT '协议类型1:ftp ',
  `server_addr` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'FTP地址',
  `server_port` int(10) NOT NULL COMMENT '端口',
  `account` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `pwd` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注',
  `deleted` bigint(20) DEFAULT '0' COMMENT '0-未删除；时间戳-已删除',
  `created_at` datetime NOT NULL COMMENT '创建时刻',
  `created_by` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '\n创建者',
  `updated_at` datetime NOT NULL COMMENT '更新时刻',
  `updated_by` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `server_name_uniq` (`server_name`,`deleted`) USING BTREE,
  UNIQUE KEY `server_addr_uniq` (`server_addr`,`server_port`,`account`,`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ftp服务信息表';

CREATE TABLE `ftp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_id` bigint(20) NOT NULL COMMENT 'FTP服务ID',
  `ftp_id` bigint(20) NOT NULL COMMENT 'FTP组件ID',
  `ftp_code` varchar(32) NOT NULL COMMENT 'FTP组件名称',
  `ftp_name` varchar(50) NOT NULL COMMENT 'FTP资源中文名称',
  `ftp_type` tinyint(4) NOT NULL COMMENT '组件类型： 1 - 上传 2 - 下载 3 - 文件监听',
  `upload_del` tinyint(4) DEFAULT NULL COMMENT '上传成功后的操作：true - 删除 false - 保留',
  `url` varchar(2000) NOT NULL COMMENT '暴露Url',
  `enabled` tinyint(4) NOT NULL COMMENT '状态 0 - 停用 1 - 启用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `deleted` bigint(20) DEFAULT '0' COMMENT '0-未删除；时间戳-已删除',
  `created_at` datetime NOT NULL COMMENT '创建时刻',
  `created_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `updated_at` datetime NOT NULL COMMENT '更新时刻',
  `updated_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_ftp_code` (`server_id`,`ftp_code`,`deleted`) USING BTREE,
  UNIQUE KEY `uniq_ftp_name` (`server_id`,`ftp_name`,`deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COMMENT='FTP组件信息表';

CREATE TABLE `ftp_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `ftp_id` bigint(20) NOT NULL COMMENT 'FTP组件ID',
  `ftp_param_id` bigint(20) DEFAULT NULL COMMENT '入参ID',
  `field_code` varchar(50) NOT NULL COMMENT '字段名称(变量名)',
  `label_name` varchar(20) DEFAULT NULL COMMENT '字段展示中文名称',
  `field_type` varchar(50) NOT NULL COMMENT '字段类型',
  `field_obj` varchar(50) DEFAULT NULL COMMENT 'Object类型对象名',
  `collective` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为数组： 0- 不是数组 1- 是',
  `basic` tinyint(4) NOT NULL COMMENT '是否是基本类型：0- 对象1- 基本类型',
  `field_length` int(11) DEFAULT NULL COMMENT '长度：string类型，长度必填',
  `default_value` varchar(500) DEFAULT NULL COMMENT '默认值',
  `mandatory` tinyint(4) DEFAULT NULL COMMENT '是否必须',
  `arg_order` tinyint(4) DEFAULT NULL COMMENT '参数顺序',
  `field_level` tinyint(4) DEFAULT '1' COMMENT '字段层级 DEFAULT ''1''',
  `belongs_to` bigint(20) DEFAULT '0' COMMENT '所属于哪个字段 DEFAULT ''0''',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_field_code` (`ftp_id`,`field_code`,`belongs_to`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='FTP组件入参数表';

CREATE TABLE `ftp_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ftp_result_id` bigint(20) DEFAULT NULL COMMENT '出参ID',
  `ftp_id` bigint(20) NOT NULL COMMENT 'FTP组件ID',
  `field_code` varchar(50) NOT NULL COMMENT '字段名称(变量名)',
  `label_name` varchar(20) DEFAULT NULL COMMENT '字段展示中文名称',
  `field_type` varchar(50) NOT NULL COMMENT '字段类型',
  `field_obj` varchar(50) DEFAULT NULL COMMENT 'Object类型对象名',
  `collective` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为数组： 0- 不是数组 1- 是',
  `basic` tinyint(4) NOT NULL COMMENT '是否是基本类型：0- 对象1- 基本类型',
  `field_expression` varchar(255) DEFAULT NULL COMMENT '表达式',
  `arg_order` tinyint(4) DEFAULT NULL COMMENT '参数顺序',
  `field_level` tinyint(4) DEFAULT '1' COMMENT '字段层级 DEFAULT ''1''',
  `belongs_to` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属于哪个字段 DEFAULT ''0''',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_field_code` (`ftp_id`,`field_code`,`belongs_to`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='FTP组件返回参数表';


-- FLow FTP
INSERT INTO `sys_dict_data` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ('2022-06-27 02:51:02', 'admin', '2022-06-27 02:51:02', 'admin', NULL, 1541252735768420352, 8, 'FTP组件', 8, 1, 0, 'FLOW_COMPONENT_TYPE');

INSERT INTO `sys_dict_data` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-09-19 09:57:52', 'admin', '2022-09-29 11:26:36', 'admin', '查询DB资源', 1571679938661273600, 1, '查询字段', 3, 1, 0, 'DML_TYPE');
INSERT INTO `sys_dict_data` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-09-19 09:58:12', 'admin', '2022-09-29 11:26:19', 'admin', '新增DB资源', 1571680019695226880, 2, '新增字段', 1, 1, 0, 'DML_TYPE');
INSERT INTO `sys_dict_data` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-09-19 09:58:19', 'admin', '2022-09-29 11:26:26', 'admin', '修改DB资源', 1571680051332861952, 3, '修改字段', 2, 1, 0, 'DML_TYPE');
INSERT INTO `sys_dict_data` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ('2022-09-19 09:58:19', 'admin', '2022-09-29 11:26:26', 'admin', '批量查询DB资源', 1571680051332861953, 4, '批量查询字段', 4, 1, 0, 'DML_TYPE');

--
INSERT INTO `sys_menu` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`, `external_link`, `deleted`) VALUES (171, '2022-10-10 15:35:10', 'admin', '2022-10-10 15:47:29', 'admin', NULL, 'FTP数据源', 59, 1, 'Ftp', 'ant-design:node-collapse-outlined', 5, 1, '/sys/vben/integrate/resourceManage/ftpDataSource/index', '', 0);

INSERT INTO `sys_menu` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`, `external_link`, `deleted`) VALUES ( '2022-06-27 03:04:34', 'admin', '2022-06-27 03:04:34', 'admin', NULL, '查询', 171, 2, 'Ftp:retrieve', NULL, 1, 1, NULL, '', 0);
INSERT INTO `sys_menu` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`, `external_link`, `deleted`) VALUES ('2022-06-27 03:05:08', 'admin', '2022-06-27 03:05:08', 'admin', NULL, '新增', 171, 2, 'Ftp:create', NULL, 2, 1, NULL, '', 0);
INSERT INTO `sys_menu` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`, `external_link`, `deleted`) VALUES ('2022-06-27 03:05:57', 'admin', '2022-06-27 03:05:57', 'admin', NULL, '编辑', 171, 2, 'Ftp:update', NULL, 3, 1, NULL, '', 0);
INSERT INTO `sys_menu` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`, `external_link`, `deleted`) VALUES ( '2022-06-27 03:05:57', 'admin', '2022-06-27 03:05:57', 'admin', NULL, '删除', 171, 2, 'Ftp:delete', NULL, 4, 1, NULL, '', 0);


-- FTP
INSERT INTO `sys_dict_type` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_type_id`, `name`, `type`, `enabled`, `sort`, `deleted`) VALUES ('2022-10-24 10:16:33', 'admin', '2022-10-24 10:16:33', 'admin', 'FTP资源类型', 1584368215714525184, 'FTP类型', 'FTP_TYPE', 1, 1, 0);

INSERT INTO `sys_dict_data` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-10-24 10:17:15', 'admin', '2022-10-24 10:17:15', 'admin', NULL, 1584368391481028608, 1, 'FTP上传', 1, 1, 0, 'FTP_TYPE');
INSERT INTO `sys_dict_data` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-10-24 10:17:31', 'admin', '2022-10-24 10:17:31', 'admin', NULL, 1584368455389638656, 2, 'FTP下载', 2, 1, 0, 'FTP_TYPE');
INSERT INTO `sys_dict_data` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-10-24 10:17:44', 'admin', '2022-10-24 10:17:44', 'admin', NULL, 1584368513212313600, 3, 'FTP监听', 3, 1, 0, 'FTP_TYPE');

commit;
--------------------- v0.15 Oracle end  ------------------------------------------

-------------------------- v0.16 FTP start -------------------------------------

-- FTP
CREATE TABLE `ftp_server` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` bigint(20) NOT NULL COMMENT '分组Id',
  `server_id` bigint(20) NOT NULL COMMENT 'FTP ID',
  `server_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'FTP名称',
  `server_type` tinyint(4) NOT NULL COMMENT '协议类型1:ftp ',
  `server_addr` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'FTP地址',
  `server_port` int(10) NOT NULL COMMENT '端口',
  `account` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `pwd` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注',
  `deleted` bigint(20) DEFAULT '0' COMMENT '0-未删除；时间戳-已删除',
  `created_at` datetime NOT NULL COMMENT '创建时刻',
  `created_by` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '\n创建者',
  `updated_at` datetime NOT NULL COMMENT '更新时刻',
  `updated_by` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `server_name_uniq` (`server_name`,`deleted`) USING BTREE,
  UNIQUE KEY `server_addr_uniq` (`server_addr`,`server_port`,`account`,`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ftp服务信息表';

CREATE TABLE `ftp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_id` bigint(20) NOT NULL COMMENT 'FTP服务ID',
  `ftp_id` bigint(20) NOT NULL COMMENT 'FTP组件ID',
  `ftp_code` varchar(32) NOT NULL COMMENT 'FTP组件名称',
  `ftp_name` varchar(50) NOT NULL COMMENT 'FTP资源中文名称',
  `ftp_type` tinyint(4) NOT NULL COMMENT '组件类型： 1 - 上传 2 - 下载 3 - 文件监听',
  `upload_del` tinyint(4) DEFAULT NULL COMMENT '上传成功后的操作：true - 删除 false - 保留',
  `url` varchar(2000) NOT NULL COMMENT '暴露Url',
  `enabled` tinyint(4) NOT NULL COMMENT '状态 0 - 停用 1 - 启用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `deleted` bigint(20) DEFAULT '0' COMMENT '0-未删除；时间戳-已删除',
  `created_at` datetime NOT NULL COMMENT '创建时刻',
  `created_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `updated_at` datetime NOT NULL COMMENT '更新时刻',
  `updated_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_ftp_code` (`server_id`,`ftp_code`,`deleted`) USING BTREE,
  UNIQUE KEY `uniq_ftp_name` (`server_id`,`ftp_name`,`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='FTP组件信息表';

CREATE TABLE `ftp_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `ftp_id` bigint(20) NOT NULL COMMENT 'FTP组件ID',
  `ftp_param_id` bigint(20) DEFAULT NULL COMMENT '入参ID',
  `field_code` varchar(50) NOT NULL COMMENT '字段名称(变量名)',
  `label_name` varchar(20) DEFAULT NULL COMMENT '字段展示中文名称',
  `field_type` varchar(50) NOT NULL COMMENT '字段类型',
  `field_obj` varchar(50) DEFAULT NULL COMMENT 'Object类型对象名',
  `collective` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为数组： 0- 不是数组 1- 是',
  `basic` tinyint(4) NOT NULL COMMENT '是否是基本类型：0- 对象1- 基本类型',
  `field_length` int(11) DEFAULT NULL COMMENT '长度：string类型，长度必填',
  `default_value` varchar(500) DEFAULT NULL COMMENT '默认值',
  `mandatory` tinyint(4) DEFAULT NULL COMMENT '是否必须',
  `arg_order` tinyint(4) DEFAULT NULL COMMENT '参数顺序',
  `field_level` tinyint(4) DEFAULT '1' COMMENT '字段层级 DEFAULT ''1''',
  `belongs_to` bigint(20) DEFAULT '0' COMMENT '所属于哪个字段 DEFAULT ''0''',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_field_code` (`ftp_id`,`field_code`,`belongs_to`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='FTP组件入参数表';

CREATE TABLE `ftp_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ftp_result_id` bigint(20) DEFAULT NULL COMMENT '出参ID',
  `ftp_id` bigint(20) NOT NULL COMMENT 'FTP组件ID',
  `field_code` varchar(50) NOT NULL COMMENT '字段名称(变量名)',
  `label_name` varchar(20) DEFAULT NULL COMMENT '字段展示中文名称',
  `field_type` varchar(50) NOT NULL COMMENT '字段类型',
  `field_obj` varchar(50) DEFAULT NULL COMMENT 'Object类型对象名',
  `collective` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为数组： 0- 不是数组 1- 是',
  `basic` tinyint(4) NOT NULL COMMENT '是否是基本类型：0- 对象1- 基本类型',
  `field_expression` varchar(255) DEFAULT NULL COMMENT '表达式',
  `arg_order` tinyint(4) DEFAULT NULL COMMENT '参数顺序',
  `field_level` tinyint(4) DEFAULT '1' COMMENT '字段层级 DEFAULT ''1''',
  `belongs_to` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属于哪个字段 DEFAULT ''0''',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_field_code` (`ftp_id`,`field_code`,`belongs_to`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='FTP组件返回参数表';


-- FLow FTP
INSERT INTO `sys_dict_data` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ('2022-06-27 02:51:02', 'admin', '2022-06-27 02:51:02', 'admin', NULL, 1541252735768420352, 8, 'FTP组件', 8, 1, 0, 'FLOW_COMPONENT_TYPE');

INSERT INTO `sys_dict_data` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-09-19 09:57:52', 'admin', '2022-09-29 11:26:36', 'admin', '查询DB资源', 1571679938661273600, 1, '查询字段', 3, 1, 0, 'DML_TYPE');
INSERT INTO `sys_dict_data` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-09-19 09:58:12', 'admin', '2022-09-29 11:26:19', 'admin', '新增DB资源', 1571680019695226880, 2, '新增字段', 1, 1, 0, 'DML_TYPE');
INSERT INTO `sys_dict_data` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-09-19 09:58:19', 'admin', '2022-09-29 11:26:26', 'admin', '修改DB资源', 1571680051332861952, 3, '修改字段', 2, 1, 0, 'DML_TYPE');
INSERT INTO `sys_dict_data` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ('2022-09-19 09:58:19', 'admin', '2022-09-29 11:26:26', 'admin', '批量查询DB资源', 1571680051332861953, 4, '批量查询字段', 4, 1, 0, 'DML_TYPE');

--  Ftp菜单
INSERT INTO `sys_menu` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`, `external_link`, `deleted`) VALUES (171, '2022-10-10 15:35:10', 'admin', '2022-10-10 15:47:29', 'admin', NULL, 'FTP数据源', 59, 1, 'Ftp', 'ant-design:node-collapse-outlined', 5, 1, '/sys/vben/integrate/resourceManage/ftpDataSource/index', '', 0);

INSERT INTO `sys_menu` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`, `external_link`, `deleted`) VALUES ( '2022-06-27 03:04:34', 'admin', '2022-06-27 03:04:34', 'admin', NULL, '查询', 171, 2, 'Ftp:retrieve', NULL, 1, 1, NULL, '', 0);
INSERT INTO `sys_menu` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`, `external_link`, `deleted`) VALUES ('2022-06-27 03:05:08', 'admin', '2022-06-27 03:05:08', 'admin', NULL, '新增', 171, 2, 'Ftp:create', NULL, 2, 1, NULL, '', 0);
INSERT INTO `sys_menu` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`, `external_link`, `deleted`) VALUES ('2022-06-27 03:05:57', 'admin', '2022-06-27 03:05:57', 'admin', NULL, '编辑', 171, 2, 'Ftp:update', NULL, 3, 1, NULL, '', 0);
INSERT INTO `sys_menu` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `title`, `parent_id`, `type`, `permission`, `icon`, `sort`, `status`, `component`, `external_link`, `deleted`) VALUES ( '2022-06-27 03:05:57', 'admin', '2022-06-27 03:05:57', 'admin', NULL, '删除', 171, 2, 'Ftp:delete', NULL, 4, 1, NULL, '', 0);


-- FTP 字典
INSERT INTO `sys_dict_type` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_type_id`, `name`, `type`, `enabled`, `sort`, `deleted`) VALUES ('2022-10-24 10:16:33', 'admin', '2022-10-24 10:16:33', 'admin', 'FTP资源类型', 1584368215714525184, 'FTP类型', 'FTP_TYPE', 1, 1, 0);

INSERT INTO `sys_dict_data` (`created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-10-24 10:17:15', 'admin', '2022-10-24 10:17:15', 'admin', NULL, 1584368391481028608, 1, 'FTP上传', 1, 1, 0, 'FTP_TYPE');
INSERT INTO `sys_dict_data` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-10-24 10:17:31', 'admin', '2022-10-24 10:17:31', 'admin', NULL, 1584368455389638656, 2, 'FTP下载', 2, 1, 0, 'FTP_TYPE');
INSERT INTO `sys_dict_data` ( `created_at`, `created_by`, `updated_at`, `updated_by`, `remark`, `dict_data_id`, `value`, `label`, `sort`, `enabled`, `deleted`, `type`) VALUES ( '2022-10-24 10:17:44', 'admin', '2022-10-24 10:17:44', 'admin', NULL, 1584368513212313600, 3, 'FTP监听', 3, 1, 0, 'FTP_TYPE');

commit;
-------------------------- v0.16 FTP start -------------------------------------



SET FOREIGN_KEY_CHECKS = 1;
