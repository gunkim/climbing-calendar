package github.gunkim.climbingcalendar.domain.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);
}
