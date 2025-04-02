package github.gunkim.climbingcalendar.config.security;

import github.gunkim.climbingcalendar.domain.user.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final User user;

    @Override
    public String getName() {
        return nameAttributeKey;
    }

    @Override
    public String toString() {
        return "CustomOAuth2User{" +
                "authorities=" + authorities +
                ", attributes=" + attributes +
                ", nameAttributeKey='" + nameAttributeKey + '\'' +
                ", user=" + user +
                '}';
    }
}
