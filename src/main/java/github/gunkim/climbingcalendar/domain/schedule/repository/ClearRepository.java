package github.gunkim.climbingcalendar.domain.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Clear;

import java.util.List;
import java.util.Optional;

public interface ClearRepository {
    Clear save(Clear clear);

    List<Clear> saveAll(List<Clear> list);

    void deleteByScheduleId(Long scheduleId);

    Optional<Clear> findById(Long id);

    List<Clear> findByScheduleId(Long scheduleId);
}
