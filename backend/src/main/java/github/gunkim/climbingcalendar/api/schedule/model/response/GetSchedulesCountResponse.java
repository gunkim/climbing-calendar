package github.gunkim.climbingcalendar.api.schedule.model.response;

public record GetSchedulesCountResponse(
        int count
) {
    public static GetSchedulesCountResponse from(int count) {
        return new GetSchedulesCountResponse(count);
    }
}
