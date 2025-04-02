package github.gunkim.climbingcalendar.infrastructure.jpa.user.entity;

import github.gunkim.climbingcalendar.domain.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@AllArgsConstructor
@Entity(name = "users")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String profileImage;
    private Instant createdAt;
    private Instant updatedAt;

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .id(user.id())
                .email(user.email())
                .name(user.name())
                .profileImage(user.profileImage())
                .createdAt(user.createdAt())
                .updatedAt(user.updatedAt())
                .build();
    }

    public User toDomain() {
        return new User(id, email, name, profileImage, createdAt, updatedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(name, that.name) && Objects.equals(profileImage, that.profileImage) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, profileImage, createdAt, updatedAt);
    }
}
