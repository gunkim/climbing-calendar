package github.gunkim.climbingcalendar.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println("CustomOAuth2SuccessHandler.onAuthenticationSuccess");
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String token = jwtTokenProvider.createToken(oAuth2User.user().id());

        response.setHeader("Authorization", "Bearer " + token);
        response.sendRedirect("/login");
    }
}
