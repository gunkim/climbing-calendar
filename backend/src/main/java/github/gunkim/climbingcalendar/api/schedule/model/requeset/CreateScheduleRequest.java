package github.gunkim.climbingcalendar.api.schedule.model.requeset;

import github.gunkim.climbingcalendar.domain.schedule.model.ClearCommand;

import java.time.Instant;
import java.util.List;

public record CreateScheduleRequest(
        String title,
        Instant scheduleDate,
        Long climbingGymId,
        String memo,
        List<ClearCommand> clearList
) {
}


