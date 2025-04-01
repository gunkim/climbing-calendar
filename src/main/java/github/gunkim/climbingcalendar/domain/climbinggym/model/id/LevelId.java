package github.gunkim.climbingcalendar.domain.climbinggym.model.id;

public record LevelId(
        Long value
) {
    public static LevelId from(Long value) {
        return new LevelId(value);
    }
}
