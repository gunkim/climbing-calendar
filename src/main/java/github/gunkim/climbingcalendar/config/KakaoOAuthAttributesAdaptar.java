package github.gunkim.climbingcalendar.config;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoOAuthAttributesAdaptar {
    private static final String REGISTRATION_ID = "kakao";

    public OAuthAttributes toOAuthAttributes(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        properties.put("id", attributes.get("id"));

        return OAuthAttributes.builder()
                .name((String) properties.get("nickname"))
                .email((String) properties.get("email"))
                .picture((String) properties.get("profile_image"))
                .attributes(properties)
                .nameAttributeKey("nickname")
                .registrationId(REGISTRATION_ID)
                .build();
    }
}
