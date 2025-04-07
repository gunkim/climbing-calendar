package github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.dao;

import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.ClimbingGymEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClimbingGymDao extends JpaRepository<ClimbingGymEntity, Long> {
}
