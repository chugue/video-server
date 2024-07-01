package com.project.videodemo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Objects;
@EnableAspectJAutoProxy
@SpringBootApplication
public class VideoDemoApplication {

    public static void main(String[] args) {
        // .env 파일에서 환경 변수 설정
        Dotenv dotenv = Dotenv.load();

        // .env 파일에서 환경 변수 설정
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(VideoDemoApplication.class, args);
    }
}
