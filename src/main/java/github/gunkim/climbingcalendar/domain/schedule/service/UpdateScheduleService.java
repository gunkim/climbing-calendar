package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearItem;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClearRepository clearRepository;
    private final GetScheduleService getScheduleService;

    public void updateSchedule(ScheduleId id, ClimbingGymId climbingGymId, String title, Instant scheduleDate, String memo, List<ClearItem> clears) {
        Schedule schedule = saveSchedule(id, climbingGymId, title, scheduleDate, memo);
        saveClears(id, clears);
    }

    private Schedule saveSchedule(ScheduleId id, ClimbingGymId climbingGymId, String title, Instant scheduleDate, String memo) {
        Schedule schedule = scheduleRepository.save(getScheduleService.getSchedule(id));
        schedule.update(climbingGymId, title, memo, scheduleDate);
        return schedule;

    }

    private void saveClears(ScheduleId scheduleId, List<ClearItem> clears) {
        clearRepository.deleteByScheduleId(scheduleId);
        clearRepository.saveAll(clears.stream()
                .map(clear -> clear.toClear(scheduleId))
                .toList());
    }
}
