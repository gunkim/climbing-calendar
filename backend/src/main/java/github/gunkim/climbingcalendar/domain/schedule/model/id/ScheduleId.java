package github.gunkim.climbingcalendar.domain.schedule.model.id;

public record ScheduleId(
        Long value
) {
    public static ScheduleId from(Long value) {
        return new ScheduleId(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
