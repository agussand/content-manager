package com._5.content_manager.entities;


import com._5.content_manager.models.user.Profile;
import com._5.content_manager.models.user.UserSettings;
import com._5.content_manager.models.user.UserStats;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Username es requerido")
    private String username;

    @Indexed(unique = true)
    @Email(message = "Email debe ser válido")
    @NotBlank(message = "Email es requerido")
    private String email;

    @NotBlank(message = "Password es requerido")
    private String passwordHash;

    private Profile profile;
    private List<String> interests;
    private Map<String, String> socialLinks;
    private UserStats stats;
    private UserSettings settings;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime lastLogin;

    private String status = "active";
}
