package github.gunkim.climbingcalendar.api.schedule.model.requeset;

import github.gunkim.climbingcalendar.domain.schedule.model.ClearCommand;

import java.time.Instant;
import java.util.List;

public record CreateScheduleRequest(
        String title,
        Instant scheduleDate,
        Long climbingGymId,
        String memo,
        List<ClearItem> clearList
) {
    public List<ClearCommand> toClearCommands() {
        return clearList.stream()
                .map(ClearItem::toClearCommand)
                .toList();
    }

    record ClearItem(
            long levelId,
            int count
    ) {
        public ClearCommand toClearCommand() {
            return new ClearCommand(
                    github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId.from(levelId),
                    count
            );
        }
    }
}


