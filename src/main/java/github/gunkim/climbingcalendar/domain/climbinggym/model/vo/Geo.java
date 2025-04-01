package github.gunkim.climbingcalendar.domain.climbinggym.model.vo;

public record Geo(
        double latitude,
        double longitude
) {
    public static Geo from(double latitude, double longitude) {
        return new Geo(latitude, longitude);
    }
}
