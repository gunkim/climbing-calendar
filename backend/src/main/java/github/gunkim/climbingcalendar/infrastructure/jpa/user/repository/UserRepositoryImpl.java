package github.gunkim.climbingcalendar.infrastructure.jpa.user.repository;

import github.gunkim.climbingcalendar.domain.user.model.User;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import github.gunkim.climbingcalendar.domain.user.repository.UserRepository;
import github.gunkim.climbingcalendar.infrastructure.jpa.user.dao.UserDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;

    @Override
    public User save(User user) {
        return userDao.save(UserEntity.from(user)).toDomain();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email)
                .map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return userDao.findById(userId.value())
                .map(UserEntity::toDomain);
    }
}
