package github.gunkim.climbingcalendar.application.model;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class ClimbingGymOfVisitAndClearCountResult {
    private final String climbingGymName;
    private final long visitCount;
    private final int clearCount;
    private final Instant lastVisitDate;

    public ClimbingGymOfVisitAndClearCountResult(String climbingGymName, long visitCount, int clearCount, Instant lastVisitDate) {
        this.climbingGymName = climbingGymName;
        this.visitCount = visitCount;
        this.clearCount = clearCount;
        this.lastVisitDate = lastVisitDate;
    }
}
