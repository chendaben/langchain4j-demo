package org.example.langchain4jdemo.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 创建宠物请求
 */
@Data
@Schema(description = "创建宠物请求")
public class CreatePetRequest {
    
    @Schema(description = "宠物名称", required = true)
    private String name;
    
    @Schema(description = "宠物类型", required = true)
    private String type;
    
    @Schema(description = "宠物品种")
    private String breed;
    
    @Schema(description = "宠物性别")
    private String gender;
    
    @Schema(description = "宠物颜色")
    private String color;
    
    @Schema(description = "宠物出生日期")
    private LocalDate birthDate;
    
    @Schema(description = "宠物描述")
    private String description;
    
    @Schema(description = "走失时间", required = true)
    private LocalDateTime lostTime;
    
    @Schema(description = "走失地点", required = true)
    private String lostLocation;
    
    @Schema(description = "联系方式", required = true)
    private String contact;
    
    @Schema(description = "主人ID", required = true)
    private Integer ownerId;
}
