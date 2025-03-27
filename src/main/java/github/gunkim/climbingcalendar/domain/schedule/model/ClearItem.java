package github.gunkim.climbingcalendar.domain.schedule.model;

public record ClearItem(
        Long levelId,
        int count
) {
    public Clear toClear(Long scheduleId) {
        return Clear.create(scheduleId, levelId, count);
    }
}