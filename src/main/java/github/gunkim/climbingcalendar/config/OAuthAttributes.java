package github.gunkim.climbingcalendar.config;

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
    public User toDomain() {
        return User.signUp(email, name, picture);
    }
}