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

        // 复制文件到目标位置
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
