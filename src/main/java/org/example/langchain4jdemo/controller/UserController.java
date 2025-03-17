package org.example.langchain4jdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.langchain4jdemo.entity.User;
import org.example.langchain4jdemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.langchain4jdemo.controller.request.CreateUserRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "用户管理", description = "用户信息管理相关接口")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @Operation(
        summary = "根据ID查询用户",
        description = "通过用户ID查询用户详细信息",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "查询成功", 
                content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "用户不存在"
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "用户ID", required = true) 
            @PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    
    @Operation(
        summary = "批量删除用户",
        description = "根据用户ID列表批量删除用户",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "删除成功", 
                content = @Content(schema = @Schema(implementation = Map.class))
            )
        }
    )
    @DeleteMapping("/batch")
    public ResponseEntity<Map<String, Object>> batchDeleteUsers(
            @Parameter(description = "用户ID列表", required = true) 
            @RequestBody List<Integer> ids) {
        int deletedCount = userService.deleteUsersByIds(ids);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "批量删除成功",
            "deletedCount", deletedCount
        ));
    }
    
    @Operation(
        summary = "新增用户",
        description = "根据用户名称、性别、身份证号和生日新增用户",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                description = "创建成功", 
                content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "请求参数错误"
            )
        }
    )
    @PostMapping
    public ResponseEntity<User> createUser(
            @Parameter(description = "用户创建请求", required = true) 
            @RequestBody CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setGender(request.getGender());
        user.setIdCard(request.getIdCard());
        user.setBirthday(request.getBirthday());
        
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
