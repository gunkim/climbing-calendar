package github.gunkim.climbingcalendar.domain.schedule.model;

public record UnsavedClear(
        Long levelId,
        int count
) {
    public Clear toClear(Long scheduleId) {
        return Clear.create(scheduleId, levelId, count);
    }
}
