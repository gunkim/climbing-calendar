package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.schedule.model.ClearWithLevel;
import github.gunkim.climbingcalendar.domain.schedule.model.ScheduleWithClear;
import github.gunkim.climbingcalendar.domain.schedule.model.ScheduleWithClimbingGym;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleWithClearReader {
    private final ScheduleWithClimbingGymReader scheduleWithClimbingGymReader;
    private final ClearWithLevelReader clearWithLevelReader;

    public List<ScheduleWithClear> getSchedulWithClears(UserId userId) {
        List<ScheduleWithClimbingGym> scheduleWithClimbingGyms = scheduleWithClimbingGymReader.getSchedules(userId);
        Map<ScheduleId, List<ClearWithLevel>> clearWithLevelsMap = clearWithLevelReader.getClears(
                        scheduleWithClimbingGyms.stream()
                                .map(scheduleWithClimbingGym -> scheduleWithClimbingGym.schedule().id())
                                .toList())
                .stream()
                .collect(Collectors.groupingBy(clearWithLevel -> clearWithLevel.clear().scheduleId()));

        return scheduleWithClimbingGyms.stream()
                .map(scheduleWithClimbingGym -> new ScheduleWithClear(
                        scheduleWithClimbingGym,
                        clearWithLevelsMap.get(scheduleWithClimbingGym.schedule().id())
                )).toList();
    }
}
