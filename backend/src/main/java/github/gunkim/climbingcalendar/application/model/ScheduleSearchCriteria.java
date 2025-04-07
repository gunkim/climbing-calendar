package github.gunkim.climbingcalendar.application.model;

import github.gunkim.climbingcalendar.common.Pageable;

public record ScheduleSearchCriteria(
        Pageable pageable,
        Integer year,
        Integer month
) {
    public ScheduleSearchCriteria {
        if ((year == null && month != null) || (year != null && month == null)) {
            throw new IllegalArgumentException("날짜 조건 필터링을 위해 연도와 월을 모두 제공해야 합니다.");
        }

    }
}
