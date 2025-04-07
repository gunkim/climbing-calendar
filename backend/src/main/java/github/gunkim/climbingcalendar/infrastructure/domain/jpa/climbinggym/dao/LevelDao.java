package github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.dao;

import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelDao extends JpaRepository<LevelEntity, Long> {
    List<LevelEntity> findByClimbingGymId(Long climbingGymId);

    List<LevelEntity> findAllByIdIn(List<Long> levelIds);
}
