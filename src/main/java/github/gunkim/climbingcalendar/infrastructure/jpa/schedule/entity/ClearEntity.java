package github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ClearId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "clear")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClearEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long scheduleId;
    private Long levelId;
    private int count;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public ClearEntity(Long id, Long scheduleId, Long levelId, int count, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.levelId = levelId;
        this.count = count;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ClearEntity from(Clear clear) {
        return ClearEntity.builder()
                .id(clear.id().value())
                .scheduleId(clear.scheduleId().value())
                .levelId(clear.levelId().value())
                .count(clear.count())
                .createdAt(clear.createdAt())
                .updatedAt(clear.updatedAt())
                .build();
    }

    public Clear toDomain() {
        return new Clear(ClearId.from(id), ScheduleId.from(scheduleId), LevelId.from(levelId), count, createdAt, updatedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClearEntity that = (ClearEntity) o;
        return count == that.count && Objects.equals(id, that.id) && Objects.equals(scheduleId, that.scheduleId) && Objects.equals(levelId, that.levelId) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduleId, levelId, count, createdAt, updatedAt);
    }
}
