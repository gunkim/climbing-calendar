package github.gunkim.climbingcalendar.infrastructure.jpa.schedule.dao;

import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDao extends JpaRepository<ScheduleEntity, Long> {
}
