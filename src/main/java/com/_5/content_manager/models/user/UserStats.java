package com._5.content_manager.models.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserStats {
    private int postsCount = 0;
    private int followersCount = 0;
    private int followingCount = 0;
}
