package org.example.langchain4jdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FileStorageConfig {

    public static final String FILE_UPLOAD_DIR = "/file";

    @Bean
    public File createUploadDirectory() {
        File directory = new File(FILE_UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
}
