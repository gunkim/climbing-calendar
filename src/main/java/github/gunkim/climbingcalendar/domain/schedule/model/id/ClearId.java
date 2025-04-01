package github.gunkim.climbingcalendar.domain.schedule.model.id;

public record ClearId(
        Long value
) {
    public static ClearId from(Long value) {
        return new ClearId(value);
    }
}
