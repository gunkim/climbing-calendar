package github.gunkim.climbingcalendar.config;

import github.gunkim.climbingcalendar.domain.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public record CustomOAuth2User(
        User user,
        OAuthAttributes attributes
) implements OAuth2User {
    @Override
    public Map<String, Object> getAttributes() {
        return attributes.attributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return this.getAttribute(this.attributes.nameAttributeKey()).toString();
    }
}
