package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.climbinggym.service.GetLevelService;
import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearWithLevel;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class ClearWithLevelReader {
    private final GetClearService getClearService;
    private final GetLevelService getLevelService;

    public List<ClearWithLevel> getClears(ScheduleId scheduleId) {
        List<Clear> clears = getClearService.getClears(scheduleId);
        return getClearWithLevels(clears);
    }

    public List<ClearWithLevel> getClears(List<ScheduleId> scheduleIds) {
        List<Clear> clears = getClearService.getClears(scheduleIds);
        return getClearWithLevels(clears);
    }

    private List<ClearWithLevel> getClearWithLevels(List<Clear> clears) {
        Map<LevelId, Level> levelMap = getLevels(clears).stream()
                .collect(toMap(Level::id, level -> level));
        return clears.stream()
                .map(clear -> new ClearWithLevel(clear, levelMap.get(clear.levelId())))
                .toList();
    }

    private List<Level> getLevels(List<Clear> clears) {
        return getLevelService.getLevels(clears.stream()
                .map(Clear::levelId)
                .toList());
    }
}
