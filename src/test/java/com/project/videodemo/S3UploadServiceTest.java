package com.project.videodemo;

import com.project.videodemo.video.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class S3UploadServiceTest {

    @Autowired
    private VideoService videoService;

    @Test
    public void testUploadFile() throws Exception {
        // 임시 텍스트 파일 생성
        Path filePath = Paths.get("test-upload.txt");
        Files.write(filePath, "Hello, S3!".getBytes());

        // 파일을 S3에 업로드
        videoService.uploadFile(filePath);

        // 테스트 후 파일 삭제
        Files.deleteIfExists(filePath);
    }
}