-- 创建通知表
CREATE TABLE IF NOT EXISTS notification (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT COMMENT '接收通知的用户ID',
    title VARCHAR(255) COMMENT '通知标题',
    content TEXT COMMENT '通知内容',
    type VARCHAR(50) COMMENT '通知类型：COMMENT-评论通知，SYSTEM-系统通知',
    related_id INT COMMENT '相关ID（如评论ID）',
    is_read BOOLEAN DEFAULT FALSE COMMENT '是否已读',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (user_id) REFERENCES user(id)
);
