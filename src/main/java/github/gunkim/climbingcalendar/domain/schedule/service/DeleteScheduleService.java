package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClearRepository clearRepository;

    public void deleteSchedule(ScheduleId scheduleId) {
        scheduleRepository.deleteById(scheduleId);
        clearRepository.deleteByScheduleId(scheduleId);
    }
}
