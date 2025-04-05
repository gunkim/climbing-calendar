package github.gunkim.climbingcalendar.domain.climbinggym.model.vo;

public record GeoLocation(
        double latitude,
        double longitude
) {
    public static GeoLocation from(double latitude, double longitude) {
        return new GeoLocation(latitude, longitude);
    }
}
