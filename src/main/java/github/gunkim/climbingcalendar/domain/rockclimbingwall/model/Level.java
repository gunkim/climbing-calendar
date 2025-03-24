package github.gunkim.climbingcalendar.domain.rockclimbingwall.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class Level {
    private final Long id;
    private Long rockClimbingWallId;
    private String color;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
}
