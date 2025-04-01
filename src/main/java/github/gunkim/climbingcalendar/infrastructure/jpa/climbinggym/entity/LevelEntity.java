package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Grade;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "level")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long climbingGymId;
    @Enumerated(EnumType.STRING)
    private Color color;
    private int startGrade;
    private int endGrade;
    private Instant createdAt;
    private Instant updatedAt;

    public Level toDomain() {
        return new Level(LevelId.from(id), ClimbingGymId.from(climbingGymId), color, Grade.from(startGrade, endGrade), createdAt, updatedAt);
    }

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
