package github.gunkim.climbingcalendar.api.climbinggym.model.request;

public record GetClimbingGymOfVisitAndClearCountRequest(
        int limit,
        String orderBy
) {
}
