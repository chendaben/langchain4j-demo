-- 创建用户角色表
CREATE TABLE IF NOT EXISTS user_role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT COMMENT '用户ID',
    role VARCHAR(50) COMMENT '角色：ADMIN-管理员，OWNER-宠物主人，USER-普通用户',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

-- 修改用户表，添加登录相关字段
ALTER TABLE user 
ADD COLUMN username VARCHAR(50) COMMENT '用户名' AFTER name,
ADD COLUMN password VARCHAR(255) COMMENT '密码' AFTER username,
ADD COLUMN email VARCHAR(100) COMMENT '邮箱' AFTER password,
ADD COLUMN phone VARCHAR(20) COMMENT '手机号' AFTER email,
ADD COLUMN last_login_time DATETIME COMMENT '最后登录时间';
