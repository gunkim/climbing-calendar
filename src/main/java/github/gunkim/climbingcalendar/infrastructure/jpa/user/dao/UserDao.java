package github.gunkim.climbingcalendar.infrastructure.jpa.user.dao;

import github.gunkim.climbingcalendar.infrastructure.jpa.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Long> {
}
