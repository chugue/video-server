package com.project.videodemo.key;

import com.project.videodemo._core.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class KeyController {
    private final KeyService keyService;

    // userkey 발급
    @PostMapping("/api/subscribe") // TODO : CORS설정같은 걸로 이 메소드 막아야됨 (프론트서버에서만 요청 가능하도록)
    public ResponseEntity<?> subscribeUser(@RequestBody KeyRequest.SubscribeDTO reqDTO) throws Exception {
        KeyResponse.UserKey respDTO = keyService.subscribeUser(reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 라이센스 발급
    @PostMapping("/api/get-license")
    public ResponseEntity<?> getVideo(@RequestBody KeyRequest.GetVideoDTO reqDTO) throws Exception {
        KeyResponse.LicenseKey respDTO = keyService.getLisence(reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }
}
