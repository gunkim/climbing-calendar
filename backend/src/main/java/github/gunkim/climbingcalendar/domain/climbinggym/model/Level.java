package github.gunkim.climbingcalendar.domain.climbinggym.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class Level {
    private final LevelId id;
    private ClimbingGymId climbingGymId;
    private Color color;
    private Grade grade;
    private Instant createdAt;
    private Instant updatedAt;
}
