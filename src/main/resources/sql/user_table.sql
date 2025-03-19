-- 创建用户表，包含身份证号字段
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    gender VARCHAR(255),
    idCard VARCHAR(255),
    birthday DATE
);
