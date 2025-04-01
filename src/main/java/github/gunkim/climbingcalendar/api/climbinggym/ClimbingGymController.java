package github.gunkim.climbingcalendar.api.climbinggym;

import github.gunkim.climbingcalendar.api.climbinggym.response.GetClimbingGymOfLevelResponse;
import github.gunkim.climbingcalendar.api.climbinggym.response.GetClimbingGymResponse;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.service.GetClimbingGymService;
import github.gunkim.climbingcalendar.domain.climbinggym.service.GetLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/climbing-gyms")
public class ClimbingGymController {
    private final GetClimbingGymService getClimbingGymService;
    private final GetLevelService getLevelService;

    @GetMapping
    public List<GetClimbingGymResponse> getClimbingGyms() {
        return getClimbingGymService.getClimbingGyms().stream()
                .map(GetClimbingGymResponse::from)
                .toList();
    }

    @GetMapping("/{id}/levels")
    public List<GetClimbingGymOfLevelResponse> getClimbingGymOfLevels(@PathVariable Long id) {
        return getLevelService.getLevels(ClimbingGymId.from(id)).stream()
                .map(GetClimbingGymOfLevelResponse::from)
                .toList();
    }
}
