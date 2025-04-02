package github.gunkim.climbingcalendar.config.security;

import github.gunkim.climbingcalendar.domain.user.model.User;
import lombok.Builder;

import java.util.Map;

@Builder
public record OAuthAttributes(
        Map<String, Object> attributes,
        String nameAttributeKey,
        String name,
        String email,
        String picture,
        String registrationId
) {
    public User toEntity() {
        return User.registration(email, name, picture);
    }
}
