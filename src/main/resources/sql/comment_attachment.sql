-- 创建评论附件表
CREATE TABLE IF NOT EXISTS comment_attachment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    comment_id INT COMMENT '关联的评论ID',
    file_name VARCHAR(255) COMMENT '文件名',
    file_path VARCHAR(255) COMMENT '文件路径',
    file_type VARCHAR(50) COMMENT '文件类型',
    file_size BIGINT COMMENT '文件大小(字节)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);
