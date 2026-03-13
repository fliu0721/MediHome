# MediHome - 家庭药品管理系统

基于 Spring Boot 2.7 + Vue3 的全栈家庭药品管理应用。

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | Spring Boot 2.7.18, MyBatis-Plus, JWT, Spring Security |
| 前端 | Vue3, Element Plus, Pinia, Axios |
| 数据库 | MySQL 5.7 |
| JDK | 1.8 |

## 功能特性

- 用户注册/登录（JWT认证）
- 家庭成员管理
- 药品分类管理
- 药品CRUD（支持过期提醒）
- 库存变动记录
- 用药记录管理
- 用药提醒设置

## 快速开始

### 后端启动

```bash
cd backend
mvn clean package -Dmaven.repo.local=./.m2/repository
java -jar target/medi-home-1.0.0.jar
```

### 前端启动

```bash
cd frontend
npm install
npm run serve
```

访问 http://localhost:3000

### 数据库初始化

```sql
CREATE DATABASE medi_home CHARACTER SET utf8mb4;
-- 执行 doc/部署说明.md 中的初始化脚本
```

## 项目结构

```
MediHome/
├── backend/          # Spring Boot 后端
├── frontend/         # Vue3 前端
└── doc/              # 文档
```

## 开发环境

- 后端端口: 8080
- 前端端口: 3000
- 验证码: 123456（开发环境固定）

## 文档

- [模块设计文档](doc/模块设计文档.md)
- [部署说明](doc/部署说明.md)
- [常见问题排查](doc/常见问题排查指南.md)
