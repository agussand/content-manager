package com._5.content_manager.models.comment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentStats {
    private int likesCount = 0;
    private int repliesCount = 0;
}
