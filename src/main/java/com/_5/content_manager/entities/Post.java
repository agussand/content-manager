package com._5.content_manager.entities;


import com._5.content_manager.models.post.Media;
import com._5.content_manager.models.post.AuthorInfo;
import com._5.content_manager.models.post.PostStats;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    @NotBlank(message = "Título es requerido")
    private String title;

    @Indexed(unique = true)
    @NotBlank(message = "Slug es requerido")
    private String slug;

    @NotBlank(message = "Contenido es requerido")
    private String content;

    private String resume;
    private AuthorInfo author;
    private List<String> tags;
    private String category;
    private Media media;
    private String visibility = "public";
    private String status = "published";
    private PostStats stats;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime publishedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;
}
