/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : im

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-05-02 23:17:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_apply
-- ----------------------------
DROP TABLE IF EXISTS `tb_apply`;
CREATE TABLE `tb_apply` (
  `id` varchar(255) NOT NULL,
  `applicantId` varchar(255) DEFAULT NULL,
  `attach` text,
  `createdAt` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `targetId` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `updatedAt` datetime NOT NULL,
  `createAt` datetime NOT NULL,
  `updateAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9c6i8dqcsm3y1sk23xcwdjgby` (`applicantId`),
  CONSTRAINT `FK9c6i8dqcsm3y1sk23xcwdjgby` FOREIGN KEY (`applicantId`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_apply
-- ----------------------------

-- ----------------------------
-- Table structure for tb_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `id` varchar(255) NOT NULL,
  `createAt` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `ownerId` varchar(255) NOT NULL,
  `picture` varchar(255) NOT NULL,
  `updateAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq7tij6roe3v7vcwi235tncxv7` (`ownerId`),
  CONSTRAINT `FKq7tij6roe3v7vcwi235tncxv7` FOREIGN KEY (`ownerId`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_group
-- ----------------------------

-- ----------------------------
-- Table structure for tb_group_member
-- ----------------------------
DROP TABLE IF EXISTS `tb_group_member`;
CREATE TABLE `tb_group_member` (
  `id` varchar(255) NOT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `createAt` datetime NOT NULL,
  `groupId` varchar(255) NOT NULL,
  `notifyLevel` int(11) NOT NULL,
  `permissionType` int(11) NOT NULL,
  `updateAt` datetime NOT NULL,
  `userId` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr95872sjqqt1duwuqequglbob` (`groupId`),
  KEY `FKi9c4bppl14q0yxi51v6pbstpl` (`userId`),
  CONSTRAINT `FKi9c4bppl14q0yxi51v6pbstpl` FOREIGN KEY (`userId`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKr95872sjqqt1duwuqequglbob` FOREIGN KEY (`groupId`) REFERENCES `tb_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_group_member
-- ----------------------------

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `id` varchar(255) NOT NULL,
  `attach` varchar(255) DEFAULT NULL,
  `content` text NOT NULL,
  `createdAt` datetime NOT NULL,
  `groupId` varchar(255) DEFAULT NULL,
  `receiverId` varchar(255) DEFAULT NULL,
  `senderId` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `updatedAt` datetime NOT NULL,
  `createAt` datetime NOT NULL,
  `updateAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK324lh5xrmhvukar5w5vjohjsg` (`groupId`),
  KEY `FK6w4nf1is0lo6itrm62qh6fwm9` (`receiverId`),
  KEY `FKqno27bq3qbfpjq8ptfa1hry20` (`senderId`),
  CONSTRAINT `FK324lh5xrmhvukar5w5vjohjsg` FOREIGN KEY (`groupId`) REFERENCES `tb_group` (`id`),
  CONSTRAINT `FK6w4nf1is0lo6itrm62qh6fwm9` FOREIGN KEY (`receiverId`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKqno27bq3qbfpjq8ptfa1hry20` FOREIGN KEY (`senderId`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_message
-- ----------------------------

-- ----------------------------
-- Table structure for tb_push_history
-- ----------------------------
DROP TABLE IF EXISTS `tb_push_history`;
CREATE TABLE `tb_push_history` (
  `id` varchar(255) NOT NULL,
  `arrivalAt` datetime DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `entity` blob NOT NULL,
  `entityType` int(11) NOT NULL,
  `receiverId` varchar(255) NOT NULL,
  `receiverPushId` varchar(255) DEFAULT NULL,
  `senderId` varchar(255) DEFAULT NULL,
  `updateAt` datetime NOT NULL,
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd74cyeys8vpmo4rri4fgiqsad` (`receiverId`),
  KEY `FKqwq79iikbk4uwx6377igb5t7u` (`senderId`),
  CONSTRAINT `FKd74cyeys8vpmo4rri4fgiqsad` FOREIGN KEY (`receiverId`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKqwq79iikbk4uwx6377igb5t7u` FOREIGN KEY (`senderId`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_push_history
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(255) NOT NULL,
  `createAt` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lastReceivedAt` datetime DEFAULT NULL,
  `name` varchar(128) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(64) NOT NULL,
  `portrait` varchar(255) DEFAULT NULL,
  `pushId` varchar(255) DEFAULT NULL,
  `sex` int(11) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `updateAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nvlr3kdy2ky121gol63otka7p` (`name`),
  UNIQUE KEY `UK_4cgso11t7xt196pe2fnmqfyxa` (`phone`),
  UNIQUE KEY `UK_6fin1quj959u8hxyits8mgv6f` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('28cdff09-6b0c-4658-8435-571cfdec5c45', '2018-04-20 02:05:22', null, '2018-04-20 02:05:22', 'ye', 'ZWI5Mjc5OTgyMjI2YTQyYWZkZjI4NjBkYmRjMjliNDU=', '137', null, '小米8', '1237', 'YjBmNTRlNTMtZDNjMS00Nzc2LTk1NjAtZDI1OTkyNGU3Zjgw', '2018-04-20 06:28:12');
INSERT INTO `tb_user` VALUES ('b823678c-990c-4898-876d-de650b328a57', '2018-04-27 11:35:10', '哈哈', '2018-04-27 11:35:10', 'CCC', 'ZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2U=', '13726248899', 'http://csx0405.oss-cn-shenzhen.aliyuncs.com/portrait/201805/d9df6fd05ff79516dde5d0fbf42c7da8.jpg', '6d327919810cbdabab2b2ec1874508a4', '1', 'ZTFmYzZlNGItMWY0MC00YTk1LWI0MTgtMTdiYjVkOTBmNjMw', '2018-05-01 08:45:54');
INSERT INTO `tb_user` VALUES ('bddf9e60-66d4-41e7-ab5f-95fddf2004fb', '2018-04-26 15:39:09', null, '2018-04-26 15:39:09', '骨头汤', 'ZTMwNTZhNDA4ZGZlOTE2OGFjNWNjNDI1YWE5MTgxMzA=', '13378997788', '', null, '2', 'NjUyMDVmNWYtMzgxMS00MjcyLThiYjUtN2QzNDMxYWFlOWI1', '2018-04-26 15:39:09');
INSERT INTO `tb_user` VALUES ('b823678c-990c-4898-876d-de650b328a17', '2018-04-27 11:35:10', '哥1', '2018-04-27 11:35:10', 'aaa', 'ZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2U=', '13726248888', 'http://csx0405.oss-cn-shenzhen.aliyuncs.com/portrait/201805/d9df6fd05ff79516dde5d0fbf42c7da8.jpg', '6d327919810cbdabab2b2ec1874508a4', '1', '1', '2018-05-01 08:45:54');
INSERT INTO `tb_user` VALUES ('b823678c-990c-4898-876d-de650b328a27', '2018-04-27 11:35:10', '哥222', '2018-04-27 11:35:10', 'bbb', 'ZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2U=', '13726248889', 'http://csx0405.oss-cn-shenzhen.aliyuncs.com/portrait/201805/d9df6fd05ff79516dde5d0fbf42c7da8.jpg', '6d327919810cbdabab2b2ec1874508a4', '1', '2', '2018-05-01 08:45:54');
INSERT INTO `tb_user` VALUES ('b823678c-990c-4898-876d-de650b328a37', '2018-04-27 11:35:10', '3哥33', '2018-04-27 11:35:10', 'cdcc', 'ZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2U=', '13726248898', 'http://csx0405.oss-cn-shenzhen.aliyuncs.com/portrait/201805/d9df6fd05ff79516dde5d0fbf42c7da8.jpg', '6d327919810cbdabab2b2ec1874508a4', '1', '3', '2018-05-01 08:45:54');
INSERT INTO `tb_user` VALUES ('b823678c-990c-4898-876d-de650b328a47', '2018-04-27 11:35:10', '4哥44', '2018-04-27 11:35:10', 'dd', 'ZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2U=', '13726248897', 'http://csx0405.oss-cn-shenzhen.aliyuncs.com/portrait/201805/d9df6fd05ff79516dde5d0fbf42c7da8.jpg', '6d327919810cbdabab2b2ec1874508a4', '1', '4', '2018-05-01 08:45:54');
INSERT INTO `tb_user` VALUES ('b823678c-990c-4898-876d-de650b328a67', '2018-04-27 11:35:10', '5哥55', '2018-04-27 11:35:10', 'eeee', 'ZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2U=', '13726248896', 'http://csx0405.oss-cn-shenzhen.aliyuncs.com/portrait/201805/d9df6fd05ff79516dde5d0fbf42c7da8.jpg', '6d327919810cbdabab2b2ec1874508a4', '1', '5', '2018-05-01 08:45:54');

-- ----------------------------
-- Table structure for tb_user_follow
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_follow`;
CREATE TABLE `tb_user_follow` (
  `id` varchar(255) NOT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `createAt` datetime NOT NULL,
  `originId` varchar(255) NOT NULL,
  `targetId` varchar(255) NOT NULL,
  `updateAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhg1xm1knhy1j9yw8xq3m2susk` (`originId`),
  KEY `FK8g0jhnfd4p3alq4dx7i7sojou` (`targetId`),
  CONSTRAINT `FK8g0jhnfd4p3alq4dx7i7sojou` FOREIGN KEY (`targetId`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKhg1xm1knhy1j9yw8xq3m2susk` FOREIGN KEY (`originId`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_follow
-- ----------------------------
INSERT INTO `tb_user_follow` VALUES ('56023ae0-2e58-42f9-90dd-43d44b3e97fe', null, '2018-04-29 02:04:30', 'bddf9e60-66d4-41e7-ab5f-95fddf2004fb', 'b823678c-990c-4898-876d-de650b328a57', '2018-04-29 02:04:30');
INSERT INTO `tb_user_follow` VALUES ('9101fdfa-ccbd-43a4-9b24-a2f88ea43a64', null, '2018-05-01 12:05:17', '28cdff09-6b0c-4658-8435-571cfdec5c45', 'b823678c-990c-4898-876d-de650b328a57', '2018-05-01 12:05:17');
INSERT INTO `tb_user_follow` VALUES ('aed25e46-3787-403c-bd30-9ef192daef45', null, '2018-05-01 12:05:17', 'b823678c-990c-4898-876d-de650b328a57', '28cdff09-6b0c-4658-8435-571cfdec5c45', '2018-05-01 12:05:17');
INSERT INTO `tb_user_follow` VALUES ('fbf931df-1dfe-46fb-a807-e0c1e48193f4', null, '2018-04-29 02:04:30', 'b823678c-990c-4898-876d-de650b328a57', 'bddf9e60-66d4-41e7-ab5f-95fddf2004fb', '2018-04-29 02:04:30');
