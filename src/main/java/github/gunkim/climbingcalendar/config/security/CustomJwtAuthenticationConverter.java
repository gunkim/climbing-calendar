package github.gunkim.climbingcalendar.config.security;

import github.gunkim.climbingcalendar.api.AuthenticatedUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Long userIdClaim = (Long) jwt.getClaims().get("userId");

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(userIdClaim);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        AbstractAuthenticationToken JwtAuthenticationToken = jwtAuthenticationConverter.convert(jwt);

        var authorities = JwtAuthenticationToken.getAuthorities();
        return new CustomJwtAuthenticationToken(authenticatedUser, authorities);
    }
}