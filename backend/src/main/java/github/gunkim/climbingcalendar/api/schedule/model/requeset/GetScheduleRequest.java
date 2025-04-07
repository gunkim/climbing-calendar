package github.gunkim.climbingcalendar.api.schedule.model.requeset;

public record GetScheduleRequest(
        Integer size,
        Integer page,
        Integer year,
        Integer month
) {
}
