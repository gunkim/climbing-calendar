package github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "clear")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClearEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long scheduleId;
    private Long levelId;
    private int count;
    private Instant createdAt;
    private Instant updatedAt;

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
