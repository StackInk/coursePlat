
-- 权限控制表

CREATE TABLE `acl_permission` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pid` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '所属上级',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '类型(1:菜单,2:按钮)',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `path` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限';


CREATE TABLE `acl_role` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE `acl_role_permission` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色ID',
  `permission_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '权限ID',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系表';


CREATE TABLE `acl_user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `wx_open_id` varchar(20) NOT NULL DEFAULT '' COMMENT '微信OpenId，后续扩展',
  `wx_secret_key` varchar(20) NOT NULL DEFAULT '' COMMENT '微信用户密钥，后续扩展',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_password` (`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';


CREATE TABLE `acl_user_role` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色id',
  `user_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

-- 教师表
CREATE TABLE `zl_teacher` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID' ,
    `uid` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '账户ID',
    `name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '教师姓名',
    `description` text NULL  COMMENT '教师简介',
    `rank` tinyint(1) NOT NULL DEFAULT 0 COMMENT '教师职称',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `gmt_create` datetime NOT NULL COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';

-- 课程表
CREATE TABLE `zl_course` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` varchar(20) NOT NULL DEFAULT '' COMMENT '课程名称',
    `type` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '课程类型(0->智慧树,1->慕课,2->线下)',
    `place` varchar(20) NOT NULL DEFAULT '' COMMENT '课程上课地址',
    `stock` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '课程库存',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `gmt_create` datetime NOT NULL COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 教师，课程关系表
CREATE TABLE `zl_course_teacher` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `cid` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '课程ID',
    `tid` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '教师ID',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `gmt_create` datetime NOT NULL COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师，课程关系表';


-- 学生表
CREATE TABLE `zl_student` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uid` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户ID',
    `name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '学生姓名',
    `department` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '院系',
    `major` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '专业',
    `credits` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '可选学分',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `gmt_create` datetime NOT NULL COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';


-- 学生，课程关系表
CREATE TABLE `zl_student_teacher` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `cid` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '课程ID',
    `sid` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '学生ID',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `gmt_create` datetime NOT NULL COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生，课程关系表';