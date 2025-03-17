-- 创建用户表，包含性别字段
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    gender VARCHAR(255),
    birthday DATE
);
