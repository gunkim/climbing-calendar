package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.service.GetClimbingGymService;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.ScheduleWithClimbingGym;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleWithClimbingGymReader {
    private final GetScheduleService getScheduleService;
    private final GetClimbingGymService getClimbingGymService;

    public ScheduleWithClimbingGym getSchedule(Long scheduleId) {
        Schedule schedule = getScheduleService.getSchedule(scheduleId);
        ClimbingGym climbingGym = getClimbingGymService.getClimbingGym(schedule.climbingGymId());

        return new ScheduleWithClimbingGym(schedule, climbingGym);
    }

    public List<ScheduleWithClimbingGym> getSchedules(Long userId) {
        return getScheduleService.getSchedules(userId).stream()
                .map(schedule -> new ScheduleWithClimbingGym(schedule, getClimbingGymService.getClimbingGym(schedule.climbingGymId())))
                .toList();
    }
}
