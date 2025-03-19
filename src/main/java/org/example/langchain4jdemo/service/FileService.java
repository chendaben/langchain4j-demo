package org.example.langchain4jdemo.service;

import org.example.langchain4jdemo.config.FileStorageConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    private final Path fileStorageLocation;

    public FileService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
    }

    public String storeFile(MultipartFile file) throws IOException {
        // 检查文件名是否包含无效字符
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.contains("..")) {
            throw new IOException("文件名无效: " + fileName);
        }
        
        // 检查文件类型
        String contentType = file.getContentType();
        if (contentType == null || (!isImageFile(contentType) && !isVideoFile(contentType))) {
            throw new IOException("不支持的文件类型: " + contentType);
        }
        
        // 生成唯一文件名
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        
        // 复制文件到目标位置
        Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        
        return uniqueFileName;
    }
    
    private boolean isImageFile(String contentType) {
        return contentType.startsWith("image/");
    }
    
    private boolean isVideoFile(String contentType) {
        return contentType.startsWith("video/");
    }
    
    public boolean deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }
}
