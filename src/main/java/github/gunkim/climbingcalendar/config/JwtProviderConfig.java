package github.gunkim.climbingcalendar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtProviderConfig {
    private static final String ALGORITHM = "HmacSHA256";

    @Bean
    public SecretKeySpec secretKeySpec(@Value("${jwt.secret-key}") String symmetricKey) {
        return new SecretKeySpec(symmetricKey.getBytes(), ALGORITHM);
    }
}
