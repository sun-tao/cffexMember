-- 创建数据库
CREATE DATABASE IF NOT EXISTS cffexmember DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE cffexmember;

-- 会员入会申请主表
CREATE TABLE IF NOT EXISTS t_membership_application (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '申请单ID',
    applicant_user_id INT NOT NULL COMMENT '申请人id',
    applicant_user_name VARCHAR(100) NOT NULL COMMENT '申请人名',
    form_data JSON COMMENT '申请单内容',
    attachment_id INT COMMENT '附件id',
    status VARCHAR(20) NOT NULL DEFAULT 'PROCESSING' COMMENT '申请状态',
    current_node_id INT COMMENT '当前结点',
    return_source_node_id INT COMMENT '上次退回的节点',
    ctime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    mtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    INDEX idx_applicant_user_id (applicant_user_id),
    INDEX idx_status (status),
    INDEX idx_ctime (ctime)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员入会申请主表';

-- 流程结点配置表
CREATE TABLE IF NOT EXISTS t_process_nodes (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '结点id',
    node_name VARCHAR(100) COMMENT '结点名称',
    node_type VARCHAR(20) NOT NULL COMMENT '结点类型',
    handler_group_code VARCHAR(50) COMMENT '处理人用户组代码',
    on_approve_node_id INT COMMENT '通过后下个结点id',
    on_reject_node_id INT COMMENT '拒绝后的下一个结点id',
    on_return_node_id INT COMMENT '退回后下个结点id',
    INDEX idx_node_type (node_type),
    INDEX idx_handler_group_code (handler_group_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程结点配置表';

-- 审批任务表
CREATE TABLE IF NOT EXISTS t_approval_tasks (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '任务ID',
    application_id INT NOT NULL COMMENT '申请ID',
    node_id INT NOT NULL COMMENT '节点ID',
    node_name VARCHAR(100) NOT NULL COMMENT '节点名称',
    task_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '任务状态',
    ctime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    mtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '完成时间',
    INDEX idx_application_id (application_id),
    INDEX idx_node_id (node_id),
    INDEX idx_task_status (task_status),
    INDEX idx_ctime (ctime)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审批任务表';

-- 审批历史表
CREATE TABLE IF NOT EXISTS t_approval_history (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '历史记录ID',
    application_id INT NOT NULL COMMENT '申请ID',
    task_id INT NOT NULL COMMENT '任务ID',
    operator_id INT NOT NULL COMMENT '操作人id',
    operator_name VARCHAR(100) NOT NULL COMMENT '操作人名字',
    operator_group_code VARCHAR(50) NOT NULL COMMENT '操作人用户分组',
    operation_type VARCHAR(20) NOT NULL COMMENT '操作类型',
    comments TEXT COMMENT '审批意见',
    ctime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '审批时间',
    INDEX idx_application_id (application_id),
    INDEX idx_task_id (task_id),
    INDEX idx_operator_id (operator_id),
    INDEX idx_ctime (ctime)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审批历史表';

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
    username VARCHAR(100) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    usergroup_code VARCHAR(50) NOT NULL COMMENT '用户组代码',
    usergroup_name VARCHAR(100) NOT NULL COMMENT '用户组名称',
    INDEX idx_usergroup_code (usergroup_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 申请附件表
CREATE TABLE IF NOT EXISTS t_application_attachments (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '附件ID',
    attachment_id INT NOT NULL COMMENT '附件ID',
    file_name VARCHAR(255) NOT NULL COMMENT '文件名',
    storage_path VARCHAR(500) NOT NULL COMMENT '存储路径',
    download_url VARCHAR(500) COMMENT '下载链接',
    INDEX idx_attachment_id (attachment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='申请附件表';

-- 插入流程节点配置数据
INSERT INTO t_process_nodes (id, node_name, node_type, handler_group_code, on_approve_node_id, on_reject_node_id, on_return_node_id) VALUES
(10000, NULL, 'START', NULL, 10001, NULL, NULL),
(10001, 'trade_dept_junior', 'PROCESSING', 'trade_dept_junior', 10002, 99999, 10006),
(10002, 'trade_dept_senior', 'PROCESSING', 'trade_dept_senior', 10003, 99999, 10006),
(10003, 'clearing_dept_junior', 'PROCESSING', 'clearing_dept_junior', 10004, 99999, 10006),
(10004, 'clearing_dept_senior', 'PROCESSING', 'clearing_dept_senior', 10005, 99999, 10006),
(10005, 'management', 'PROCESSING', 'management', 99999, 99999, 10006),
(10006, 'member_modify', 'PROCESSING', 'member', NULL, NULL, NULL),
(99999, NULL, 'END', NULL, NULL, NULL, NULL);

-- 插入测试用户数据（密码都是123456的BCrypt加密）
INSERT INTO users (username, password, usergroup_code, usergroup_name) VALUES
('member001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'member', '会员'),
('trade_junior001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'trade_dept_junior', '交易部初审'),
('trade_senior001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'trade_dept_senior', '交易部复审'),
('clearing_junior001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'clearing_dept_junior', '结算部初审'),
('clearing_senior001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'clearing_dept_senior', '结算部复审'),
('manager001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'management', '领导'); 