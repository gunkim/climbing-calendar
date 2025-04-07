package github.gunkim.climbingcalendar.application;

import github.gunkim.climbingcalendar.application.model.ScheduleSearchResult;
import github.gunkim.climbingcalendar.application.model.ScheduleSearchCriteria;

import java.util.List;

public interface ScheduleQueryService {
    List<ScheduleSearchResult> getSchedules(ScheduleSearchCriteria params);
}
