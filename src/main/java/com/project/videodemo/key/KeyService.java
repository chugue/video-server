package com.project.videodemo.key;


import com.project.videodemo.error.exception.Exception404;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class KeyService {
    private final KeyRepository keyRepository;
    @Value("${content.contentKey}")
    private String contentKey;
    @Value("${content.keyId}")
    private String keyId;


    @Transactional
    public KeyResponse.UserKey subscribeUser(KeyRequest.SubscribeDTO reqDTO) throws Exception {
        String userKey = UUID.randomUUID().toString();

        Key key = keyRepository.save(Key.builder()
                .userId(reqDTO.getUserId())
                .userKey(userKey)
                .createdAt(LocalDateTime.now()).build());

        return new KeyResponse.UserKey(userKey);
    }


    public KeyResponse.LicenseKey getLisence(KeyRequest.GetVideoDTO reqDTO) {
        Key storedKey = keyRepository.findById(reqDTO.getUserId()).orElse(null);

        if (storedKey != null && storedKey.getUserKey().equals(reqDTO.getUserKey())){
            return new KeyResponse.LicenseKey(keyId, contentKey);
        } else {
            throw new RuntimeException("Invalid user key");
        }
    }
}
