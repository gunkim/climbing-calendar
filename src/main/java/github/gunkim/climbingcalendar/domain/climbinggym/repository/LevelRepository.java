package github.gunkim.climbingcalendar.domain.climbinggym.repository;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;

import java.util.List;

public interface LevelRepository {
    List<Level> findByClimbingGymId(Long climbingGymId);
}
