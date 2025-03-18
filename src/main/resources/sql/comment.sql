-- 创建线索评论表
CREATE TABLE IF NOT EXISTS comment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT COMMENT '评论内容',
    contact VARCHAR(255) COMMENT '联系方式',
    is_useful BOOLEAN DEFAULT FALSE COMMENT '是否标记为有用',
    pet_id INT COMMENT '关联的宠物ID',
    user_id INT COMMENT '评论用户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (pet_id) REFERENCES pet(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);
