package github.gunkim.climbingcalendar.application;

import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKeySpec secretKey;

    public JwtProvider(SecretKeySpec secretKey) {
        this.secretKey = secretKey;
    }

    public String createToken(UserId userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plusMinutes(EXPIRATION_MINUTES);

        return Jwts.builder()
                .signWith(secretKey)
                .claim(USER_ID_PAYLOAD_PARAMETER, userId.value())
                .issuedAt(toDate(now))
                .expiration(toDate(expirationTime))
                .compact();
    }

    public UserId parse(String jws) {
        return UserId.from(Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jws)
                .getPayload()
                .get(USER_ID_PAYLOAD_PARAMETER, Long.class));
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

    private static final String USER_ID_PAYLOAD_PARAMETER = "userId";
    private static final long EXPIRATION_MINUTES = 30L;
}