package github.gunkim.climbingcalendar.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class User {
    private final Long id;
    private String email;
    private String name;
    private String password;
    private Instant createdAt;
    private Instant updatedAt;
}
