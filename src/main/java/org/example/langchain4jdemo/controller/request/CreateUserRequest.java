package org.example.langchain4jdemo.controller.request;

import lombok.Data;
import java.util.Date;

/**
 * Request object for creating a new user
 */
@Data
public class CreateUserRequest {
    /**
     * User's name
     */
    private String name;
    
    /**
     * User's birthday
     */
    private Date birthday;
}
