package github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.dao;

import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.ClearEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClearDao extends JpaRepository<ClearEntity, Long> {
    List<ClearEntity> findByScheduleId(Long scheduleId);

    List<ClearEntity> findAllByScheduleIdIn(List<Long> scheduleIds);

    void deleteByScheduleId(Long scheduleId);
}
