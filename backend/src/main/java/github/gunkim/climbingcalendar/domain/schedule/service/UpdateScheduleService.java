package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.exception.UnauthorizedScheduleException;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearCommand;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateScheduleService {
    private final GetScheduleService getScheduleService;
    private final UpdateClearService updateClearService;
    private final ScheduleRepository scheduleRepository;

    public Schedule updateSchedule(ScheduleId scheduleId,
                                   UserId userId,
                                   ClimbingGymId climbingGymId,
                                   String title,
                                   Instant scheduleDate,
                                   String memo,
                                   List<ClearCommand> clearCommands) {
        Schedule updatedSchedule = updateSchedule(scheduleId, userId, climbingGymId, title, scheduleDate, memo);
        scheduleRepository.update(updatedSchedule);

        updateClearService.updateClears(scheduleId, clearCommands);
        return updatedSchedule;
    }

    private Schedule updateSchedule(ScheduleId scheduleId,
                                    UserId userId,
                                    ClimbingGymId climbingGymId,
                                    String title,
                                    Instant scheduleDate,
                                    String memo) {
        Schedule schedule = getScheduleService.getScheduleById(scheduleId);
        schedule.validateUserAuthorization(userId);
        schedule.update(climbingGymId, title, memo, scheduleDate);

        return schedule;
    }
}