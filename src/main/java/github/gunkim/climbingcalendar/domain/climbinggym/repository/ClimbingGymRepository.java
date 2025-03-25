package github.gunkim.climbingcalendar.domain.climbinggym.repository;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;

import java.util.List;

public interface ClimbingGymRepository {
    List<ClimbingGym> findAll();
}
