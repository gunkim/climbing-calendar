package github.gunkim.climbingcalendar.api.schedule.model;

import github.gunkim.climbingcalendar.domain.schedule.model.ClearWithLevel;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ClearItem(
        Long id,
        String color,
        int startGrade,
        int endGrade,
        int count
) {
    public static ClearItem from(ClearWithLevel clearWithLevel) {
        return ClearItem.builder()
                .id(clearWithLevel.clear().levelId().value())
                .color(clearWithLevel.level().color().toString())
                .startGrade(clearWithLevel.level().grade().startGrade())
                .endGrade(clearWithLevel.level().grade().endGrade())
                .count(clearWithLevel.clear().count())
                .build();
    }
}
