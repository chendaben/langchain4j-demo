-- 创建宠物表
CREATE TABLE IF NOT EXISTS pet (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) COMMENT '宠物名称',
    type VARCHAR(255) COMMENT '宠物类型',
    breed VARCHAR(255) COMMENT '宠物品种',
    gender VARCHAR(50) COMMENT '宠物性别',
    color VARCHAR(100) COMMENT '宠物颜色',
    birth_date DATE COMMENT '宠物出生日期',
    description TEXT COMMENT '宠物描述',
    lost_time DATETIME COMMENT '走失时间',
    lost_location VARCHAR(255) COMMENT '走失地点',
    contact VARCHAR(255) COMMENT '联系方式',
    status VARCHAR(50) DEFAULT 'LOST' COMMENT '状态：LOST-走失中，FOUND-已找回',
    owner_id INT COMMENT '主人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (owner_id) REFERENCES user(id)
);
