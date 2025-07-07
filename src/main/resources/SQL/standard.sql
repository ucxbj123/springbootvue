/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : springbootvue

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 07/07/2025 22:32:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for testproject
-- ----------------------------
DROP TABLE IF EXISTS `testproject`;
CREATE TABLE `testproject`  (
                                `ID` int(5) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                `project` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目',
                                `StandardCode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验标准编码',
                                `StandardName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检验标准名称',
                                `checkLength` int(5) NULL DEFAULT NULL COMMENT '长度',
                                `isEnabled` bit(1) NULL DEFAULT NULL COMMENT '是否启用',
                                `CreateTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                `CreateUser` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
                                PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testproject
-- ----------------------------
INSERT INTO `testproject` VALUES (1, '测试0202', 'Dream-2023020200001', '测试1', 27, b'1', '2023-02-02 20:39:51', 'admin');

SET FOREIGN_KEY_CHECKS = 1;


/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : springbootvue

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 07/07/2025 22:33:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for teststandard
-- ----------------------------
DROP TABLE IF EXISTS `teststandard`;
CREATE TABLE `teststandard`  (
                                 `ID` int(5) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                 `StandardCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '检验标准编号',
                                 `StandardName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检验标准名称',
                                 `StandardProject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检验项目',
                                 `CheckStartPosition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检验起始位置',
                                 `StandardLength` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '长度',
                                 `CheckCondition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检验条件1',
                                 `CheckValue` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参考值',
                                 `LogicValue` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '逻辑值',
                                 `CheckCondition1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检验条件2',
                                 `CheckValue1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参考值2',
                                 `IsCheck` bit(1) NULL DEFAULT NULL COMMENT '是否校验',
                                 `UniqueCheck` bit(1) NULL DEFAULT NULL COMMENT '唯一校验',
                                 `ValueType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '值类型',
                                 `IsEnabled` bit(1) NULL DEFAULT NULL COMMENT '是否启用',
                                 `CreateTime` datetime(0) NOT NULL COMMENT '创建时间',
                                 `CreateUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
                                 PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teststandard
-- ----------------------------
INSERT INTO `teststandard` VALUES (1, 'Dream-2023020200001', '测试1', '料号', '1', '10', '=', '51680589**', '', '', '', b'1', b'0', 'string', b'1', '2023-02-02 20:50:07', 'admin');
INSERT INTO `teststandard` VALUES (2, 'Dream-2023020200001', '测试1', '厂商', '11', '1', '=', 'E', '', '', '', b'1', b'0', 'string', b'1', '2023-02-02 20:50:07', 'admin');
INSERT INTO `teststandard` VALUES (3, 'Dream-2023020200001', '测试1', '日期', '12', '6', '>', '220216', '', '', '', b'1', b'0', 'int', b'1', '2023-02-02 20:50:07', 'admin');
INSERT INTO `teststandard` VALUES (4, 'Dream-2023020200001', '测试1', '年', '12', '2', '=', '22', '', '', '', b'1', b'0', 'int', b'1', '2023-02-02 20:50:07', 'admin');
INSERT INTO `teststandard` VALUES (5, 'Dream-2023020200001', '测试1', '月', '14', '2', '>=', '06', 'and', '<=', '12', b'1', b'0', 'int', b'1', '2023-02-02 20:50:07', 'admin');
INSERT INTO `teststandard` VALUES (6, 'Dream-2023020200001', '测试1', '日', '16', '2', '>=', '01', 'and', '<=', '31', b'1', b'0', 'int', b'1', '2023-02-02 20:50:07', 'admin');
INSERT INTO `teststandard` VALUES (7, 'Dream-2023020200001', '测试1', '版本', '18', '2', '=', 'YH', '', '', '', b'1', b'0', 'string', b'1', '2023-02-02 20:50:07', 'admin');
INSERT INTO `teststandard` VALUES (8, 'Dream-2023020200001', '测试1', '供应商', '20', '3', '=', 'B1A', '', '', '', b'1', b'0', 'string', b'1', '2023-02-02 20:50:07', 'admin');
INSERT INTO `teststandard` VALUES (9, 'Dream-2023020200001', '测试1', '流水号', '23', '5', '>=', '0', 'and', '<=', '99999', b'1', b'0', 'int', b'1', '2023-02-02 20:50:07', 'admin');

SET FOREIGN_KEY_CHECKS = 1;

