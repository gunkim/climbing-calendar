package github.gunkim.climbingcalendar.api.user.model.response;

import github.gunkim.climbingcalendar.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserResponse(
        Long id,
        String email,
        String name,
        String profileImage
) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.id().value())
                .email(user.email())
                .name(user.name())
                .profileImage(user.profileImage())
                .build();
    }
}
