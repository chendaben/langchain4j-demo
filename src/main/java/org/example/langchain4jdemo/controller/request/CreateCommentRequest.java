package org.example.langchain4jdemo.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建评论请求
 */
@Data
@Schema(description = "创建评论请求")
public class CreateCommentRequest {
    
    @Schema(description = "评论内容", required = true)
    private String content;
    
    @Schema(description = "联系方式")
    private String contact;
    
    @Schema(description = "关联的宠物ID", required = true)
    private Integer petId;
    
    @Schema(description = "评论用户ID", required = true)
    private Integer userId;
}
