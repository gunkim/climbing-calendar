package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.dao;

import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LevelDao extends JpaRepository<LevelEntity, Long> {
    Optional<List<LevelEntity>> findByClimbingGymId(Long climbingGymId);
}
