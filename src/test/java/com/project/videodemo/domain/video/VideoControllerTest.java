package com.project.videodemo.domain.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.videodemo.video.Video;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Sql("classpath:db/data.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class VideoControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private EntityManager em;


    @BeforeEach
    public void setUp() {
        em.clear();
    }

    @Test
    public void testSingleFileUpload() throws Exception {
        // 파일 시스템 경로에서 파일 읽기
        Path filePath = Paths.get("videolocation/rush_hour.mp4");
        byte[] fileContent = Files.readAllBytes(filePath);

        // Mock 파일 생성
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "rush_hour.mp4",
                "video/mp4",
                fileContent
        );

        // 파일 업로드 요청 수행
        MvcResult result = mvc.perform(multipart("/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.filePath").value(containsString("rush_hour.mpd"))).andReturn();

        // 추가적인 검증 작업
        String response = result.getResponse().getContentAsString();
        System.out.println(response);  // 응답 내용 출력1

    }
}
