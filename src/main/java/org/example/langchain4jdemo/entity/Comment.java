package org.example.langchain4jdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Data
@TableName("comment")
public class Comment {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 联系方式
     */
    private String contact;
    
    /**
     * 是否标记为有用
     */
    private Boolean isUseful;
    
    /**
     * 关联的宠物ID
     */
    private Integer petId;
    
    /**
     * 评论用户ID
     */
    private Integer userId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
