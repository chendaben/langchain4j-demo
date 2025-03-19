package org.example.langchain4jdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论附件实体类
 */
@Data
@TableName("comment_attachment")
public class CommentAttachment {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 关联的评论ID
     */
    private Integer commentId;
    
    /**
     * 文件名
     */
    private String fileName;
    
    /**
     * 文件路径
     */
    private String filePath;
    
    /**
     * 文件类型
     */
    private String fileType;
    
    /**
     * 文件大小(字节)
     */
    private Long fileSize;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
