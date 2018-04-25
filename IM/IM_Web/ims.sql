

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema im
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema im
-- -----------------------------------------------------

-- CREATE SCHEMA IF NOT EXISTS im DEFAULT CHARACTER SET utf8 ;
USE im;

-- -----------------------------------------------------
-- Table im.`TB_USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS im.`TB_USER` (
  `id` VARCHAR(255) NOT NULL,
  `createAt` DATETIME NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `lastReceivedAt` DATETIME NULL DEFAULT NULL,
  `name` VARCHAR(128) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(64) NOT NULL,
  `portrait` VARCHAR(255) NULL DEFAULT NULL,
  `pushId` VARCHAR(255) NULL DEFAULT NULL,
  `sex` INT(11) NOT NULL,
  `token` VARCHAR(255) NULL DEFAULT NULL,
  `updateAt` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_nvlr3kdy2ky121gol63otka7p` (`name` ASC),
  UNIQUE INDEX `UK_4cgso11t7xt196pe2fnmqfyxa` (`phone` ASC),
  UNIQUE INDEX `UK_6fin1quj959u8hxyits8mgv6f` (`token` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table im.`TB_APPLY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS im.`TB_APPLY` (
  `id` VARCHAR(255) NOT NULL,
  `applicantId` VARCHAR(255) NULL DEFAULT NULL,
  `attach` TEXT NULL DEFAULT NULL,
  `createdAt` DATETIME NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `targetId` VARCHAR(255) NOT NULL,
  `type` INT(11) NOT NULL,
  `updatedAt` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK9c6i8dqcsm3y1sk23xcwdjgby` (`applicantId` ASC),
  CONSTRAINT `FK9c6i8dqcsm3y1sk23xcwdjgby`
    FOREIGN KEY (`applicantId`)
    REFERENCES im.`TB_USER` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table im.`TB_GROUP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS im.`TB_GROUP` (
  `id` VARCHAR(255) NOT NULL,
  `createAt` DATETIME NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `ownerId` VARCHAR(255) NOT NULL,
  `picture` VARCHAR(255) NOT NULL,
  `updateAt` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKq7tij6roe3v7vcwi235tncxv7` (`ownerId` ASC),
  CONSTRAINT `FKq7tij6roe3v7vcwi235tncxv7`
    FOREIGN KEY (`ownerId`)
    REFERENCES im.`TB_USER` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table im.`TB_GROUP_MEMBER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS im.`TB_GROUP_MEMBER` (
  `id` VARCHAR(255) NOT NULL,
  `alias` VARCHAR(255) NULL DEFAULT NULL,
  `createAt` DATETIME NOT NULL,
  `groupId` VARCHAR(255) NOT NULL,
  `notifyLevel` INT(11) NOT NULL,
  `permissionType` INT(11) NOT NULL,
  `updateAt` DATETIME NOT NULL,
  `userId` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKr95872sjqqt1duwuqequglbob` (`groupId` ASC),
  INDEX `FKi9c4bppl14q0yxi51v6pbstpl` (`userId` ASC),
  CONSTRAINT `FKi9c4bppl14q0yxi51v6pbstpl`
    FOREIGN KEY (`userId`)
    REFERENCES im.`TB_USER` (`id`),
  CONSTRAINT `FKr95872sjqqt1duwuqequglbob`
    FOREIGN KEY (`groupId`)
    REFERENCES im.`TB_GROUP` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table im.`TB_MESSAGE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS im.`TB_MESSAGE` (
  `id` VARCHAR(255) NOT NULL,
  `attach` VARCHAR(255) NULL DEFAULT NULL,
  `content` TEXT NOT NULL,
  `createdAt` DATETIME NOT NULL,
  `groupId` VARCHAR(255) NULL DEFAULT NULL,
  `receiverId` VARCHAR(255) NULL DEFAULT NULL,
  `senderId` VARCHAR(255) NOT NULL,
  `type` INT(11) NOT NULL,
  `updatedAt` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK324lh5xrmhvukar5w5vjohjsg` (`groupId` ASC),
  INDEX `FK6w4nf1is0lo6itrm62qh6fwm9` (`receiverId` ASC),
  INDEX `FKqno27bq3qbfpjq8ptfa1hry20` (`senderId` ASC),
  CONSTRAINT `FK324lh5xrmhvukar5w5vjohjsg`
    FOREIGN KEY (`groupId`)
    REFERENCES im.`TB_GROUP` (`id`),
  CONSTRAINT `FK6w4nf1is0lo6itrm62qh6fwm9`
    FOREIGN KEY (`receiverId`)
    REFERENCES im.`TB_USER` (`id`),
  CONSTRAINT `FKqno27bq3qbfpjq8ptfa1hry20`
    FOREIGN KEY (`senderId`)
    REFERENCES im.`TB_USER` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table im.`TB_PUSH_HISTORY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS im.`TB_PUSH_HISTORY` (
  `id` VARCHAR(255) NOT NULL,
  `arrivalAt` DATETIME NULL DEFAULT NULL,
  `createdAt` DATETIME NOT NULL,
  `entity` BLOB NOT NULL,
  `entityType` INT(11) NOT NULL,
  `receiverId` VARCHAR(255) NOT NULL,
  `receiverPushId` VARCHAR(255) NULL DEFAULT NULL,
  `senderId` VARCHAR(255) NULL DEFAULT NULL,
  `updateAt` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKd74cyeys8vpmo4rri4fgiqsad` (`receiverId` ASC),
  INDEX `FKqwq79iikbk4uwx6377igb5t7u` (`senderId` ASC),
  CONSTRAINT `FKd74cyeys8vpmo4rri4fgiqsad`
    FOREIGN KEY (`receiverId`)
    REFERENCES im.`TB_USER` (`id`),
  CONSTRAINT `FKqwq79iikbk4uwx6377igb5t7u`
    FOREIGN KEY (`senderId`)
    REFERENCES im.`TB_USER` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table im.`TB_USER_FOLLOW`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS im.`TB_USER_FOLLOW` (
  `id` VARCHAR(255) NOT NULL,
  `alias` VARCHAR(255) NULL DEFAULT NULL,
  `createAt` DATETIME NOT NULL,
  `originId` VARCHAR(255) NOT NULL,
  `targetId` VARCHAR(255) NOT NULL,
  `updateAt` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKhg1xm1knhy1j9yw8xq3m2susk` (`originId` ASC),
  INDEX `FK8g0jhnfd4p3alq4dx7i7sojou` (`targetId` ASC),
  CONSTRAINT `FK8g0jhnfd4p3alq4dx7i7sojou`
    FOREIGN KEY (`targetId`)
    REFERENCES im.`TB_USER` (`id`),
  CONSTRAINT `FKhg1xm1knhy1j9yw8xq3m2susk`
    FOREIGN KEY (`originId`)
    REFERENCES im.`TB_USER` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
