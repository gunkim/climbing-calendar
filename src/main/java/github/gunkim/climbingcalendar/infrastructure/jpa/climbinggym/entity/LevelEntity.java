package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "level")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer climbingGymId;
    private String color;
    private int startGrade;
    private int endGrade;
    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelEntity that = (LevelEntity) o;
        return startGrade == that.startGrade && endGrade == that.endGrade && Objects.equals(id, that.id) && Objects.equals(climbingGymId, that.climbingGymId) && Objects.equals(color, that.color) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, climbingGymId, color, startGrade, endGrade, createdAt, updatedAt);
    }
}
