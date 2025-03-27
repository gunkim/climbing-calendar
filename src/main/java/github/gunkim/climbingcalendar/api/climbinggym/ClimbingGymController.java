package github.gunkim.climbingcalendar.api.climbinggym;

import github.gunkim.climbingcalendar.api.climbinggym.response.GetClimbingGymResponse;
import github.gunkim.climbingcalendar.domain.climbinggym.service.GetClimbingGymService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/climbing-gyms")
public class ClimbingGymController {
    private final GetClimbingGymService getClimbingGymService;

    @GetMapping
    public List<GetClimbingGymResponse> getClimbingGyms() {
        return getClimbingGymService.getClimbingGyms().stream()
                .map(GetClimbingGymResponse::from)
                .toList();
    }
}
