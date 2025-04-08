package github.gunkim.climbingcalendar.api.climbinggym.request;

public record GetClimbingGymOfVisitAndClearCountRequest(
        int limit,
        String orderBy
) {
}
