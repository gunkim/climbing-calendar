package github.gunkim.climbingcalendar.application;

import github.gunkim.climbingcalendar.application.model.ClimbingGymOfVisitAndClearCountCriteria;
import github.gunkim.climbingcalendar.application.model.ClimbingGymOfVisitAndClearCountResult;

import java.util.List;

public interface ClimbingGymQueryService {
    List<ClimbingGymOfVisitAndClearCountResult> getClimbingGymOfVisitAndClearCounts(ClimbingGymOfVisitAndClearCountCriteria criteria);

}
