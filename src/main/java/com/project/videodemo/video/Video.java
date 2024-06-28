package com.project.videodemo.video;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name = "video_tb")
public class Video {
    @Id
    private Integer id;
    private String title;
    private String videoPath;

    @Builder
    public Video(Integer id, String title, String videoPath) {
        this.id = id;
        this.title = title;
        this.videoPath = videoPath;
    }
}
