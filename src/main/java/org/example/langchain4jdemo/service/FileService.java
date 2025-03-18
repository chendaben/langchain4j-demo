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

    private final String uploadDir;
    
    public FileService() {
        this.uploadDir = System.getProperty("file.upload.dir", FileStorageConfig.FILE_UPLOAD_DIR);
    }

    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file");
        }
        
        Path uploadPath = Paths.get(uploadDir);
        
        // Create directories if they don't exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Normalize the file name
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            fileName = "file_" + System.currentTimeMillis();
        }
        
        // Copy the file to the target location
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        
        return fileName;
    }
}
