-- 初始化管理员用户
INSERT INTO user (name, username, password, email, phone, gender, idCard, birthday, last_login_time)
VALUES ('Root Admin', 'root', '123456', 'admin@example.com', '13800138000', 'Male', '110101199001010000', '1990-01-01', NOW());

-- 为Root用户分配管理员角色
INSERT INTO user_role (user_id, role, create_time)
VALUES (LAST_INSERT_ID(), 'ADMIN', NOW());
