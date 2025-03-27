package github.gunkim.climbingcalendar.api.climbinggym.response;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetClimbingGymOfLevelResponse(
        Long id,
        int startGrade,
        int endGrade,
        String color
) {
    public static GetClimbingGymOfLevelResponse from(Level level) {
        return GetClimbingGymOfLevelResponse.builder()
                .id(level.id())
                .startGrade(level.startGrade())
                .endGrade(level.endGrade())
                .color(level.color())
                .build();
    }
}
