package github.gunkim.climbingcalendar.domain.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class Clear {
    private final Long id;
    private Long rockClimbingWallId;
    private Long LevelId;
    private Long scheduleId;
    private String color;
    private int clearCount;
    private Instant createdAt;
    private Instant updatedAt;
}
