package org.example.langchain4jdemo.controller;

import org.example.langchain4jdemo.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FileService fileService;

    private FileController fileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileController = new FileController(fileService);
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    void uploadFile_ValidFile_ReturnsSuccess() throws Exception {
        // 准备测试数据
        String fileName = "test-file.txt";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                fileName,
                MediaType.TEXT_PLAIN_VALUE,
                "测试文件内容".getBytes()
        );

        // 模拟服务层行为
        when(fileService.storeFile(any())).thenReturn(fileName);

        // 执行测试
        mockMvc.perform(multipart("/upload")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("文件上传成功"))
                .andExpect(jsonPath("$.fileName").value(fileName));
    }

    @Test
    void uploadFile_EmptyFile_ReturnsBadRequest() throws Exception {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "",
                MediaType.TEXT_PLAIN_VALUE,
                new byte[0]
        );

        // 执行测试
        mockMvc.perform(multipart("/upload")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("请选择要上传的文件"));
    }

    @Test
    void uploadFile_ServiceException_ReturnsBadRequest() throws Exception {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "测试文件内容".getBytes()
        );

        // 模拟服务层异常
        when(fileService.storeFile(any())).thenThrow(new IOException("存储文件失败"));

        // 执行测试
        mockMvc.perform(multipart("/upload")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("文件上传失败: 存储文件失败"));
    }
}
