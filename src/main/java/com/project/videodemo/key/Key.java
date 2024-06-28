package com.project.videodemo.key;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Data
@Table(name = "key_tb")
public class Key {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private String userKey;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Key(Integer id, Integer userId, String userKey, LocalDateTime createdAt) {
        this.Id = id;
        this.userId = userId;
        this.userKey = userKey;
        this.createdAt = createdAt;
    }
}
