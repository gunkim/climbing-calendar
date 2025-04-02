package github.gunkim.climbingcalendar.domain.user.model.id;

public record UserId(
        Long value
) {
    public static UserId from(Long value) {
        return new UserId(value);
    }
}
