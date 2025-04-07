package github.gunkim.climbingcalendar.application.model;

import github.gunkim.climbingcalendar.common.Pageable;

import java.time.Month;

public record ScheduleSearchCriteria(
        Pageable pageable,
        Month month
) {
}
