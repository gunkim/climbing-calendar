package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.climbinggym.service.GetLevelService;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearWithLevel;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClearWithLevelReader {
    private final GetClearService getClearService;
    private final GetLevelService getLevelService;

    public List<ClearWithLevel> getClears(ScheduleId scheduleId) {
        return getClearService.getClears(scheduleId).stream()
                .map(clear -> new ClearWithLevel(clear, getLevelService.getLevel(clear.levelId())))
                .toList();
    }
}
