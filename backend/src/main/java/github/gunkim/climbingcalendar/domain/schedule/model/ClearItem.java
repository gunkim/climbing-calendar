package github.gunkim.climbingcalendar.domain.schedule.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;

public record ClearItem(
        Long levelId,
        int count
) {
    public Clear toClear(ScheduleId scheduleId) {
        return Clear.create(scheduleId, LevelId.from(levelId), count);
    }
}