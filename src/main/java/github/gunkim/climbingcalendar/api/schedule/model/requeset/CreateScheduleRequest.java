package github.gunkim.climbingcalendar.api.schedule.model.requeset;

import java.time.Instant;
import java.util.List;

public record CreateScheduleRequest(
        String title,
        Instant scheduleDate,
        Long climbingGymId,
        String memo,
        List<ClearItem> clearList
) {
    public record ClearItem(
        Long LevelId,
        int count
    ) {
    }
}


