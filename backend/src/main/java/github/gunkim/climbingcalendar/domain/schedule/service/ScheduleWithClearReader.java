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

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class ScheduleWithClearReader {
    private final ScheduleWithClimbingGymReader scheduleWithClimbingGymReader;
    private final ClearWithLevelReader clearWithLevelReader;

    public List<ScheduleWithClear> getScheduleWithClears(UserId userId) {
        List<ScheduleWithClimbingGym> schedules = getSchedulesForUser(userId);
        Map<ScheduleId, List<ClearWithLevel>> clearsMap = getClearsMappedBySchedule(schedules);

        return mergeSchedulesWithClears(schedules, clearsMap);
    }

    private List<ScheduleWithClimbingGym> getSchedulesForUser(UserId userId) {
        return scheduleWithClimbingGymReader.getSchedules(userId);
    }

    private Map<ScheduleId, List<ClearWithLevel>> getClearsMappedBySchedule(
            List<ScheduleWithClimbingGym> schedules) {
        List<ScheduleId> scheduleIds = schedules.stream()
                .map(schedule -> schedule.schedule().id())
                .toList();

        return clearWithLevelReader.getClears(scheduleIds).stream()
                .collect(groupingBy(clear -> clear.clear().scheduleId()));
    }

    private List<ScheduleWithClear> mergeSchedulesWithClears(
            List<ScheduleWithClimbingGym> schedules,
            Map<ScheduleId, List<ClearWithLevel>> clearsMap) {

        return schedules.stream()
                .map(schedule -> new ScheduleWithClear(
                        schedule,
                        clearsMap.getOrDefault(schedule.schedule().id(), List.of())
                ))
                .toList();
    }
}

