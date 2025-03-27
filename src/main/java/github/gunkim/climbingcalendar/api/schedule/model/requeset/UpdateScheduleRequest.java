package github.gunkim.climbingcalendar.api.schedule.model.requeset;

import github.gunkim.climbingcalendar.domain.schedule.model.ClearItem;

import java.time.Instant;
import java.util.List;

public record UpdateScheduleRequest(
        String title,
        Instant scheduleDate,
        Long climbingGymId,
        String memo,
        List<ClearItem> clearList
) {
}
