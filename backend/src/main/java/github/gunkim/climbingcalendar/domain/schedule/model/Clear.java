package github.gunkim.climbingcalendar.domain.schedule.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ClearId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

/**
 * 일정(schedule)에 대한 클리어(clear) 기록을 나타냅니다.
 */
@Getter
@ToString
@Accessors(fluent = true)
public final class Clear {
    private static final int MIN_COUNT = 1;
    private static final int MAX_COUNT = 20;

    private final ClearId id;
    private final ScheduleId scheduleId;
    private final LevelId levelId;
    private final int count;
    private final Instant createdAt;
    private final Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public Clear(ClearId id, ScheduleId scheduleId, LevelId levelId, int count, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.scheduleId = requireNonNull(scheduleId, "ScheduleId cannot be null");
        this.levelId = requireNonNull(levelId, "LevelId cannot be null");
        this.count = checkCountBounds(count);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Clear create(ScheduleId scheduleId, LevelId levelId, int count) {
        Instant now = Instant.now();
        return Clear.builder()
                .scheduleId(scheduleId)
                .levelId(levelId)
                .count(count)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    private int checkCountBounds(int count) {
        if (count < MIN_COUNT) {
            throw new IllegalArgumentException("Count must be greater than or equal to " + MIN_COUNT);
        }
        if (count > MAX_COUNT) {
            throw new IllegalArgumentException("Count must be less than or equal to " + MAX_COUNT);
        }
        return count;
    }
}