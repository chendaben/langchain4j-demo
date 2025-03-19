package org.example.langchain4jdemo.controller.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
