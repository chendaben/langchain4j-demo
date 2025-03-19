package org.example.langchain4jdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.langchain4jdemo.entity.Notification;
import org.example.langchain4jdemo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知控制器
 */
@RestController
@RequestMapping("/notifications")
@Tag(name = "通知管理", description = "通知相关接口")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 获取用户通知列表
     *
     * @param userId 用户ID
     * @param page   页码
     * @param size   每页大小
     * @return 通知列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户通知列表", description = "分页获取用户的通知列表")
    public ResponseEntity<?> getUserNotifications(
            @Parameter(description = "用户ID") @PathVariable Integer userId,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId, page, size));
    }

    /**
     * 获取用户未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    @GetMapping("/user/{userId}/unread/count")
    @Operation(summary = "获取用户未读通知数量", description = "获取用户的未读通知数量")
    public ResponseEntity<Integer> getUnreadNotificationCount(
            @Parameter(description = "用户ID") @PathVariable Integer userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotificationCount(userId));
    }

    /**
     * 标记通知为已读
     *
     * @param id 通知ID
     * @return 标记结果
     */
    @PutMapping("/{id}/read")
    @Operation(summary = "标记通知为已读", description = "将指定通知标记为已读")
    public ResponseEntity<?> markAsRead(
            @Parameter(description = "通知ID") @PathVariable Integer id) {
        boolean success = notificationService.markAsRead(id);
        if (success) {
            return ResponseEntity.ok("通知已标记为已读");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("通知不存在");
        }
    }

    /**
     * 标记用户所有通知为已读
     *
     * @param userId 用户ID
     * @return 标记结果
     */
    @PutMapping("/user/{userId}/read/all")
    @Operation(summary = "标记用户所有通知为已读", description = "将用户的所有通知标记为已读")
    public ResponseEntity<?> markAllAsRead(
            @Parameter(description = "用户ID") @PathVariable Integer userId) {
        int count = notificationService.markAllAsRead(userId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("markedCount", count);
        response.put("message", "已将所有通知标记为已读");
        
        return ResponseEntity.ok(response);
    }

    /**
     * 删除通知
     *
     * @param id 通知ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除通知", description = "删除指定通知")
    public ResponseEntity<?> deleteNotification(
            @Parameter(description = "通知ID") @PathVariable Integer id) {
        boolean success = notificationService.deleteNotification(id);
        if (success) {
            return ResponseEntity.ok("通知删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("通知不存在");
        }
    }

    /**
     * 创建系统通知（管理员）
     *
     * @param userId  用户ID
     * @param title   通知标题
     * @param content 通知内容
     * @return 通知ID
     */
    @PostMapping("/system/user/{userId}")
    @Operation(summary = "创建系统通知", description = "为指定用户创建系统通知")
    public ResponseEntity<?> createSystemNotification(
            @Parameter(description = "用户ID") @PathVariable Integer userId,
            @Parameter(description = "通知标题") @RequestParam String title,
            @Parameter(description = "通知内容") @RequestParam String content) {
        Integer notificationId = notificationService.createSystemNotification(userId, title, content);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", notificationId);
        response.put("message", "系统通知创建成功");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 批量创建系统通知（管理员）
     *
     * @param userIds 用户ID列表
     * @param title   通知标题
     * @param content 通知内容
     * @return 创建结果
     */
    @PostMapping("/system/batch")
    @Operation(summary = "批量创建系统通知", description = "为多个用户创建系统通知")
    public ResponseEntity<?> createSystemNotifications(
            @Parameter(description = "用户ID列表") @RequestBody List<Integer> userIds,
            @Parameter(description = "通知标题") @RequestParam String title,
            @Parameter(description = "通知内容") @RequestParam String content) {
        int count = notificationService.createSystemNotifications(userIds, title, content);
        
        Map<String, Object> response = new HashMap<>();
        response.put("createdCount", count);
        response.put("message", "批量系统通知创建成功");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
