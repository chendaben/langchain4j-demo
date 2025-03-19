package org.example.langchain4jdemo.controller;

import org.example.langchain4jdemo.controller.request.LoginRequest;
import org.example.langchain4jdemo.entity.User;
import org.example.langchain4jdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class DirectAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.getUserByUsername(request.getUsername());
        
        if (user != null && request.getPassword().equals(user.getPassword())) {
            // Update last login time
            userService.updateLastLoginTime(request.getUsername());
            
            Map<String, Object> response = new HashMap<>();
            response.put("username", request.getUsername());
            response.put("message", "登录成功");
            
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.status(401).body("用户名或密码错误");
    }
}
