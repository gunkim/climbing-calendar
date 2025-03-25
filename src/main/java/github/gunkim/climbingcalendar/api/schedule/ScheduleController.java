package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.api.schedule.model.requeset.CreateScheduleRequest;
import github.gunkim.climbingcalendar.domain.schedule.service.CreateScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    private final CreateScheduleService createScheduleService;

    @PostMapping
    public void createSchedule(@RequestBody CreateScheduleRequest createScheduleRequest) {
        Long userId = 1L;
        createScheduleService.createSchedule(userId, createScheduleRequest.climbingGymId(), createScheduleRequest.title(), createScheduleRequest.memo(),
                createScheduleRequest.scheduleDate(), createScheduleRequest.clearList());
    }
}
