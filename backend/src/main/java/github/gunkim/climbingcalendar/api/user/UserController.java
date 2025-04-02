package github.gunkim.climbingcalendar.api.user;

import github.gunkim.climbingcalendar.api.AuthenticatedUser;
import github.gunkim.climbingcalendar.api.user.model.response.UserResponse;
import github.gunkim.climbingcalendar.domain.user.service.GetUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/users")
interface UserResource {
    @GetMapping("/me")
    UserResponse getCurrentUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser);
}

@RestController
@RequiredArgsConstructor
public class UserController implements UserResource {
    private final GetUserService getUserService;

    @Override
    public UserResponse getCurrentUser(AuthenticatedUser authenticatedUser) {
        var user = getUserService.getUser(authenticatedUser.userId());

        return UserResponse.from(user);
    }
}
