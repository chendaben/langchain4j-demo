package org.example.langchain4jdemo.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer id;
    private String name;
    private String gender;
    private String idCard;
    private Date birthday;
}
