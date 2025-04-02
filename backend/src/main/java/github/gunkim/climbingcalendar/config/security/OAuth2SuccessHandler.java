package github.gunkim.climbingcalendar.config.security;

import github.gunkim.climbingcalendar.application.JwtProvider;
import github.gunkim.climbingcalendar.domain.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final String TOKEN_PARAM = "token";

    private final JwtProvider jwtProvider;
    private final String frontendUrl;

    public OAuth2SuccessHandler(JwtProvider jwtProvider, @Value("${frontend-url}") String frontendUrl) {
        this.jwtProvider = jwtProvider;
        this.frontendUrl = frontendUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        handleSuccessfulAuthentication(authentication, request, response);
    }

    private void handleSuccessfulAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomOAuth2User oauth2User = extractOAuth2User(authentication);
        String token = generateTokenForUser(oauth2User.getUser());
        String redirectUrl = buildRedirectUrl(token);
        log.trace("Redirect URL: {}", redirectUrl);

        performRedirect(request, response, redirectUrl);
    }

    private CustomOAuth2User extractOAuth2User(Authentication authentication) {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        log.trace("OAuth2 인증 성공: {}", oauth2User.getName());
        return oauth2User;
    }

    private String generateTokenForUser(User user) {
        return jwtProvider.createToken(user.id());
    }

    private String buildRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString(frontendUrl)
                .queryParam(TOKEN_PARAM, token)
                .build()
                .toString();
    }

    private void performRedirect(HttpServletRequest request, HttpServletResponse response, String redirectUrl) throws IOException {
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}