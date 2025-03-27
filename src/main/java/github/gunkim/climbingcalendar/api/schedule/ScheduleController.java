package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.api.schedule.model.requeset.CreateScheduleRequest;
import github.gunkim.climbingcalendar.api.schedule.model.requeset.UpdateScheduleRequest;
import github.gunkim.climbingcalendar.domain.schedule.service.CreateScheduleService;
import github.gunkim.climbingcalendar.domain.schedule.service.DeleteScheduleService;
import github.gunkim.climbingcalendar.domain.schedule.service.UpdateScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    private final CreateScheduleService createScheduleService;
    private final UpdateScheduleService updateScheduleService;
    private final DeleteScheduleService deleteScheduleService;

    @PostMapping
    public void createSchedule(@RequestBody CreateScheduleRequest createScheduleRequest) {
        Long userId = 1L;
        createScheduleService.createSchedule(userId, createScheduleRequest.climbingGymId(), createScheduleRequest.title(), createScheduleRequest.memo(),
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
