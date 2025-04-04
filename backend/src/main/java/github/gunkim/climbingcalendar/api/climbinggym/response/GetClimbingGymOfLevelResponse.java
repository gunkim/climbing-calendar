package github.gunkim.climbingcalendar.api.climbinggym.response;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetClimbingGymOfLevelResponse(
        Long id,
        int startGrade,
        int endGrade,
        Color color
) {
    public static GetClimbingGymOfLevelResponse from(Level level) {
        return GetClimbingGymOfLevelResponse.builder()
                .id(level.id().value())
                .startGrade(level.grade().startGrade())
                .endGrade(level.grade().endGrade())
                .color(level.color())
                .build();
    }
}
