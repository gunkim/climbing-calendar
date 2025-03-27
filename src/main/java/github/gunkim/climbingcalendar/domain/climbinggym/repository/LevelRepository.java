package github.gunkim.climbingcalendar.domain.climbinggym.repository;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;

import java.util.List;
import java.util.Optional;

public interface LevelRepository {
    Optional<List<Level>> findByClimbingGymId(Long climbingGymId);
}
