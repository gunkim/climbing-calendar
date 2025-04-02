package github.gunkim.climbingcalendar.config.security;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoOAuthAttributesAdapter {
    private static final String REGISTRATION_ID = "kakao";

    public OAuthAttributes toOAuthAttributes(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        properties.put("id", attributes.get("id"));

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return OAuthAttributes.builder()
                .name((String) properties.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) properties.get("profile_image"))
                .attributes(properties)
                .nameAttributeKey("nickname")
                .registrationId(REGISTRATION_ID)
                .build();
    }

    public boolean supports(String registrationId) {
        return REGISTRATION_ID.equals(registrationId);
    }
}
