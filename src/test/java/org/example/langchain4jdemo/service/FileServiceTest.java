package org.example.langchain4jdemo.service;

import org.example.langchain4jdemo.config.FileStorageConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FileServiceTest {

    @TempDir
    Path tempDir;

    @Mock
    private FileStorageConfig fileStorageConfig;

    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(fileStorageConfig.getUploadDir()).thenReturn(tempDir.toString());
        fileService = new FileService(fileStorageConfig);
    }

    @Test
    void storeFile_ValidFile_ReturnsFileName() throws IOException {
        // 准备测试数据
        String fileName = "test-file.txt";
        String content = "测试文件内容";
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                fileName,
                "text/plain",
                content.getBytes()
        );

        // 执行测试
        String storedFileName = fileService.storeFile(multipartFile);

        // 验证结果
        assertEquals(fileName, storedFileName);
        assertTrue(Files.exists(tempDir.resolve(fileName)));
        String storedContent = Files.readString(tempDir.resolve(fileName));
        assertEquals(content, storedContent);
    }

    @Test
    void storeFile_InvalidFileName_ThrowsException() {
        // 准备测试数据
        String fileName = "..\\invalid.txt";
        String content = "测试文件内容";
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                fileName,
                "text/plain",
                content.getBytes()
        );

        // 执行测试并验证异常
        Exception exception = assertThrows(IOException.class, () -> {
            fileService.storeFile(multipartFile);
        });

        assertTrue(exception.getMessage().contains("文件名无效"));
    }
}
