package github.gunkim.climbingcalendar.domain.user.model;

import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@ToString
@Accessors(fluent = true)
public class User {
    private final UserId id;
    private String email;
    private String name;
    private String profileImage;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public User(UserId id, String email, String name, String profileImage, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User registration(String email, String name, String profileImage) {
        return User.builder()
                .email(email)
                .name(name)
                .profileImage(profileImage)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    public User update(String name, String picture) {
        this.name = name;
        this.profileImage = picture;
        this.updatedAt = Instant.now();
        return this;
    }
}
