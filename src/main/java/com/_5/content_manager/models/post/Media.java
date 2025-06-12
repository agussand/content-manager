package com._5.content_manager.models.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Media {
    private String url;
    private String type;
    private String alt;
    private int duration;
    private String quality;
}
