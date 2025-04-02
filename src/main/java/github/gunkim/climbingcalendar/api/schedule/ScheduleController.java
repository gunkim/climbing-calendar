package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.api.schedule.model.requeset.CreateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.UpdateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.response.GetScheduleResponse;
import github.gunkim.climbingcalendar.api.CurrentUser;
import github.gunkim.climbingcalendar.domain.schedule.service.*;
import github.gunkim.climbingcalendar.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    private final CreateScheduleService createScheduleService;
    private final UpdateScheduleService updateScheduleService;
    private final DeleteScheduleService deleteScheduleService;

    private final ScheduleWithClimbingGymReader scheduleWithClimbingGymReader;
    private final ClearWithLevelReader clearWithLevelReader;

    @GetMapping
    public List<GetScheduleResponse> getSchedules(@CurrentUser User user) {
        return scheduleWithClimbingGymReader.getSchedules(user.id()).stream()
                .map(scheduleWithClimbingGym -> GetScheduleResponse.from(scheduleWithClimbingGym, clearWithLevelReader.getClears(scheduleWithClimbingGym.schedule().id())))
                .toList();
    }

    @PostMapping
    public void createSchedule(@CurrentUser User user, @RequestBody CreateScheduleRequest createScheduleRequest) {
        createScheduleService.createSchedule(user.id(), createScheduleRequest.climbingGymId(), createScheduleRequest.title(), createScheduleRequest.memo(),
                createScheduleRequest.scheduleDate(), createScheduleRequest.clearList());
    }

    @PutMapping("/{id}")
    public void updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleRequest updateScheduleRequest) {
        updateScheduleService.updateSchedule(id, updateScheduleRequest.climbingGymId(), updateScheduleRequest.title(), updateScheduleRequest.scheduleDate(),
                updateScheduleRequest.memo(), updateScheduleRequest.clearList());
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        deleteScheduleService.deleteSchedule(id);
    }
}
