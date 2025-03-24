package github.gunkim.climbingcalendar.domain.climbinggym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class Level {
    private final Long id;
    private Long climbingGymId;
    private String color;
    private int startGrade;
    private int endGrade;
    private Instant createdAt;
    private Instant updatedAt;
}
