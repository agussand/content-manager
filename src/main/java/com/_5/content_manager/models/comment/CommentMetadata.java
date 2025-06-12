package com._5.content_manager.models.comment;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentMetadata {
    private String ipAddress;
    private String userAgent;
    private boolean edited = false;
    private LocalDateTime editedAt;
}
