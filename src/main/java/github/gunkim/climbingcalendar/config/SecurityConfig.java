package github.gunkim.climbingcalendar.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(this::configureHttpPermissions)
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/"))
                .oauth2Login(this::configureOAuth2Login)
                .build();
    }

    private void configureHttpPermissions(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requestMatcherRegistry) {
        requestMatcherRegistry.anyRequest().authenticated();
    }

    private void configureOAuth2Login(OAuth2LoginConfigurer<HttpSecurity> oauth2LoginConfigurer) {
        oauth2LoginConfigurer.userInfoEndpoint(endpointCustomizer -> endpointCustomizer.userService(customOAuth2UserService));
        oauth2LoginConfigurer.successHandler(customOAuth2SuccessHandler);
    }
}
