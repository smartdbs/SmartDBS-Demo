
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for acc_authority
-- ----------------------------
DROP TABLE IF EXISTS `acc_authority`;
CREATE TABLE `acc_authority` (
  `group_num` int(16) NOT NULL AUTO_INCREMENT COMMENT '门禁权限组编号,企业内唯一',
  `name` varchar(50) NOT NULL COMMENT '权限组名称',
  `timezone_num` int(16) NOT NULL COMMENT '门禁时间段编号,企业内唯一',
  `company_id` varchar(50) DEFAULT NULL COMMENT '企业id',
  `start_time` varchar(50) DEFAULT NULL COMMENT '权限组有效时间，默认及时生效，即当前时间，时间格式为ISO8601',
  `end_time` varchar(50) DEFAULT NULL COMMENT '权限组有效时间，默认永久，时间格式为ISO8601',
  PRIMARY KEY (`group_num`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='门禁权限组';

-- ----------------------------
-- Table structure for acc_authority_door
-- ----------------------------
DROP TABLE IF EXISTS `acc_authority_door`;
CREATE TABLE `acc_authority_door` (
  `id` int(16) NOT NULL AUTO_INCREMENT COMMENT '权限组-门中间表id',
  `group_num` int(16) NOT NULL COMMENT '权限组id',
  `door_id` varchar(50) NOT NULL COMMENT '门id',
  `dev_id` int(16) NOT NULL COMMENT '设备id',
  `door_num` int(16) NOT NULL COMMENT '门编号',
  `sn` varchar(50) DEFAULT NULL COMMENT '设备序列号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8 COMMENT='门禁权限组与门关系表';

-- ----------------------------
-- Table structure for acc_authority_employee
-- ----------------------------
DROP TABLE IF EXISTS `acc_authority_employee`;
CREATE TABLE `acc_authority_employee` (
  `id` varchar(50) NOT NULL COMMENT '权限组-人员中间表id',
  `group_num` int(16) NOT NULL COMMENT '权限组id',
  `employee_id` varchar(50) NOT NULL COMMENT '员工id',
  `employee_no` varchar(50) DEFAULT NULL COMMENT '员工工号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='门禁权限组与人员关系表';

-- ----------------------------
-- Table structure for acc_door
-- ----------------------------
DROP TABLE IF EXISTS `acc_door`;
CREATE TABLE `acc_door` (
  `id` varchar(50) NOT NULL,
  `door_num` int(16) NOT NULL COMMENT '门编号',
  `door_name` varchar(100) NOT NULL COMMENT '门名称',
  `sn` varchar(50) NOT NULL COMMENT '设备序列号',
  `company_id` varchar(50) NOT NULL COMMENT '企业id'
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='门信息';

-- ----------------------------
-- Table structure for acc_timezone
-- ----------------------------
DROP TABLE IF EXISTS `acc_timezone`;
CREATE TABLE `acc_timezone` (
  `timezone_num` int(16) NOT NULL AUTO_INCREMENT COMMENT '门禁时间段编号,企业内唯一',
  `timezone_name` varchar(100) DEFAULT NULL COMMENT '门禁时间段名称',
  `company_id` varchar(50) DEFAULT NULL COMMENT '企业id',
  PRIMARY KEY (`timezone_num`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='门禁时间段';

-- ----------------------------
-- Table structure for acc_timezone_detail
-- ----------------------------
DROP TABLE IF EXISTS `acc_timezone_detail`;
CREATE TABLE `acc_timezone_detail` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `week` int(16) DEFAULT NULL COMMENT '时间段类型，0～6表示周日～周六，7～9表示节假日类型，即7=节假日类型一，8=节假日类型二，9=节假日类型三',
  `start_time` varchar(50) DEFAULT NULL COMMENT '开始时间，格式HH:mm',
  `end_time` varchar(50) DEFAULT NULL COMMENT '截止时间，格式HH:mm',
  `timezone_num` int(16) NOT NULL COMMENT '时间段编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='门禁时间段';

-- ----------------------------
-- Table structure for acc_transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `acc_transaction_log`;
CREATE TABLE `acc_transaction_log` (
  `id` varchar(50) NOT NULL COMMENT '门禁记录id',
  `operator` varchar(50) DEFAULT NULL COMMENT '用户工号或者其他操作者',
  `verified` int(16) DEFAULT NULL COMMENT '验证方式',
  `event_code` int(16) DEFAULT NULL COMMENT '事件码',
  `door_num` int(16) DEFAULT NULL COMMENT '门编号',
  `in_out_type` int(16) DEFAULT NULL COMMENT '出入状态， 0:入， 1:出',
  `time` varchar(50) DEFAULT NULL COMMENT '时间, ISO标准时间格式:yyyy-MM-ddTHH:mm:ss±HH:mm, (yyyy-MM-ddTHH:mm:ss):设备的本地时间, (HH:mm):设备的时区',
  `company_id` varchar(50) DEFAULT NULL COMMENT '企业id',
  `created_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `sn` varchar(50) DEFAULT NULL COMMENT '设备序列号',
  `time_stamp` bigint(50) DEFAULT NULL COMMENT '时间戳',
  `modified_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='门禁事件记录';

-- ----------------------------
-- Table structure for att_record
-- ----------------------------
DROP TABLE IF EXISTS `att_record`;
CREATE TABLE `att_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考勤记录id',
  `check_in_time` bigint(20) DEFAULT NULL COMMENT '打卡时间',
  `company_id` varchar(255) DEFAULT NULL COMMENT '企业id',
  `created_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `iso_check_in_time` varchar(255) DEFAULT NULL COMMENT '打卡时间(iso8601标准格式)',
  `employee_no` varchar(32) DEFAULT NULL COMMENT '员工工号',
  `sn` varchar(64) DEFAULT NULL COMMENT '设备序列号',
  `modified_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_att_record_pin` (`employee_no`) USING BTREE,
  KEY `idx_record_sn` (`sn`) USING BTREE,
  KEY `idx_record_companyId` (`company_id`) USING BTREE,
  KEY `idx_record_checkInTime` (`check_in_time`) USING BTREE,
  KEY `idx_record_createTime` (`created_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52960947 DEFAULT CHARSET=utf8 COMMENT='考勤记录表';

-- ----------------------------
-- Table structure for bio_template
-- ----------------------------
DROP TABLE IF EXISTS `bio_template`;
CREATE TABLE `bio_template` (
  `id` varchar(32) NOT NULL,
  `sn` varchar(100) DEFAULT NULL COMMENT '设备序列号',
  `company_id` varchar(100) DEFAULT NULL COMMENT '企业id',
  `employee_no` varchar(32) DEFAULT NULL COMMENT '员工编号',
  `finger_count` int(3) DEFAULT NULL COMMENT '模板大小',
  `face_count` int(3) DEFAULT NULL COMMENT '生物模板特征类型',
  `palm_print_count` int(3) DEFAULT NULL,
  `face_photo_count` int(3) DEFAULT NULL,
  `modified_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `created_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='生物模板表';

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` varchar(32) NOT NULL,
  `active` bit(1) DEFAULT NULL COMMENT '状态，1代表正常，0代表软删除, 默认1',
  `agent_code` varchar(64) DEFAULT NULL COMMENT '代理商编号',
  `app_key` varchar(255) DEFAULT NULL COMMENT 'app_key',
  `company_code` varchar(64) DEFAULT NULL COMMENT '企业编号',
  `company_name` varchar(30) DEFAULT NULL COMMENT '企业名称',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `first_name` varchar(32) DEFAULT NULL COMMENT '姓',
  `last_name` varchar(32) DEFAULT NULL COMMENT '名',
  `formatted_name` varchar(64) DEFAULT NULL COMMENT '格式化姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `website` varchar(255) DEFAULT NULL COMMENT '网站',
  `user_name` varchar(50) DEFAULT NULL COMMENT '集成账号',
  `password` varchar(64) DEFAULT NULL COMMENT '集成账号密码',
  `created_date` bigint(20) DEFAULT NULL,
  `modified_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK46b11aic1f3xkre0jgeswn98s` (`company_code`,`app_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='公司表';

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备id',
  `company_id` varchar(255) DEFAULT NULL COMMENT '企业id',
  `created_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `alias` varchar(100) DEFAULT NULL COMMENT '设备别名',
  `sn` varchar(64) DEFAULT NULL COMMENT '设备序列号',
  `status` int(1) DEFAULT NULL COMMENT '设备在线状态(0:离线 1:在线)',
  `type` smallint(6) DEFAULT NULL COMMENT '设备类型(0:考勤设备 1:门禁设备)',
  `enable` int(1) DEFAULT NULL COMMENT '设备启用状态(0:禁用 1:启用)',
  `fw_version` varchar(50) DEFAULT NULL COMMENT '固件版本',
  `ip_address` varchar(16) DEFAULT NULL COMMENT '公网ip',
  `local_ip` varchar(16) DEFAULT NULL COMMENT '内网ip',
  `protocol` int(1) DEFAULT NULL COMMENT '设备协议版本(0 pull，1 push，2 best，3 ufo，4 best-w，5 best-t)',
  `modified_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `active` int(1) DEFAULT NULL COMMENT '设备激活状态(0:未激活 1:已激活)',
  `mac` varchar(100) DEFAULT NULL COMMENT '物理地址',
  `support_remote_face_photo` int(1) DEFAULT '0' COMMENT '是否支持远程登记可见光人脸，0 不支持，1支持，2未知',
  `support_remote_finger` int(1) DEFAULT '0' COMMENT '是否支持远程登记指纹，0 不支持，1支持，2未知',
  `support_remote_palm_print` int(1) DEFAULT '0' COMMENT '是否支持远程登记掌纹，0 不支持，1支持，2未知',
  `model` varchar(100) DEFAULT NULL COMMENT '设备型号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_s4rxlrfg7kdj94nkghktylgmk` (`sn`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='设备表';

-- ----------------------------
-- Table structure for device_employee
-- ----------------------------
DROP TABLE IF EXISTS `device_employee`;
CREATE TABLE `device_employee` (
  `id` varchar(32) NOT NULL COMMENT '设备员工关系id',
  `company_id` varchar(255) DEFAULT NULL COMMENT '企业id',
  `employee_no` varchar(255) DEFAULT NULL COMMENT '员工工号',
  `sn` varchar(255) DEFAULT NULL COMMENT '设备序列号',
  `modified_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `created_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dev_emp_sn` (`sn`) USING BTREE,
  KEY `idx_dev_emp_employeeid` (`employee_no`) USING BTREE,
  KEY `idx_dev_emp_companyid` (`company_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='设备与员工关系';

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` varchar(32) NOT NULL,
  `active` bit(1) DEFAULT NULL COMMENT '状态（假删除使用）',
  `company_id` varchar(32) DEFAULT NULL COMMENT '企业id',
  `created_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `created_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `employee_no` varchar(32) DEFAULT NULL COMMENT '员工工号',
  `ext_modified_date` bigint(20) DEFAULT NULL COMMENT '第三方修改时间',
  `first_name` varchar(32) DEFAULT NULL COMMENT '名',
  `formatted_name` varchar(64) DEFAULT NULL COMMENT '格式化名称',
  `gender` varchar(255) DEFAULT NULL COMMENT '性别。3表示其他，1表示男性，2表示女性',
  `hire_date` varchar(255) DEFAULT NULL COMMENT '入职时间',
  `imported_date` bigint(20) DEFAULT NULL COMMENT '第三方API数据进来时间',
  `last_name` varchar(32) DEFAULT NULL COMMENT '姓',
  `modified_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `modified_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机',
  `avatar` varchar(255) DEFAULT NULL COMMENT '档案照，存url',
  `status` int(11) DEFAULT NULL COMMENT '状态（冗余字段，可能没用到）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_employee_cmpid_empno` (`company_id`,`employee_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='员工表';

-- ----------------------------
-- Table structure for employee_enrollment
-- ----------------------------
DROP TABLE IF EXISTS `employee_enrollment`;
CREATE TABLE `employee_enrollment` (
  `id` varchar(32) NOT NULL,
  `card_no` varchar(255) DEFAULT NULL COMMENT '卡号',
  `company_id` varchar(32) DEFAULT NULL COMMENT '企业id',
  `created_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `created_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `device_password` varchar(255) DEFAULT NULL COMMENT '核验密码',
  `device_permission` int(11) DEFAULT '0' COMMENT '设备用户权限',
  `device_verify_mode` int(11) DEFAULT NULL COMMENT '员工支持的核验方式，与设备核验方式编码一致',
  `employee_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `ext_modified_date` bigint(20) DEFAULT NULL COMMENT '第三方修改时间',
  `imported_date` bigint(20) DEFAULT NULL COMMENT '第三方API数据进来时间',
  `modified_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `modified_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `timezone_Id` varchar(255) DEFAULT NULL COMMENT '所属时间段编号',
  `device_password_salt` varchar(255) DEFAULT NULL COMMENT '加密的盐值，devicePasswordEncryption不为空时必填',
  `device_password_encryption` varchar(255) DEFAULT NULL COMMENT '加密过后的核验密码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_employee_enrollment_empid` (`employee_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='员工注册信息';
