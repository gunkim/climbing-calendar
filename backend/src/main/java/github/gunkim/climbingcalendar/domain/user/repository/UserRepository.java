package github.gunkim.climbingcalendar.domain.user.repository;

import github.gunkim.climbingcalendar.domain.user.model.User;

public interface UserRepository extends UserReadRepository {
    User save(User user);
}
