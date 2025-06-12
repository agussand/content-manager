package com._5.content_manager.models.post;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostStats {
    private int viewsCount = 0;
    private int likesCount = 0;
    private int commentsCount = 0;
}
