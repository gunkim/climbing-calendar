package github.gunkim.climbingcalendar.domain.schedule.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class Schedule {
    private final Long id;
    private Long userId;
    private Long climbingGymId;
    private String title;
    private String memo;
    private Instant scheduleDate;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public Schedule(Long id, Long userId, Long climbingGymId, String title, String memo, Instant scheduleDate, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.climbingGymId = climbingGymId;
        this.title = title;
        this.memo = memo;
        this.scheduleDate = scheduleDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Schedule create(Long userId, Long climbingGymId, String title, String memo, Instant scheduleDate) {
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

    public Schedule update(Long climbingGymId, String title, String memo, Instant scheduleDate) {
        Instant now = Instant.now();

        return Schedule.builder()
                .id(id)
                .userId(userId)
                .climbingGymId(climbingGymId)
                .title(title)
                .memo(memo)
                .scheduleDate(scheduleDate)
                .createdAt(createdAt)
                .updatedAt(now)
                .build();
    }
}
