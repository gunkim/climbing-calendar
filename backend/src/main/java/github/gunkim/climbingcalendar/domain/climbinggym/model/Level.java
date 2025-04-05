package github.gunkim.climbingcalendar.domain.climbinggym.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Grade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class Level {
    private final LevelId id;
    private ClimbingGymId climbingGymId;
    private Color color;
    private Grade grade;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public Level(LevelId id, ClimbingGymId climbingGymId, Color color, Grade grade, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.climbingGymId = climbingGymId;
        this.color = color;
        this.grade = grade;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Level create(ClimbingGymId climbingGymId, Color color, Grade grade) {
        return Level.builder()
                .climbingGymId(climbingGymId)
                .color(color)
                .grade(grade)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
