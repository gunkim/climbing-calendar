package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetScheduleService {
    private final ScheduleRepository scheduleRepository;

    public Schedule getSchedule(ScheduleId id) {
        return scheduleRepository.findById(id).orElseThrow();
    }

    public List<Schedule> getSchedules(UserId userId) {
        return scheduleRepository.findByUserId(userId);
    }
}
