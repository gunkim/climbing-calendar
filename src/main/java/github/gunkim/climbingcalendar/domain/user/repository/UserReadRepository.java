package github.gunkim.climbingcalendar.domain.user.repository;

import github.gunkim.climbingcalendar.domain.user.model.User;

import java.util.Optional;

public interface UserReadRepository {
    Optional<User> findByEmail(String email);
}
