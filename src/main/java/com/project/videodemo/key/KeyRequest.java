package com.project.videodemo.key;

import lombok.Data;

public class KeyRequest {

    @Data
    public static class SubscribeDTO {
        private Integer userId;
    }

    @Data
    public static class GetVideoDTO {
        private Integer userId;
        private String userKey;
        private Integer contentId;
    }
}
