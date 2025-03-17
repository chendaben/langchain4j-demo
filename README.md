# Langchain4j Demo

这是一个基于Spring Boot和Langchain4j的演示项目，展示了如何在Java应用程序中集成大型语言模型。

## 项目概述

本项目是一个Spring Boot应用程序，集成了以下功能：

- 使用Langchain4j与Deepseek API进行自然语言处理
- MySQL数据库连接与MyBatis-Plus ORM框架
- RESTful API接口，包含用户管理功能
- Swagger/OpenAPI文档

## 技术栈

- Java 17
- Spring Boot 3.4.3
- Langchain4j
- MySQL 5.7
- MyBatis-Plus
- Swagger/OpenAPI

## 功能特性

### 聊天功能

- `/chat` 端点接收用户消息并返回AI生成的回复
- 使用Deepseek API作为底层语言模型

### 用户管理

- 根据ID查询用户信息
- 批量删除用户
- 创建新用户

## 快速开始

### 前置条件

- Java 17或更高版本
- Maven
- MySQL 5.7

### 数据库配置

1. 创建MySQL数据库`test`
2. 执行以下SQL脚本创建用户表：

```sql
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    gender VARCHAR(255),
    idCard VARCHAR(255),
    birthday DATE
);
```

### 配置应用

在`application.yml`中配置数据库连接和Deepseek API：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test?useSSL=false&characterEncoding=utf-8
    username: root
    password: 123456

langchain4j:
  open-ai:
    chat-model:
      api-key: your-api-key
      base-url: https://api.deepseek.com/v1
      model-name: deepseek-chat
```

### 构建与运行

```bash
mvn clean package
java -jar target/langchain4j-demo-0.0.1-SNAPSHOT.jar
```

应用将在`http://localhost:8080`启动。

## API文档

启动应用后，访问`http://localhost:8080/swagger-ui.html`查看API文档。

### 主要API端点

- `GET /chat?message=Hello` - 发送消息到AI并获取回复
- `GET /users/{id}` - 根据ID查询用户
- `DELETE /users/batch` - 批量删除用户
- `POST /users` - 创建新用户

## 贡献指南

欢迎提交Pull Request或Issue来改进这个项目。

## 许可证

[MIT License](LICENSE)
