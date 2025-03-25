package github.gunkim.climbingcalendar.domain.schedule.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class Clear {
    private final Long id;
    private Long scheduleId;
    private Long levelId;
    private int count;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public Clear(Long id, Long scheduleId, Long levelId, int count, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.levelId = levelId;
        this.count = count;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Clear create(Long scheduleId, Long levelId, int count) {
        Instant now = Instant.now();

        return Clear.builder()
                .scheduleId(scheduleId)
                .levelId(levelId)
                .count(count)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
