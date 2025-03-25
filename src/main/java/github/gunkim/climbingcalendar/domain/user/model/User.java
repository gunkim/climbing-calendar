package github.gunkim.climbingcalendar.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class User {
    private final Long id;
    private String email;
    private String name;
    private String profileImage;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public User(Long id, String email, String name, String profileImage, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User signUp(String email, String name, String profileImage) {
        var now = Instant.now();
        return User.builder()
                .email(email)
                .name(name)
                .profileImage(profileImage)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public User update(String name, String picture) {
        this.name = name;
        this.profileImage = picture;
        this.updatedAt = Instant.now();
        return this;
    }
}
