package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.schedule.exception.UnauthorizedScheduleException;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClearRepository clearRepository;
    private final GetScheduleService getScheduleService;

    public void deleteSchedule(ScheduleId scheduleId, UserId userId) {
        Schedule schedule = getScheduleService.getScheduleById(scheduleId);
        if (!schedule.userId().equals(userId)) {
            throw new UnauthorizedScheduleException("User is not authorized to delete this schedule.");
        }
        scheduleRepository.deleteById(scheduleId);
        clearRepository.deleteByScheduleId(scheduleId);
    }
}
