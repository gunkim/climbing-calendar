package github.gunkim.climbingcalendar.api.schedule.model.response;

import github.gunkim.climbingcalendar.api.schedule.model.ClearItem;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearWithLevel;
import github.gunkim.climbingcalendar.domain.schedule.model.ScheduleWithClear;
import github.gunkim.climbingcalendar.domain.schedule.model.ScheduleWithClimbingGym;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetScheduleResponse(
    Long id,
    String title,
    Instant scheduleDate,
    String memo,
    Long climbingGymId,
    String climbingGymName,
    List<ClearItem> clearList
) {
    public static GetScheduleResponse from(ScheduleWithClear scheduleWithClear) {
        return GetScheduleResponse.builder()
                .id(scheduleWithClear.scheduleWithClimbingGym().schedule().id().value())
                .title(scheduleWithClear.scheduleWithClimbingGym().schedule().title())
                .scheduleDate(scheduleWithClear.scheduleWithClimbingGym().schedule().scheduleDate())
                .memo(scheduleWithClear.scheduleWithClimbingGym().schedule().memo())
                .climbingGymId(scheduleWithClear.scheduleWithClimbingGym().climbingGym().id().value())
                .climbingGymName(scheduleWithClear.scheduleWithClimbingGym().climbingGym().name())
                .clearList(
                        scheduleWithClear.clearWithLevels().stream()
                                .map(ClearItem::from)
                                .toList()
                )
                .build();
    }
}
