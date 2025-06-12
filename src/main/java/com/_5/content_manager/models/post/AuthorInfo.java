package com._5.content_manager.models.post;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorInfo {
    private String userId;
    private String username;
    private String displayName;
}
