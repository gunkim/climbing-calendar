package github.gunkim.climbingcalendar.infrastructure.jpa.schedule.dao;

import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity.ClearEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClearDao extends JpaRepository<ClearEntity, Long> {
    void deleteByScheduleId(Long scheduleId);
}
