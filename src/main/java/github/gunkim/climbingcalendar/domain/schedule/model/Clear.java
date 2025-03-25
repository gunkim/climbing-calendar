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
    private Long scheduleId;
    private Long levelId;
    private int count;
    private Instant createdAt;
    private Instant updatedAt;

    public static Clear create(Long scheduleId, Long levelId, int count) {
        Instant now = Instant.now();
        return new Clear(
                null,
                scheduleId,
                levelId,
                count,
                now,
                now
        );
    }
}
