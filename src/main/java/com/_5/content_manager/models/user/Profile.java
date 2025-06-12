package com._5.content_manager.models.user;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Profile {
    private String firstName;
    private String lastName;
    private String bio;
    private String avatarUrl;
    private LocalDateTime birthDate;
}
