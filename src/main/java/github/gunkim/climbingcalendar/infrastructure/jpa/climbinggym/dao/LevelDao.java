package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.dao;

import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelDao extends JpaRepository<LevelEntity, Long> {
}
