package com.project.videodemo.domain.key;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.videodemo.key.Key;
import com.project.videodemo.key.KeyRepository;
import com.project.videodemo.key.KeyRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Sql("classpath:db/data.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class KeyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KeyRepository keyRepository;


    @Test
    public void testGetVideo() throws Exception {

        // Mock 요청 데이터 생성
        KeyRequest.GetVideoDTO reqDTO = new KeyRequest.GetVideoDTO();
        reqDTO.setUserId(1);
        reqDTO.setUserKey("1234");

        // POST 요청 수행
        mvc.perform(post("/get-license")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.license").isNotEmpty());


    }

    @Test
    public void getLicenseFailTest() throws Exception {
        // Mock 요청 데이터 생성
        KeyRequest.GetVideoDTO reqDTO = new KeyRequest.GetVideoDTO();
        reqDTO.setUserId(1);
        reqDTO.setUserKey("invalid-key"); // 올바르지 않은 userKey로 설정

        // POST 요청 수행 및 예외 검증
        mvc.perform(post("/get-license")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDTO)))
                .andExpect(status().isBadRequest()) // 400 상태 코드 기대
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(RuntimeException.class)
                        .hasMessageContaining("Invalid user key"))
                .andDo(print());
    }

    @Test
    public void testSubscribeUser() throws Exception {
        // Mock 요청 데이터 생성
        KeyRequest.SubscribeDTO reqDTO = new KeyRequest.SubscribeDTO();
        reqDTO.setUserId(1);

        // POST 요청 수행
        mvc.perform(post("/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDTO)))
                .andExpect(status().isOk());

        // 데이터베이스 검증
        Optional<Key> optionalKey = keyRepository.findAll().stream().findFirst();
        assertThat(optionalKey).isPresent();
        Key key = optionalKey.get();
        assertThat(key.getUserId()).isEqualTo(1);
        assertThat(key.getUserKey()).isNotEmpty();
    }

    @Test
    public void testSubscribeUserWithInvalidData() throws Exception {
        // Mock 잘못된 요청 데이터 생성
        KeyRequest.SubscribeDTO reqDTO = new KeyRequest.SubscribeDTO();
        reqDTO.setUserId(-1); // 잘못된 userId

        // POST 요청 수행
        mvc.perform(post("/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDTO)))
                .andExpect(status().isBadRequest()); // 잘못된 요청으로 인한 400 상태 코드 기대

        // 데이터베이스 검증 (키가 저장되지 않아야 함)
        Optional<Key> optionalKey = keyRepository.findAll().stream().findFirst();
        assertThat(optionalKey).isNotPresent() ; // 데이터베이스에 키가 저장되지 않아야 함
    }


}
