package org.example.langchain4jdemo.service;

import org.example.langchain4jdemo.config.FileStorageConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService;
    
    @TempDir
    Path tempDir;
    
    @BeforeEach
    void setUp() throws IOException {
        // Override the upload directory for testing
        System.setProperty("file.upload.dir", tempDir.toString());
        fileService = new FileService();
    }
    
    @Test
    void storeFile_ShouldSaveFile_WhenFileIsValid() throws IOException {
        // Arrange
        String content = "test file content";
        MockMultipartFile file = new MockMultipartFile(
            "file", 
            "test.txt", 
            "text/plain", 
            content.getBytes()
        );
        
        // Act
        String savedFileName = fileService.storeFile(file);
        
        // Assert
        assertNotNull(savedFileName);
        assertEquals("test.txt", savedFileName);
        
        Path savedFilePath = Paths.get(tempDir.toString(), savedFileName);
        assertTrue(Files.exists(savedFilePath));
        
        String savedContent = new String(Files.readAllBytes(savedFilePath));
        assertEquals(content, savedContent);
    }
    
    @Test
    void storeFile_ShouldThrowException_WhenFileIsEmpty() {
        // Arrange
        MockMultipartFile emptyFile = new MockMultipartFile(
            "file", 
            "empty.txt", 
            "text/plain", 
            new byte[0]
        );
        
        // Act & Assert
        Exception exception = assertThrows(IOException.class, () -> {
            fileService.storeFile(emptyFile);
        });
        
        assertEquals("Failed to store empty file", exception.getMessage());
    }
}
