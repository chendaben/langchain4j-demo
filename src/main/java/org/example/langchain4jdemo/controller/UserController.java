package org.example.langchain4jdemo.controller;

import org.example.langchain4jdemo.entity.User;
import org.example.langchain4jdemo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/batch")
    public ResponseEntity<Map<String, Object>> batchDeleteUsers(@RequestBody List<Integer> ids) {
        int deletedCount = userService.deleteUsersByIds(ids);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "批量删除成功",
            "deletedCount", deletedCount
        ));
    }
}
