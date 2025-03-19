-- 用户表 (使用双引号避免H2保留字冲突)
CREATE TABLE IF NOT EXISTS "user" (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20),
    gender VARCHAR(10),
    last_login_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP
);

-- 用户角色表
CREATE TABLE IF NOT EXISTS user_role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    role VARCHAR(50) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- 初始化管理员用户
INSERT INTO "user" (username, password, name, email, create_time) 
VALUES ('admin', 'admin', '管理员', 'admin@example.com', CURRENT_TIMESTAMP);

-- 初始化测试用户
INSERT INTO "user" (username, password, name, email, create_time) 
VALUES ('test', 'test', '测试用户', 'test@example.com', CURRENT_TIMESTAMP);

-- 初始化用户角色
INSERT INTO user_role (user_id, role, create_time) 
VALUES (1, 'ADMIN', CURRENT_TIMESTAMP);

INSERT INTO user_role (user_id, role, create_time) 
VALUES (2, 'USER', CURRENT_TIMESTAMP);
