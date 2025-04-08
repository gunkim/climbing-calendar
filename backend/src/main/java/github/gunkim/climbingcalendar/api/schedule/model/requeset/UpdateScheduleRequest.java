package github.gunkim.climbingcalendar.api.schedule.model.requeset;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearCommand;

import java.time.Instant;
import java.util.List;

public record UpdateScheduleRequest(
        String title,
        Instant scheduleDate,
        Long climbingGymId,
        String memo,
        List<ClearItem> clearList
) {
    public record ClearItem(
            Long levelId,
            int count
    ) {
        public ClearCommand toClearCommand() {
            return new ClearCommand(LevelId.from(levelId), count);
        }
    }
}
