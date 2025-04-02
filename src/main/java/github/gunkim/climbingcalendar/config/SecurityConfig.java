package github.gunkim.climbingcalendar.config;

import github.gunkim.climbingcalendar.config.security.CustomOAuth2UserService;
import github.gunkim.climbingcalendar.config.security.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/"))
                .authorizeHttpRequests(this::configureHttpPermissions)
                .oauth2Login(this::configureOAuth2Login)
                .build();
    }

    private void configureHttpPermissions(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requestMatcherRegistry) {
        requestMatcherRegistry.anyRequest().authenticated();
    }

    private void configureOAuth2Login(OAuth2LoginConfigurer<HttpSecurity> oauth2LoginConfigurer) {
        oauth2LoginConfigurer
                .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))
                .successHandler(oAuth2SuccessHandler);
    }
}
