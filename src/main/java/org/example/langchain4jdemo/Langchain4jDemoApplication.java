package org.example.langchain4jdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.langchain4jdemo.mapper")
public class Langchain4jDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Langchain4jDemoApplication.class, args);
    }

}
