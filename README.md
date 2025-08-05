# 会员入会申请系统

## 项目简介

这是一个会员入会申请审批系统，为交易所提供会员申请管理平台。会员可以提交入会申请，交易所各部门员工可以按流程进行审批。

## 主要功能

1. **会员申请提交** - 会员可以提交入会申请
2. **多部门审批流转** - 支持交易部、结算部等多个部门的审批流程
3. **审批历史记录** - 完整的审批历史追踪
4. **状态管理** - 申请状态实时更新

## 技术栈

- **后端框架**: Spring Boot 2.6.13
- **数据库**: MySQL 8.0
- **ORM框架**: MyBatis
- **Java版本**: JDK 1.8

## 项目结构

```
src/main/java/com/example/cffexmember/
├── entity/          # 实体类
├── dto/            # 数据传输对象
├── mapper/         # MyBatis Mapper接口
├── service/        # 业务逻辑层
├── controller/     # 控制器层
└── CffexmemberApplication.java  # 启动类

src/main/resources/
├── mapper/         # MyBatis XML映射文件
├── sql/           # 数据库脚本
└── application.yml # 配置文件
```

## 数据库设计

### 核心表结构

1. **t_membership_application** - 会员申请主表
2. **t_process_nodes** - 流程节点配置表
3. **t_approval_tasks** - 审批任务表
4. **t_approval_history** - 审批历史表
5. **users** - 用户表
6. **t_application_attachments** - 申请附件表

### 审批流程

```
START → 交易部初审 → 交易部复审 → 结算部初审 → 结算部复审 → 领导审批 → END
                ↓         ↓         ↓         ↓         ↓
              RETURN   RETURN   RETURN   RETURN   RETURN
                ↓         ↓         ↓         ↓         ↓
             会员修改 ← 会员修改 ← 会员修改 ← 会员修改 ← 会员修改
```

## 快速开始

### 1. 环境准备

- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库初始化

```sql
-- 执行 src/main/resources/sql/init.sql 脚本
```

### 3. 配置数据库连接

修改 `src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/cffexmember?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 4. 启动应用

```bash
mvn spring-boot:run
```

应用将在 `http://localhost:8080` 启动。

## API接口

### 申请相关接口

- `POST /api/applications/submit` - 提交申请
- `GET /api/applications/{id}` - 查询申请详情
- `GET /api/applications/applicant/{applicantUserId}` - 查询申请人的申请列表
- `GET /api/applications/status/{status}` - 根据状态查询申请列表
- `GET /api/applications/all` - 查询所有申请

### 审批相关接口

- `POST /api/approvals/approve` - 审批操作
- `GET /api/approvals/tasks/pending/{handlerGroupCode}` - 查询待处理任务
- `GET /api/approvals/tasks/application/{applicationId}` - 查询申请的任务列表
- `GET /api/approvals/history/application/{applicationId}` - 查询申请审批历史
- `GET /api/approvals/history/task/{taskId}` - 查询任务审批历史

## 测试数据

系统初始化时会创建以下测试用户：

- `member001` - 会员
- `trade_junior001` - 交易部初审
- `trade_senior001` - 交易部复审
- `clearing_junior001` - 结算部初审
- `clearing_senior001` - 结算部复审
- `manager001` - 领导

## 使用示例

### 1. 提交申请

```bash
curl -X POST http://localhost:8080/api/applications/submit \
  -H "Content-Type: application/json" \
  -d '{
    "applicantUserId": 1,
    "applicantUserName": "张三",
    "formData": "{\"company\":\"测试公司\",\"contact\":\"13800138000\"}"
  }'
```

### 2. 审批操作

```bash
curl -X POST http://localhost:8080/api/approvals/approve \
  -H "Content-Type: application/json" \
  -d '{
    "applicationId": 1,
    "taskId": 1,
    "operatorId": 2,
    "operatorName": "交易部初审员",
    "operatorGroupCode": "trade_dept_junior",
    "operationType": "APPROVE",
    "comments": "材料齐全，同意通过"
  }'
```

## 开发说明

### 添加新的审批节点

1. 在 `t_process_nodes` 表中添加节点配置
2. 更新相关节点的 `on_approve_node_id` 等字段
3. 创建对应的用户组和用户

### 扩展功能

- 文件上传功能
- 邮件通知功能
- 审批超时提醒
- 数据统计报表

## 注意事项

1. 确保数据库连接配置正确
2. 审批流程严格按照节点配置执行
3. 所有操作都有完整的日志记录
4. 建议在生产环境中添加权限验证

## 许可证

MIT License 