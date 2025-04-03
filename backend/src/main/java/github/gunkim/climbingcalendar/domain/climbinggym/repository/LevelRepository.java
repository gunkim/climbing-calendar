package github.gunkim.climbingcalendar.domain.climbinggym.repository;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;

import java.util.List;
import java.util.Optional;

public interface LevelRepository {
    List<Level> findByClimbingGymId(ClimbingGymId climbingGymId);

    List<Level> findByIdsIn(List<LevelId> levelIds);

    Optional<Level> findById(LevelId id);

    List<Level> findAll();
}
