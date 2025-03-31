package github.gunkim.climbingcalendar.domain.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);

    void deleteById(Long scheduleId);

    Optional<Schedule> findById(Long id);

    List<Schedule> findByUserId(Long userId);
}
