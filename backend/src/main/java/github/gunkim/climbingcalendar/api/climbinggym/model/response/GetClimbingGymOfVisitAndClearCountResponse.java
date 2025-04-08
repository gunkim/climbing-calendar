package github.gunkim.climbingcalendar.api.climbinggym.model.response;

import github.gunkim.climbingcalendar.application.model.ClimbingGymOfVisitAndClearCountResult;

import java.time.Instant;

public record GetClimbingGymOfVisitAndClearCountResponse(
        String climbingGymName,
        long visitCount,
        int clearCount,
        Instant lastVisitDate
) {
    public static GetClimbingGymOfVisitAndClearCountResponse from(ClimbingGymOfVisitAndClearCountResult result) {
        return new GetClimbingGymOfVisitAndClearCountResponse(
                result.climbingGymName(),
                result.visitCount(),
                result.clearCount(),
                result.lastVisitDate()
        );
    }
}
