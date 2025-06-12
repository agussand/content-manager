package com._5.content_manager.dtos;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class FakeCommentDTO {
    private int id;
    private String body;
    private int postId;
    private int likes;
    private Map<String, Object> user;
}
