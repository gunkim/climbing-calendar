package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.api.AuthenticatedUser;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.CreateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.UpdateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.response.GetScheduleResponse;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.service.ClearWithLevelReader;
import github.gunkim.climbingcalendar.domain.schedule.service.CreateScheduleService;
import github.gunkim.climbingcalendar.domain.schedule.service.DeleteScheduleService;
import github.gunkim.climbingcalendar.domain.schedule.service.ScheduleWithClimbingGymReader;
import github.gunkim.climbingcalendar.domain.schedule.service.UpdateScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static github.gunkim.climbingcalendar.api.schedule.model.requeset.UpdateScheduleRequest.ClearItem;

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
        return scheduleWithClimbingGymReader.getSchedules(authenticatedUser.userId()).stream()
                .map(scheduleWithClimbingGym -> GetScheduleResponse.from(
                        scheduleWithClimbingGym,
                        clearWithLevelReader.getClears(scheduleWithClimbingGym.schedule().id())
                )).toList();
    }

    @Override
    public void createSchedule(AuthenticatedUser authenticatedUser, CreateScheduleRequest createScheduleRequest) {
        createScheduleService.createSchedule(authenticatedUser.userId(), ClimbingGymId.from(createScheduleRequest.climbingGymId()),
                createScheduleRequest.title(), createScheduleRequest.memo(), createScheduleRequest.scheduleDate(), createScheduleRequest.clearList());
    }

    @Override
    public void updateSchedule(Long id, UpdateScheduleRequest updateScheduleRequest) {
        var clearCommands = updateScheduleRequest.clearList().stream()
                .map(ClearItem::toClearCommand)
                .toList();
        updateScheduleService.updateSchedule(ScheduleId.from(id), ClimbingGymId.from(updateScheduleRequest.climbingGymId()),
                updateScheduleRequest.title(), updateScheduleRequest.scheduleDate(), updateScheduleRequest.memo(), clearCommands);
    }

    @Override
    public void deleteSchedule(Long id) {
        deleteScheduleService.deleteSchedule(ScheduleId.from(id));
    }
}
