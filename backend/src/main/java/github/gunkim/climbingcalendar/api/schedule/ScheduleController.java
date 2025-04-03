package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.api.AuthenticatedUser;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.CreateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.UpdateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.response.GetScheduleResponse;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearWithLevel;
import github.gunkim.climbingcalendar.domain.schedule.model.ScheduleWithClimbingGym;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/schedules")
interface ScheduleResource {
    @GetMapping
    List<GetScheduleResponse> getSchedules(@AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @PostMapping
    void createSchedule(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @RequestBody CreateScheduleRequest createScheduleRequest);

    @PutMapping("/{id}")
    void updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleRequest updateScheduleRequest);

    @DeleteMapping("/{id}")
    void deleteSchedule(@PathVariable Long id);
}

@RestController
@RequiredArgsConstructor
public class ScheduleController implements ScheduleResource {
    private final CreateScheduleService createScheduleService;
    private final UpdateScheduleService updateScheduleService;
    private final DeleteScheduleService deleteScheduleService;
    private final ScheduleWithClimbingGymReader scheduleWithClimbingGymReader;
    private final ClearWithLevelReader clearWithLevelReader;

    @Override
    public List<GetScheduleResponse> getSchedules(AuthenticatedUser authenticatedUser) {
        List<ScheduleWithClimbingGym> scheduleWithClimbingGyms = scheduleWithClimbingGymReader.getSchedules(authenticatedUser.userId());
        List<ClearWithLevel> clearWithLevels = clearWithLevelReader.getClears(scheduleWithClimbingGyms.stream()
                .map(scheduleWithClimbingGym -> scheduleWithClimbingGym.schedule().id())
                .toList());

        return scheduleWithClimbingGyms.stream()
                .map(scheduleWithClimbingGym -> GetScheduleResponse.from(
                        scheduleWithClimbingGym,
                        clearWithLevels.stream()
                                .filter(clearWithLevel -> clearWithLevel.clear().scheduleId().equals(scheduleWithClimbingGym.schedule().id()))
                        .toList()
                )).toList();
    }

    @Override
    public void createSchedule(AuthenticatedUser authenticatedUser, CreateScheduleRequest createScheduleRequest) {
        createScheduleService.createSchedule(authenticatedUser.userId(), ClimbingGymId.from(createScheduleRequest.climbingGymId()), createScheduleRequest.title(), createScheduleRequest.memo(), createScheduleRequest.scheduleDate(), createScheduleRequest.clearList());
    }

    @Override
    public void updateSchedule(Long id, UpdateScheduleRequest updateScheduleRequest) {
        updateScheduleService.updateSchedule(ScheduleId.from(id), ClimbingGymId.from(updateScheduleRequest.climbingGymId()), updateScheduleRequest.title(), updateScheduleRequest.scheduleDate(), updateScheduleRequest.memo(), updateScheduleRequest.clearList());
    }

    @Override
    public void deleteSchedule(Long id) {
        deleteScheduleService.deleteSchedule(ScheduleId.from(id));
    }
}
