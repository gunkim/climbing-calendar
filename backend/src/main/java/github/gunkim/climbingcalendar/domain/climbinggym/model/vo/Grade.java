package github.gunkim.climbingcalendar.domain.climbinggym.model.vo;

public record Grade(
        int startGrade,
        int endGrade
) {
    public static Grade from(int startGrade, int endGrade) {
        return new Grade(startGrade, endGrade);
    }
}
