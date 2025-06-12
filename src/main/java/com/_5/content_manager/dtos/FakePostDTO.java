package com._5.content_manager.dtos;

import lombok.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class FakePostDTO {
    private int id;
    private String title;
    private String body;
    private List<String> tags;
    private Map<String, Integer> reactions;
    private int views;
    private int userId;
}
