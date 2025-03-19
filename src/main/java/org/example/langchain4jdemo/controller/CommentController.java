package org.example.langchain4jdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.langchain4jdemo.controller.request.CreateCommentRequest;
import org.example.langchain4jdemo.entity.Comment;
import org.example.langchain4jdemo.entity.CommentAttachment;
import org.example.langchain4jdemo.service.CommentAttachmentService;
import org.example.langchain4jdemo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/comments")
@Tag(name = "评论管理", description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentAttachmentService commentAttachmentService;

    /**
     * 获取宠物评论列表
     *
     * @param petId 宠物ID
     * @param page  页码
     * @param size  每页大小
     * @return 评论列表
     */
    @GetMapping("/pet/{petId}")
    @Operation(summary = "获取宠物评论列表", description = "根据宠物ID分页获取评论列表")
    public ResponseEntity<?> getCommentsByPetId(
            @Parameter(description = "宠物ID") @PathVariable Integer petId,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(commentService.getCommentsByPetId(petId, page, size));
    }

    /**
     * 创建评论
     *
     * @param request 评论请求
     * @return 评论ID
     */
    @PostMapping
    @Operation(summary = "创建评论", description = "创建新的评论")
    public ResponseEntity<?> createComment(@RequestBody CreateCommentRequest request) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setContact(request.getContact());
        comment.setPetId(request.getPetId());
        comment.setUserId(request.getUserId());
        comment.setIsUseful(false);
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());

        Integer commentId = commentService.addComment(comment);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", commentId);
        response.put("message", "评论创建成功");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 上传评论附件
     *
     * @param commentId 评论ID
     * @param files     文件列表
     * @return 上传结果
     */
    @PostMapping("/{commentId}/attachments")
    @Operation(summary = "上传评论附件", description = "为评论上传图片或视频附件")
    public ResponseEntity<?> uploadAttachments(
            @Parameter(description = "评论ID") @PathVariable Integer commentId,
            @Parameter(description = "文件列表") @RequestParam("files") MultipartFile[] files) {
        try {
            int count = commentAttachmentService.uploadAttachments(commentId, files);
            
            Map<String, Object> response = new HashMap<>();
            response.put("uploadedCount", count);
            response.put("message", "附件上传成功");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("附件上传失败: " + e.getMessage());
        }
    }

    /**
     * 获取评论附件列表
     *
     * @param commentId 评论ID
     * @return 附件列表
     */
    @GetMapping("/{commentId}/attachments")
    @Operation(summary = "获取评论附件列表", description = "获取评论的所有附件")
    public ResponseEntity<List<CommentAttachment>> getAttachments(
            @Parameter(description = "评论ID") @PathVariable Integer commentId) {
        return ResponseEntity.ok(commentAttachmentService.getAttachmentsByCommentId(commentId));
    }

    /**
     * 删除评论
     *
     * @param id     评论ID
     * @param userId 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论", description = "用户删除自己的评论")
    public ResponseEntity<?> deleteComment(
            @Parameter(description = "评论ID") @PathVariable Integer id,
            @Parameter(description = "用户ID") @RequestParam Integer userId) {
        boolean success = commentService.deleteComment(id, userId);
        if (success) {
            commentAttachmentService.deleteAttachmentsByCommentId(id);
            return ResponseEntity.ok("评论删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("无权删除该评论或评论不存在");
        }
    }

    /**
     * 管理员删除评论
     *
     * @param id 评论ID
     * @return 删除结果
     */
    @DeleteMapping("/admin/{id}")
    @Operation(summary = "管理员删除评论", description = "管理员删除任意评论")
    public ResponseEntity<?> deleteCommentByAdmin(
            @Parameter(description = "评论ID") @PathVariable Integer id) {
        boolean success = commentService.deleteCommentByAdmin(id);
        if (success) {
            commentAttachmentService.deleteAttachmentsByCommentId(id);
            return ResponseEntity.ok("评论删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("评论不存在");
        }
    }

    /**
     * 标记评论为有用
     *
     * @param id 评论ID
     * @return 标记结果
     */
    @PutMapping("/{id}/useful")
    @Operation(summary = "标记评论为有用", description = "宠物主人标记评论为有用")
    public ResponseEntity<?> markCommentAsUseful(
            @Parameter(description = "评论ID") @PathVariable Integer id) {
        boolean success = commentService.markCommentAsUseful(id);
        if (success) {
            return ResponseEntity.ok("评论已标记为有用");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("评论不存在");
        }
    }

    /**
     * 删除评论附件
     *
     * @param commentId    评论ID
     * @param attachmentId 附件ID
     * @return 删除结果
     */
    @DeleteMapping("/{commentId}/attachments/{attachmentId}")
    @Operation(summary = "删除评论附件", description = "删除评论的特定附件")
    public ResponseEntity<?> deleteAttachment(
            @Parameter(description = "评论ID") @PathVariable Integer commentId,
            @Parameter(description = "附件ID") @PathVariable Integer attachmentId) {
        boolean success = commentAttachmentService.deleteAttachment(attachmentId);
        if (success) {
            return ResponseEntity.ok("附件删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("附件不存在");
        }
    }
}
