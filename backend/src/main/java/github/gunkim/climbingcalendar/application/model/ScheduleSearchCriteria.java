package github.gunkim.climbingcalendar.application.model;

import github.gunkim.climbingcalendar.common.Pageable;

public record ScheduleSearchCriteria(
        Pageable pageable,
        Integer year,
        Integer month
) {
}
