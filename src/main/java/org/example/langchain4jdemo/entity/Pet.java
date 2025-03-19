package org.example.langchain4jdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 宠物实体类
 */
@Data
@TableName("pet")
public class Pet {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 宠物名称
     */
    private String name;
    
    /**
     * 宠物类型
     */
    private String type;
    
    /**
     * 宠物品种
     */
    private String breed;
    
    /**
     * 宠物性别
     */
    private String gender;
    
    /**
     * 宠物颜色
     */
    private String color;
    
    /**
     * 宠物出生日期
     */
    private LocalDate birthDate;
    
    /**
     * 宠物描述
     */
    private String description;
    
    /**
     * 走失时间
     */
    private LocalDateTime lostTime;
    
    /**
     * 走失地点
     */
    private String lostLocation;
    
    /**
     * 联系方式
     */
    private String contact;
    
    /**
     * 状态：LOST-走失中，FOUND-已找回
     */
    private String status;
    
    /**
     * 主人ID
     */
    private Integer ownerId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
