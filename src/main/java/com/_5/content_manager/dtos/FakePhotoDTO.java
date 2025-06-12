package com._5.content_manager.dtos;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class FakePhotoDTO {
    private int id;
    private int width;
    private int height;
    private String url;
    private String photographer;
    private String photographer_url;
    private long photographer_id;
    private String avg_color;
    private Map<String, String> src;
    private boolean liked;
    private String alt;
}
