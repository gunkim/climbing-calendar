package github.gunkim.climbingcalendar.domain.user.service;

import github.gunkim.climbingcalendar.domain.user.model.User;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import github.gunkim.climbingcalendar.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserService {
    private final UserRepository userRepository;

    public User getUser(UserId userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: %s".formatted(userId)));
    }
}
