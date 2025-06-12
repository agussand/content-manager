package com._5.content_manager.models.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSettings {
    private boolean emailNotifications = true;
    private String profileVisibility = "public";
    private String theme = "light";
}
