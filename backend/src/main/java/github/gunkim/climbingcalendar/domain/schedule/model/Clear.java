package github.gunkim.climbingcalendar.domain.schedule.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ClearId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class Clear {
    private final ClearId id;
    private ScheduleId scheduleId;
    private LevelId levelId;
    private int count;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public Clear(ClearId id, ScheduleId scheduleId, LevelId levelId, int count, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.levelId = levelId;
        this.count = count;
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
}
