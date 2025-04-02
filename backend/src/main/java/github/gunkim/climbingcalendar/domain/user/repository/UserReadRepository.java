package github.gunkim.climbingcalendar.domain.user.repository;

import github.gunkim.climbingcalendar.domain.user.model.User;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;

import java.util.Optional;

public interface UserReadRepository {
    Optional<User> findByEmail(String email);

    Optional<User> findById(UserId userId);
}
