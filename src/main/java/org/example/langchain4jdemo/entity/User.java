package org.example.langchain4jdemo.entity;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Data
@Schema(description = "用户实体")
public class User {
    @Schema(description = "用户ID", example = "1")
    private Integer id;
    
    @Schema(description = "用户名称", example = "张三")
    private String name;
    
    @Schema(description = "出生日期", example = "1990-01-01")
    private Date birthday;
}
