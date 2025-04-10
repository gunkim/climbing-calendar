package github.gunkim.climbingcalendar.infrastructure.domain.jpa.user.dao;

import github.gunkim.climbingcalendar.infrastructure.domain.jpa.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
