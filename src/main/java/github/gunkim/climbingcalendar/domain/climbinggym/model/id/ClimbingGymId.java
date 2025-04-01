package github.gunkim.climbingcalendar.domain.climbinggym.model.id;

public record ClimbingGymId(
        Long value
) {
    public static ClimbingGymId from(Long value) {
        return new ClimbingGymId(value);
    }
}
