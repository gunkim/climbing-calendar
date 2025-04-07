package github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Grade;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Entity(name = "level")
@SQLRestriction("deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE level SET deleted_at = NOW() WHERE id = ?")
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

    @Builder(access = AccessLevel.PRIVATE)
    public LevelEntity(Long id, Long climbingGymId, Color color, int startGrade, int endGrade, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.climbingGymId = climbingGymId;
        this.color = color;
        this.startGrade = startGrade;
        this.endGrade = endGrade;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static LevelEntity from(Level level) {
        return LevelEntity.builder()
                .id(Optional.ofNullable(level.id()).map(LevelId::value).orElse(null))
                .climbingGymId(level.climbingGymId().value())
                .color(level.color())
                .startGrade(level.grade().startGrade())
                .endGrade(level.grade().endGrade())
                .createdAt(level.createdAt())
                .updatedAt(level.updatedAt())
                .build();
    }

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
