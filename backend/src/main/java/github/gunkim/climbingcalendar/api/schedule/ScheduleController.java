package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.api.AuthenticatedUser;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.CreateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.GetSchedulesCountRequest;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.UpdateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.response.GetScheduleResponse;
import github.gunkim.climbingcalendar.api.schedule.model.response.GetSchedulesCountResponse;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.model.ScheduleWithClear;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static github.gunkim.climbingcalendar.api.schedule.model.requeset.UpdateScheduleRequest.ClearItem;

@RequestMapping("/api/v1/schedules")
interface ScheduleResource {
    @GetMapping
    List<GetScheduleResponse> getSchedules(@AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @PostMapping
    void createSchedule(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @RequestBody CreateScheduleRequest createScheduleRequest);

    @PutMapping("/{id}")
    void updateSchedule(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @PathVariable Long id, @RequestBody UpdateScheduleRequest updateScheduleRequest);

    @DeleteMapping("/{id}")
    void deleteSchedule(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @PathVariable Long id);

    @GetMapping("/count")
    GetSchedulesCountResponse getSchedulesCount(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @ModelAttribute GetSchedulesCountRequest request);
}

@RestController
@RequiredArgsConstructor
public class ScheduleController implements ScheduleResource {
    private final CreateScheduleService createScheduleService;
    private final UpdateScheduleService updateScheduleService;
    private final DeleteScheduleService deleteScheduleService;
    private final GetSchedulesCountService getSchedulesCountService;
    private final ScheduleWithClearReader scheduleWithClearReader;

    @Override
    public List<GetScheduleResponse> getSchedules(AuthenticatedUser authenticatedUser) {
        List<ScheduleWithClear> scheduleWithClears = scheduleWithClearReader.getScheduleWithClears(authenticatedUser.userId());
        return scheduleWithClears.stream().map(GetScheduleResponse::from).toList();
    }

    @Override
    public void createSchedule(AuthenticatedUser authenticatedUser, CreateScheduleRequest createScheduleRequest) {
        createScheduleService.createSchedule(authenticatedUser.userId(), ClimbingGymId.from(createScheduleRequest.climbingGymId()), createScheduleRequest.title(),
                createScheduleRequest.memo(), createScheduleRequest.scheduleDate(), createScheduleRequest.clearList());
    }

    @Override
    public void updateSchedule(AuthenticatedUser authenticatedUser, Long id, UpdateScheduleRequest updateScheduleRequest) {
        var clearCommands = updateScheduleRequest.clearList().stream().map(ClearItem::toClearCommand).toList();
        updateScheduleService.updateSchedule(ScheduleId.from(id), authenticatedUser.userId(), ClimbingGymId.from(updateScheduleRequest.climbingGymId()),
                updateScheduleRequest.title(), updateScheduleRequest.scheduleDate(), updateScheduleRequest.memo(), clearCommands);
    }

    @Override
    public void deleteSchedule(AuthenticatedUser authenticatedUser, Long id) {
        deleteScheduleService.deleteSchedule(ScheduleId.from(id), authenticatedUser.userId());
    }

    @Override
    public GetSchedulesCountResponse getSchedulesCount(AuthenticatedUser authenticatedUser, @ModelAttribute GetSchedulesCountRequest getSchedulesCountRequest) {
        return GetSchedulesCountResponse.from(getSchedulesCountService.getSchedulesCount(authenticatedUser.userId(), getSchedulesCountRequest.year(),
                getSchedulesCountRequest.month()));
    }
}
