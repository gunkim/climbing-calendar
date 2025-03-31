package github.gunkim.climbingcalendar.domain.climbinggym.repository;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;

import java.util.List;
import java.util.Optional;

public interface ClimbingGymRepository {
    List<ClimbingGym> findAll();

    Optional<ClimbingGym> findById(Long id);
}
