package github.gunkim.climbingcalendar.domain.schedule.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class Schedule {
    private final ScheduleId id;
    private UserId userId;
    private ClimbingGymId climbingGymId;
    private String title;
    private String memo;
    private Instant scheduleDate;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public Schedule(ScheduleId id, UserId userId, ClimbingGymId climbingGymId, String title, String memo, Instant scheduleDate, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.climbingGymId = climbingGymId;
        this.title = title;
        this.memo = memo;
        this.scheduleDate = scheduleDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Schedule create(UserId userId, ClimbingGymId climbingGymId, String title, String memo, Instant scheduleDate) {
        Instant now = Instant.now();

        return Schedule.builder()
                .userId(userId)
                .climbingGymId(climbingGymId)
                .title(title)
                .memo(memo)
                .scheduleDate(scheduleDate)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void update(ClimbingGymId climbingGymId, String title, String memo, Instant scheduleDate) {
        Instant now = Instant.now();
        this.climbingGymId = climbingGymId;
        this.title = title;
        this.memo = memo;
        this.scheduleDate = scheduleDate;
        this.updatedAt = now;
    }
}
