package github.gunkim.climbingcalendar.api.climbinggym;

import github.gunkim.climbingcalendar.api.AuthenticatedUser;
import github.gunkim.climbingcalendar.api.climbinggym.request.GetClimbingGymOfVisitAndClearCountRequest;
import github.gunkim.climbingcalendar.api.climbinggym.response.GetClimbingGymOfLevelResponse;
import github.gunkim.climbingcalendar.api.climbinggym.response.GetClimbingGymOfVisitAndClearCountResponse;
import github.gunkim.climbingcalendar.api.climbinggym.response.GetClimbingGymResponse;
import github.gunkim.climbingcalendar.application.ClimbingGymQueryService;
import github.gunkim.climbingcalendar.application.model.ClimbingGymOfVisitAndClearCountCriteria;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.service.GetClimbingGymService;
import github.gunkim.climbingcalendar.domain.climbinggym.service.GetLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/climbing-gyms")
interface ClimbingGymResource {
    @GetMapping
    List<GetClimbingGymResponse> getClimbingGyms();

    @GetMapping("/{id}/levels")
    List<GetClimbingGymOfLevelResponse> getClimbingGymOfLevels(@PathVariable("id") long id);

    @GetMapping("/count")
    List<GetClimbingGymOfVisitAndClearCountResponse> getClimbingGymOfVisitAndClearCounts(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @ModelAttribute
    GetClimbingGymOfVisitAndClearCountRequest getClimbingGymOfVisitAndClearCountRequest);
}

@RestController
@RequiredArgsConstructor
public class ClimbingGymController implements ClimbingGymResource {
    private final GetClimbingGymService getClimbingGymService;
    private final GetLevelService getLevelService;
    private final ClimbingGymQueryService climbingGymQueryService;

    public List<GetClimbingGymResponse> getClimbingGyms() {
        return getClimbingGymService.getClimbingGyms().stream()
                .map(GetClimbingGymResponse::from)
                .toList();
    }

    public List<GetClimbingGymOfLevelResponse> getClimbingGymOfLevels(long id) {
        return getLevelService.getLevels(ClimbingGymId.from(id)).stream()
                .map(GetClimbingGymOfLevelResponse::from)
                .toList();
    }

    @Override
    public List<GetClimbingGymOfVisitAndClearCountResponse> getClimbingGymOfVisitAndClearCounts(AuthenticatedUser authenticatedUser,
                                                                                                GetClimbingGymOfVisitAndClearCountRequest getClimbingGymOfVisitAndClearCountRequest) {
        return climbingGymQueryService.getClimbingGymOfVisitAndClearCounts(ClimbingGymOfVisitAndClearCountCriteria.of(authenticatedUser.userId(),
                        getClimbingGymOfVisitAndClearCountRequest.limit(), getClimbingGymOfVisitAndClearCountRequest.orderBy())).stream()
                .map(GetClimbingGymOfVisitAndClearCountResponse::from)
                .toList();
    }
}
