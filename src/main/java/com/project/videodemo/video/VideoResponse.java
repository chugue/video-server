package com.project.videodemo.video;


import lombok.Data;

import java.nio.file.Path;

public class VideoResponse {


    @Data
    public static class RespDTO {
        private String filePath;

        public RespDTO(Path mpdFilePath) {
            this.filePath = mpdFilePath.toString();
        }
    }
}
