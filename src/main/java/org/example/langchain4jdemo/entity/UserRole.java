package org.example.langchain4jdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户角色实体类
 */
@Data
@TableName("user_role")
public class UserRole {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 用户ID
     */
    private Integer userId;
    
    /**
     * 角色：ADMIN-管理员，OWNER-宠物主人，USER-普通用户
     */
    private String role;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
