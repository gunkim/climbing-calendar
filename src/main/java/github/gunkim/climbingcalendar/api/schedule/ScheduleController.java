package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.api.schedule.model.requeset.CreateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.UpdateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.response.GetScheduleResponse;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.service.*;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
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
    public List<GetScheduleResponse> getSchedules() {
        Long userId = 1L;
        return scheduleWithClimbingGymReader.getSchedules(UserId.from(userId)).stream()
                .map(scheduleWithClimbingGym -> GetScheduleResponse.from(scheduleWithClimbingGym, clearWithLevelReader.getClears(scheduleWithClimbingGym.schedule().id())))
                .toList();
    }

    @PostMapping
    public void createSchedule(@RequestBody CreateScheduleRequest createScheduleRequest) {
        Long userId = 1L;
        createScheduleService.createSchedule(UserId.from(userId), ClimbingGymId.from(createScheduleRequest.climbingGymId()), createScheduleRequest.title(),
                createScheduleRequest.memo(),
                createScheduleRequest.scheduleDate(), createScheduleRequest.clearList());
    }

    @PutMapping("/{id}")
    public void updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleRequest updateScheduleRequest) {
        updateScheduleService.updateSchedule(ScheduleId.from(id), ClimbingGymId.from(updateScheduleRequest.climbingGymId()), updateScheduleRequest.title(),
                updateScheduleRequest.scheduleDate(),
                updateScheduleRequest.memo(), updateScheduleRequest.clearList());
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        deleteScheduleService.deleteSchedule(ScheduleId.from(id));
    }
}
