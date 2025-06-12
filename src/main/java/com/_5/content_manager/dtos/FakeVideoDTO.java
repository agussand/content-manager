package com._5.content_manager.dtos;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class FakeVideoDTO {
    private long id;
    private int width;
    private int height;
    private int duration;
    private String full_res;
    private List<String> tags;
    private String url;
    private String image;
    private String avg_color;
    private User user;
    private List<VideoFile> video_files;
    private List<VideoPicture> video_pictures;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Data
    public static class User {
        private long id;
        private String name;
        private String url;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Data
    public static class VideoFile {
        private long id;
        private String quality;
        private String file_type;
        private int width;
        private int height;
        private double fps;
        private String link;
        private long size;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Data
    public static class VideoPicture {
        private long id;
        private int nr;
        private String picture;
    }
}
