package github.gunkim.climbingcalendar.application.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public class ScheduleSearchResult {
    private Long id;
    private String title;
    private Instant scheduleDate;
    private String memo;
    private Long climbingGymId;
    private String climbingGymName;
    private List<ClearDto> clearList;

    public ScheduleSearchResult(Long id, String title, Instant scheduleDate, String memo, Long climbingGymId, String climbingGymName) {
        this.id = id;
        this.title = title;
        this.scheduleDate = scheduleDate;
        this.memo = memo;
        this.climbingGymId = climbingGymId;
        this.climbingGymName = climbingGymName;
    }

    public void setClearList(List<ClearDto> clearList) {
        this.clearList = clearList;
    }

    public record ClearDto(
            Long id,
            Color color,
            int startGrade,
            int endGrade,
            int count
    ) {
    }
}
