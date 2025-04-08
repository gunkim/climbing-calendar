package github.gunkim.climbingcalendar.application.model;

import github.gunkim.climbingcalendar.domain.user.model.id.UserId;

public record ClimbingGymOfVisitAndClearCountCriteria(
        UserId userId,
        int limit,
        String orderBy
) {
    public static ClimbingGymOfVisitAndClearCountCriteria of(UserId userId, int limit, String orderBy) {
        return new ClimbingGymOfVisitAndClearCountCriteria(userId, limit, orderBy);
    }
}
