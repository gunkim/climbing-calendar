package github.gunkim.climbingcalendar.api.schedule.model.response;

import github.gunkim.climbingcalendar.api.schedule.model.ClearItem;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearWithLevel;
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
    public static GetScheduleResponse from(ScheduleWithClimbingGym scheduleWithClimbingGym, List<ClearWithLevel> clears) {
        return GetScheduleResponse.builder()
                .id(scheduleWithClimbingGym.schedule().id())
                .title(scheduleWithClimbingGym.schedule().title())
                .scheduleDate(scheduleWithClimbingGym.schedule().scheduleDate())
                .memo(scheduleWithClimbingGym.schedule().memo())
                .climbingGymId(scheduleWithClimbingGym.climbingGym().id())
                .climbingGymName(scheduleWithClimbingGym.climbingGym().name())
                .clearList(
                        clears.stream()
                                .map(ClearItem::from)
                                .toList()
                )
                .build();
    }
}
