package github.gunkim.climbingcalendar.domain.schedule.model;

import java.util.List;

public record ScheduleWithClear(
        ScheduleWithClimbingGym scheduleWithClimbingGym,
        List<ClearWithLevel> clearWithLevels
) {
}
