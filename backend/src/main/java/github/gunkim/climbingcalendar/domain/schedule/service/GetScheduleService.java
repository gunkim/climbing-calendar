package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.schedule.exception.ScheduleNotFoundException;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleReadRepository;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetScheduleService {
    private final ScheduleReadRepository scheduleReadRepository;

    public Schedule getScheduleById(ScheduleId id) {
        return scheduleReadRepository.findById(id)
                .orElseThrow(() -> ScheduleNotFoundException.fromId(id));
    }

    public List<Schedule> getSchedulesByUserId(UserId userId) {
        return scheduleReadRepository.findByUserId(userId);
    }
}
