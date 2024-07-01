package com.project.videodemo.key;

import lombok.Data;

import javax.crypto.SecretKey;
import java.util.Arrays;

public class KeyResponse {

    @Data
    public static class UserKey{
        private String key;

        public UserKey(String key) {
            this.key = key;
        }
    }

    @Data
    public static class LicenseKey {
        private String keyId;
        private String contentKey;

        public LicenseKey(String keyId, String contentKey) {
            this.keyId = keyId;
            this.contentKey = contentKey;
        }
    }
}
