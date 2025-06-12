package com._5.content_manager.entities;


import com._5.content_manager.models.comment.CommentMetadata;
import com._5.content_manager.models.comment.CommentStats;
import com._5.content_manager.models.post.AuthorInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    @NotBlank(message = "Post ID es requerido")
    private String postId;

    private AuthorInfo author;

    @NotBlank(message = "Contenido es requerido")
    private String content;

    private CommentStats stats;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;
}
