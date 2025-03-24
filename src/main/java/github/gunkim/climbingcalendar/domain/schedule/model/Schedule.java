package github.gunkim.climbingcalendar.domain.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class Schedule {
    private final Long id;
    private Long userId;
    private Long ClimbingGymId;
    private String title;
    private String memo;
    private Instant scheduleDate;
    private Instant createdAt;
    private Instant updatedAt;
}
