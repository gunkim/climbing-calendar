package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetScheduleService {
    private final ScheduleRepository scheduleRepository;

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow();
    }

    public List<Schedule> getSchedules(Long userId) {
        return scheduleRepository.findByUserId(userId);
    }
}
