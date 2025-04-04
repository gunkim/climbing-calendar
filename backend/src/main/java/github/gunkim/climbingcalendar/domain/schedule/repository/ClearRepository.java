package github.gunkim.climbingcalendar.domain.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ClearId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;

import java.util.List;
import java.util.Optional;

public interface ClearRepository {
    Clear save(Clear clear);

    List<Clear> saveAll(List<Clear> list);

    void deleteByScheduleId(ScheduleId scheduleId);

    Optional<Clear> findById(ClearId id);

    List<Clear> findAllByScheduleId(ScheduleId scheduleId);
}
