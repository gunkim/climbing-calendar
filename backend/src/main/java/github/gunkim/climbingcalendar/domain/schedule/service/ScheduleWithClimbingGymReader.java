package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.service.GetClimbingGymService;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.ScheduleWithClimbingGym;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleWithClimbingGymReader {
    private final GetScheduleService getScheduleService;
    private final GetClimbingGymService getClimbingGymService;

    public ScheduleWithClimbingGym getSchedule(ScheduleId scheduleId) {
        Schedule schedule = getScheduleService.getScheduleById(scheduleId);
        ClimbingGym climbingGym = getClimbingGymService.getClimbingGym(schedule.climbingGymId());
        return new ScheduleWithClimbingGym(schedule, climbingGym);
    }

    public List<ScheduleWithClimbingGym> getSchedules(UserId userId) {
        return getScheduleService.getSchedulesByUserId(userId).stream()
                .map(schedule -> new ScheduleWithClimbingGym(schedule, getClimbingGymService.getClimbingGym(schedule.climbingGymId())))
                .toList();
    }
}
