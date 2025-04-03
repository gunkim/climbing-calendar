package github.gunkim.climbingcalendar.infrastructure.jpa.schedule.dao;

import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity.ClearEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClearDao extends JpaRepository<ClearEntity, Long> {

    List<ClearEntity> findByScheduleId(Long scheduleId);

    List<ClearEntity> findByScheduleIdIn(List<Long> scheduleIds);

    void deleteByScheduleId(Long scheduleId);
}
