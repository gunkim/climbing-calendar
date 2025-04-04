package github.gunkim.climbingcalendar.domain.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;

import java.util.List;
import java.util.Optional;

public interface ScheduleReadRepository {
    Optional<Schedule> findById(ScheduleId id);

    List<Schedule> findByUserId(UserId userId);
}
