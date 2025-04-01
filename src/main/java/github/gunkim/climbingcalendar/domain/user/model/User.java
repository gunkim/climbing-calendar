package github.gunkim.climbingcalendar.domain.user.model;

import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class User {
    private final UserId id;
    private String email;
    private String name;
    private String profileImage;
    private Instant createdAt;
    private Instant updatedAt;
}
