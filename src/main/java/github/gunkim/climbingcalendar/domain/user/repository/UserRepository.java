package github.gunkim.climbingcalendar.domain.user.repository;

import github.gunkim.climbingcalendar.domain.user.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);
}
