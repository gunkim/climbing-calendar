package github.gunkim.climbingcalendar.domain.schedule.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.exception.UnauthorizedScheduleException;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

/**
 * 일정을 나타내는 도메인 클래스입니다.
 */
@Getter
@ToString
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
    public Schedule(ScheduleId id,
                    UserId userId,
                    ClimbingGymId climbingGymId,
                    String title,
                    String memo,
                    Instant scheduleDate,
                    Instant createdAt,
                    Instant updatedAt) {
        this.id = id;
        this.userId = requireNonNull(userId, "UserId cannot be null");
        this.climbingGymId = requireNonNull(climbingGymId, "ClimbingGymId cannot be null");
        this.title = requireNonNull(title, "Title cannot be null");
        this.memo = memo;
        this.scheduleDate = requireNonNull(scheduleDate, "ScheduleDate cannot be null");
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Schedule create(UserId userId,
                                  ClimbingGymId climbingGymId,
                                  String title,
                                  String memo,
                                  Instant scheduleDate) {
        Instant currentTime = Instant.now();
        return Schedule.builder()
                .userId(userId)
                .climbingGymId(climbingGymId)
                .title(title)
                .memo(memo)
                .scheduleDate(scheduleDate)
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
    }

    public void update(ClimbingGymId climbingGymId, String title, String memo, Instant scheduleDate) {
        this.climbingGymId = requireNonNull(climbingGymId, "ClimbingGymId cannot be null");
        this.title = requireNonNull(title, "Title cannot be null");
        this.memo = memo;
        this.scheduleDate = requireNonNull(scheduleDate, "ScheduleDate cannot be null");
        this.updatedAt = Instant.now();
    }

    public void validateOwner(UserId userId) {
        if (!this.userId().equals(userId)) {
            throw new UnauthorizedScheduleException("User is not authorized to update this schedule.");
        }
    }
}