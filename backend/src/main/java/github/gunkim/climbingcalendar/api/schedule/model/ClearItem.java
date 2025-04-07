package github.gunkim.climbingcalendar.api.schedule.model;

import github.gunkim.climbingcalendar.application.model.ScheduleSearchResult;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ClearItem(
        Long id,
        Color color,
        int startGrade,
        int endGrade,
        int count
) {
    public static ClearItem from(ScheduleSearchResult.ClearDto clearDto) {
        return ClearItem.builder()
                .id(clearDto.id())
                .color(clearDto.color())
                .startGrade(clearDto.startGrade())
                .endGrade(clearDto.endGrade())
                .count(clearDto.count())
                .build();
    }
}
