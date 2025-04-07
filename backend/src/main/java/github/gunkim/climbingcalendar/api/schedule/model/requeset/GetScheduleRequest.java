package github.gunkim.climbingcalendar.api.schedule.model.requeset;

import github.gunkim.climbingcalendar.application.model.ScheduleSearchCriteria;
import github.gunkim.climbingcalendar.common.Pageable;

import java.time.Month;

public record GetScheduleRequest(
        Integer size,
        Integer page,
        Month month
) {
    public ScheduleSearchCriteria toCriteria() {
        return new ScheduleSearchCriteria(
                Pageable.of(page, size),
                month
        );
    }
}
