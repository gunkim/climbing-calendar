package github.gunkim.climbingcalendar.application.model;

import java.time.Instant;

public record ClimbingGymOfVisitAndClearCountResult(
        String climbingGymName,
        long visitCount,
        int clearCount,
        Instant lastVisitDate
) {
}
