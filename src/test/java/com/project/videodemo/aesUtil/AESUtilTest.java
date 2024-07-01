package com.project.videodemo.aesUtil;

import com.project.videodemo._core.AESUtil;
import com.project.videodemo.key.KeyRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AESUtilTest {
    @Autowired
    private AESUtil aesUtil;

//    @Test
//    public void generateKey_test() throws Exception {
//        // given
//        KeyRequest.SubscribeDTO reqDTO = new KeyRequest.SubscribeDTO(1, "1234");
//        // when
//        String userKey = aesUtil.generateKey(reqDTO.getUserId(), reqDTO.getPassword());
//        // eye
//        System.out.println("👉👉👉👉👉👉👉👉👉" + userKey);
//        // then
//
//    }
}
