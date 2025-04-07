package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.api.AuthenticatedUser;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.CreateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.GetScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.GetSchedulesCountRequest;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.UpdateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.response.GetScheduleResponse;
import github.gunkim.climbingcalendar.api.schedule.model.response.GetSchedulesCountResponse;
import github.gunkim.climbingcalendar.application.ScheduleQueryService;
import github.gunkim.climbingcalendar.application.model.ScheduleSearchCriteria;
import github.gunkim.climbingcalendar.common.Pageable;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.service.CreateScheduleService;
import github.gunkim.climbingcalendar.domain.schedule.service.DeleteScheduleService;
import github.gunkim.climbingcalendar.domain.schedule.service.GetSchedulesCountService;
import github.gunkim.climbingcalendar.domain.schedule.service.UpdateScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    List<GetScheduleResponse> getSchedules(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @ModelAttribute GetScheduleRequest request);

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
    private static final int DEFAULT_SIZE = 31;

    private final CreateScheduleService createScheduleService;
    private final UpdateScheduleService updateScheduleService;
    private final DeleteScheduleService deleteScheduleService;
    private final ScheduleQueryService scheduleQueryService;
    private final GetSchedulesCountService getSchedulesCountService;

    @Override
    public List<GetScheduleResponse> getSchedules(AuthenticatedUser authenticatedUser, GetScheduleRequest request) {
        var pageable = Pageable.of(
                request.page(),
                request.size() == null ? DEFAULT_SIZE : request.size()
        );
        var criteria = new ScheduleSearchCriteria(pageable, request.year(), request.month());
        return scheduleQueryService.getSchedules(criteria).stream()
                .map(GetScheduleResponse::from)
                .toList();
    }

    @Override
    public void createSchedule(AuthenticatedUser authenticatedUser, CreateScheduleRequest createScheduleRequest) {
        createScheduleService.createSchedule(authenticatedUser.userId(), ClimbingGymId.from(createScheduleRequest.climbingGymId()),
                createScheduleRequest.title(), createScheduleRequest.memo(), createScheduleRequest.scheduleDate(), createScheduleRequest.clearList());
    }

    @Override
    public void updateSchedule(AuthenticatedUser authenticatedUser, Long id, UpdateScheduleRequest updateScheduleRequest) {
        var clearCommands = updateScheduleRequest.clearList().stream()
                .map(ClearItem::toClearCommand)
                .toList();
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
