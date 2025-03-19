package org.example.langchain4jdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String gender;
    private String idCard;
    private Date birthday;
    private LocalDateTime lastLoginTime;
}
