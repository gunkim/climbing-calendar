package github.gunkim.climbingcalendar.api.climbinggym;

import github.gunkim.climbingcalendar.api.climbinggym.response.GetClimbingGymOfLevelResponse;
import github.gunkim.climbingcalendar.api.climbinggym.response.GetClimbingGymResponse;
import github.gunkim.climbingcalendar.domain.climbinggym.service.GetClimbingGymService;
import github.gunkim.climbingcalendar.domain.climbinggym.service.GetLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/climbing-gyms")
interface ClimbingGymResource {
    @GetMapping
    List<GetClimbingGymResponse> getClimbingGyms();

    @GetMapping("/{id}/levels")
    List<GetClimbingGymOfLevelResponse> getClimbingGymOfLevels(@PathVariable long id);
}

@RestController
@RequiredArgsConstructor
public class ClimbingGymController implements ClimbingGymResource {
    private final GetClimbingGymService getClimbingGymService;
    private final GetLevelService getLevelService;

    public List<GetClimbingGymResponse> getClimbingGyms() {
        return getClimbingGymService.getClimbingGyms().stream()
                .map(GetClimbingGymResponse::from)
                .toList();
    }

    public List<GetClimbingGymOfLevelResponse> getClimbingGymOfLevels(long id) {
        return getLevelService.getLevels(id).stream()
                .map(GetClimbingGymOfLevelResponse::from)
                .toList();
    }
}
