/*
 Navicat Premium Data Transfer

 Source Server         : wz-rds
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : rm-bp1o147ihi96mpvl5o.mysql.rds.aliyuncs.com:3306
 Source Schema         : zeebe_monitor

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 23/09/2022 20:35:47
*/

CREATE database if NOT EXISTS `zeebe_monitor` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `zeebe_monitor`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_process
-- ----------------------------
DROP TABLE IF EXISTS `t_process`;
CREATE TABLE `t_process`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `process_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板名称',
  `process_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `process_definition_key` bigint(11) NOT NULL COMMENT '流程模板key',
  `bpmn_process_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'bpmn流程ID',
  `version` int(11) NOT NULL,
  `create_by` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `state` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否启用：0-未启用；1-启用；2-停用；3-废弃',
  `delete_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除；1-已删除',
  `bpmn_xml` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '模板文件详情',
  `inputs` json NULL COMMENT '入参',
  `outputs` json NULL COMMENT '出参',
  `webhook_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '可以通过该ID创建流程实例',
  `start_timer_expression` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定时任务表达式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_process_history
-- ----------------------------
DROP TABLE IF EXISTS `t_process_history`;
CREATE TABLE `t_process_history`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `process_id` bigint(11) NOT NULL COMMENT '流程模板id',
  `process_definition_key` bigint(11) NOT NULL COMMENT '流程模板key',
  `bpmn_xml` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '模板文件详情',
  `create_by` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 186 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程模板历史' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for t_service_task
-- ----------------------------
DROP TABLE IF EXISTS `t_service_task`;
CREATE TABLE `t_service_task`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `process_id` bigint(11) NOT NULL COMMENT '流程模板id',
  `service_task_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_type` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'zeebe节点的jobType',
  `is_delete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-未删除 1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for t_worker_bind
-- ----------------------------
DROP TABLE IF EXISTS `t_worker_bind`;
CREATE TABLE `t_worker_bind`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `process_id` bigint(11) NOT NULL COMMENT '流程模板id',
  `process_definition_key` bigint(11) NOT NULL COMMENT '流程模板key',
  `job_type` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'zeebe节点的jobType',
  `bind_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '绑定类型 1-api绑定',
  `platform_id` bigint(11) NULL DEFAULT NULL,
  `bind_api_id` bigint(11) NULL DEFAULT NULL COMMENT '绑定api的id',
  `if_use` tinyint(4) NOT NULL COMMENT '是否启用 0-未启用 1-启用',
  `create_by` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-未删除 1-已删除',
  `bind_api_group_id` bigint(11) NULL DEFAULT NULL COMMENT '绑定api的分组id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点绑定' ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;
