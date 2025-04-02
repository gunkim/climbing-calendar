package github.gunkim.climbingcalendar.domain.schedule.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;

public record ClearWithLevel(
        Clear clear,
        Level level
) {
}
