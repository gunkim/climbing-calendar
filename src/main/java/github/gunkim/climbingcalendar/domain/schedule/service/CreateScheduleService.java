package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.UnsavedClear;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClearRepository clearRepository;

    public void createSchedule(Long userId, Long climbingGymId, String title, String memo, Instant scheduleDate, List<UnsavedClear> clears) {
        Schedule schedule = saveSchedule(userId, climbingGymId, title, memo, scheduleDate);
        saveClears(schedule.id(), clears);
    }

    private Schedule saveSchedule(Long userId, Long climbingGymId, String title, String memo, Instant scheduleDate) {
        return scheduleRepository.save(Schedule.create(userId, climbingGymId, title, memo, scheduleDate));
    }

    private void saveClears(long scheduleId, List<UnsavedClear> clears) {
        clearRepository.saveAll(clears.stream()
                .map(clear -> clear.toClear(scheduleId))
                .toList());
    }
}
