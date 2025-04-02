package github.gunkim.climbingcalendar.config;

import github.gunkim.climbingcalendar.config.security.CustomJwtAuthenticationConverter;
import github.gunkim.climbingcalendar.config.security.CustomOAuth2UserService;
import github.gunkim.climbingcalendar.config.security.OAuth2SuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecretKeySpec secretKey;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(this::configureCors)
                .headers(this::configureHeaders)
                .sessionManagement(this::configureSessionManagement)
                .authorizeHttpRequests(this::configureHttpPermissions)
                .oauth2Login(this::configureOAuth2Login)
                .exceptionHandling(this::configureExceptionHandling)
                .oauth2ResourceServer(this::configureJwt)
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    private SessionManagementConfigurer<HttpSecurity> configureSessionManagement(SessionManagementConfigurer<HttpSecurity> sessionConfigurer) {
        return sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private HeadersConfigurer<HttpSecurity> configureHeaders(HeadersConfigurer<HttpSecurity> headersConfigurer) {
        return headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
    }

    private ExceptionHandlingConfigurer<HttpSecurity> configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {
        return exceptionHandling.authenticationEntryPoint((request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        );
    }

    private void configureJwt(OAuth2ResourceServerConfigurer<HttpSecurity> oauth2ResourceServerConfigurer) {
        oauth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()));
        oauth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(new CustomJwtAuthenticationConverter()));
    }

    private void configureHttpPermissions(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requestMatcherRegistry) {
        requestMatcherRegistry.anyRequest().authenticated();
    }

    private void configureOAuth2Login(OAuth2LoginConfigurer<HttpSecurity> oauth2LoginConfigurer) {
        oauth2LoginConfigurer
                .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))
                .successHandler(oAuth2SuccessHandler);
    }

    private void configureCors(CorsConfigurer<HttpSecurity> cors) {
        cors.configurationSource(request -> {
            var corsConfig = new CorsConfiguration();
            corsConfig.setAllowedOriginPatterns(List.of("*"));
            corsConfig.setAllowedMethods(List.of("*"));
            corsConfig.setAllowedHeaders(List.of("*"));
            corsConfig.setAllowCredentials(true);
            return corsConfig;
        });
    }
}
