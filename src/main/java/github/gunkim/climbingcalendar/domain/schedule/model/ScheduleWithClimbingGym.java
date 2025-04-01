package github.gunkim.climbingcalendar.domain.schedule.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;

public record ScheduleWithClimbingGym(
        Schedule schedule,
        ClimbingGym climbingGym
) {
}
