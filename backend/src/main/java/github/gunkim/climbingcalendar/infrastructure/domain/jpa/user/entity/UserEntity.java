package github.gunkim.climbingcalendar.infrastructure.domain.jpa.user.entity;

import github.gunkim.climbingcalendar.domain.user.model.User;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Entity(name = "users")
@SQLRestriction("deleted_at is NULL")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
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
                .id(Optional.ofNullable(user.id()).map(UserId::value).orElse(null))
                .email(user.email())
                .name(user.name())
                .profileImage(user.profileImage())
                .createdAt(user.createdAt())
                .updatedAt(user.updatedAt())
                .build();
    }

    public User toDomain() {
        return new User(UserId.from(id), email, name, profileImage, createdAt, updatedAt);
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
