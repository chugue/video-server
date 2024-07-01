package com.project.videodemo.key;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

public class KeyRequest {

    @NoArgsConstructor
    @Data
    public static class SubscribeDTO {
        @NotNull(message = "userId가 공백일 수 없습니다.")
        @Min(value = 1, message = "유효하지 않은 사용자 번호 입니다.")
        @Max(value = 999, message = "유효하지 않은 사용자 번호 입니다.")
        private Integer userId;
    }

    @NoArgsConstructor
    @Data
    public static class GetVideoDTO {
        @NotNull(message = "userId가 공백일 수 없습니다.")
        @Min(value = 0, message = "유효하지 않은 사용자 번호 입니다.")
        @Max(value = 999, message = "유효하지 않은 사용자 번호 입니다.")
        private Integer userId;
        @NotEmpty(message = "key가 공백일 수 없습니다.")
        private String userKey;
        private Integer contentId;
    }
}
