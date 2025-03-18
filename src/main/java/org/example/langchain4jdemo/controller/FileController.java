package org.example.langchain4jdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.langchain4jdemo.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
@Tag(name = "文件上传", description = "文件上传相关接口")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(
        summary = "上传文件",
        description = "上传文件到服务器",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "上传成功", 
                content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "上传失败"
            )
        }
    )
    @PostMapping
    public ResponseEntity<Map<String, Object>> uploadFile(
            @Parameter(description = "要上传的文件", required = true) 
            @RequestParam("file") MultipartFile file) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (file.isEmpty()) {
                response.put("success", false);
                response.put("message", "请选择要上传的文件");
                return ResponseEntity.badRequest().body(response);
            }
            
            String fileName = fileService.storeFile(file);
            
            response.put("success", true);
            response.put("message", "文件上传成功");
            response.put("fileName", fileName);
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "文件上传失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
