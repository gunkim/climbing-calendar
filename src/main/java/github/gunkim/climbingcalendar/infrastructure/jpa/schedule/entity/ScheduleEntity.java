package github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long climbingGymId;
    private String title;
    private String memo;
    private Instant scheduleDate;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public ScheduleEntity(Long id, Long userId, Long climbingGymId, String title, String memo, Instant scheduleDate, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.climbingGymId = climbingGymId;
        this.title = title;
        this.memo = memo;
        this.scheduleDate = scheduleDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleEntity that = (ScheduleEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(climbingGymId, that.climbingGymId) && Objects.equals(title, that.title) && Objects.equals(memo, that.memo) && Objects.equals(scheduleDate, that.scheduleDate) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, climbingGymId, title, memo, scheduleDate, createdAt, updatedAt);
    }

    public static ScheduleEntity from(Schedule schedule) {
        return ScheduleEntity.builder()
                .id(schedule.id())
                .userId(schedule.userId())
                .climbingGymId(schedule.climbingGymId())
                .title(schedule.title())
                .scheduleDate(schedule.scheduleDate())
                .memo(schedule.memo())
                .scheduleDate(schedule.scheduleDate())
                .createdAt(schedule.createdAt())
                .updatedAt(schedule.updatedAt())
                .build();
    }

    public Schedule toDomain() {
        return new Schedule(id, userId, climbingGymId, title, memo, scheduleDate, createdAt, updatedAt);
    }
}
