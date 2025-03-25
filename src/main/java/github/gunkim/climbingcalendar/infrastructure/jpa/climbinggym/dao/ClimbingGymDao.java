package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.dao;

import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity.ClimbingGymEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClimbingGymDao extends JpaRepository<ClimbingGymEntity, Long> {
}
