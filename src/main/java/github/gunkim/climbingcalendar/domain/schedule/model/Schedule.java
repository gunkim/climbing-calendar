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
    private Long climbingGymId;
    private String title;
    private String memo;
    private Instant scheduleDate;
    private Instant createdAt;
    private Instant updatedAt;

    public static Schedule create(Long userId, Long climbingGymId, String title, String memo, Instant scheduleDate) {
        Instant now = Instant.now();
        return new Schedule(
                null,
                userId,
                climbingGymId,
                title,
                memo,
                scheduleDate,
                now,
                now
        );
    }
}
