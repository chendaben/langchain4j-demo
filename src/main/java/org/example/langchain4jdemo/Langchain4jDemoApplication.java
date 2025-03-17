package org.example.langchain4jdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@MapperScan("org.example.langchain4jdemo.mapper")
@OpenAPIDefinition(
    info = @Info(
        title = "Langchain4j Demo API",
        version = "1.0",
        description = "Langchain4j Demo Application API Documentation"
    )
)
public class Langchain4jDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Langchain4jDemoApplication.class, args);
    }

}
