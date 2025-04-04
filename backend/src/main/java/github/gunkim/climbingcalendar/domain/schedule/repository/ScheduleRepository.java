package github.gunkim.climbingcalendar.domain.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;

public interface ScheduleRepository extends ScheduleReadRepository {
    Schedule save(Schedule schedule);

    Schedule update(Schedule schedule);

    void deleteById(ScheduleId scheduleId);
}
