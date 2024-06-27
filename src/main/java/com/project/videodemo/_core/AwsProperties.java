package com.project.videodemo._core;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {
    private Credentials credentials;
    private String region;
    private String bucket;

    @Data
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }
}

