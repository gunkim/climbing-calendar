package github.gunkim.climbingcalendar.api.schedule.model.response;

import github.gunkim.climbingcalendar.api.schedule.model.ClearItem;
import github.gunkim.climbingcalendar.application.model.ScheduleSearchResult;
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
    public static GetScheduleResponse from(ScheduleSearchResult scheduleSearchResult) {
        return GetScheduleResponse.builder()
                .id(scheduleSearchResult.id())
                .title(scheduleSearchResult.title())
                .scheduleDate(scheduleSearchResult.scheduleDate())
                .memo(scheduleSearchResult.memo())
                .climbingGymId(scheduleSearchResult.climbingGymId())
                .climbingGymName(scheduleSearchResult.climbingGymName())
                .clearList(scheduleSearchResult.clearList().stream()
                        .map(ClearItem::from)
                        .toList())
                .build();
    }
}
