package github.gunkim.climbingcalendar.api.schedule.model.requeset;

import github.gunkim.climbingcalendar.api.schedule.model.ClearVO;

import java.time.Instant;
import java.util.List;

public record CreateScheduleRequest(
        String title,
        Instant scheduleDate,
        Long climbingGymId,
        String memo,
        List<ClearVO> clearList
) {
}
