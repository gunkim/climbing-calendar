package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearCommand;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClearRepository clearRepository;

    public void createSchedule(UserId userId, ClimbingGymId climbingGymId, String title, String memo, Instant scheduleDate, List<ClearCommand> clears) {
        Schedule schedule = saveSchedule(userId, climbingGymId, title, memo, scheduleDate);
        saveClears(schedule.id(), clears);
    }

    private Schedule saveSchedule(UserId userId, ClimbingGymId climbingGymId, String title, String memo, Instant scheduleDate) {
        return scheduleRepository.save(Schedule.create(userId, climbingGymId, title, memo, scheduleDate));
    }

    private void saveClears(ScheduleId scheduleId, List<ClearCommand> clears) {
        clearRepository.saveAll(clears.stream()
                .map(clear -> clear.toClear(scheduleId))
                .toList());
    }
}
