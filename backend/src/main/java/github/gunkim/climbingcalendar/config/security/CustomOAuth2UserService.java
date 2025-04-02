package github.gunkim.climbingcalendar.config.security;

import github.gunkim.climbingcalendar.domain.user.model.User;
import github.gunkim.climbingcalendar.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final KakaoOAuthAttributesAdapter kakaoOAuthAttributesAdapter;
    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        var registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (!kakaoOAuthAttributesAdapter.supports(registrationId)) {
            throw new IllegalArgumentException("지원하지 않는 OAuth2 벤더입니다.");
        }
        var attributes = oAuthAttributes(oAuth2User);
        var user = saveOrUpdate(attributes);

        return new CustomOAuth2User(
                List.of(),
                attributes.attributes(),
                attributes.nameAttributeKey(),
                user
        );
    }

    private OAuthAttributes oAuthAttributes(OAuth2User oAuth2User) {
        return kakaoOAuthAttributesAdapter
                .toOAuthAttributes(oAuth2User.getAttributes());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.email())
                .map(entity -> entity.update(attributes.name(), attributes.picture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
