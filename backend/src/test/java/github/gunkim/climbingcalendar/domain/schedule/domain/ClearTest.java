package github.gunkim.climbingcalendar.domain.schedule.domain;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Clear")
class ClearTest {
    @Test
    void 스케줄_아이디가_null이라면_예외가_발생한다() {
        assertThatThrownBy(() ->
                Clear.create(null, LevelId.from(1L), 5)
        ).isInstanceOf(NullPointerException.class)
                .hasMessageContaining("ScheduleId cannot be null");
    }

    @Test
    void 레벨_아이디가_null이라면_예외가_발생한다() {
        assertThatThrownBy(() ->
                Clear.create(ScheduleId.from(1L), null, 5)
        ).isInstanceOf(NullPointerException.class)
                .hasMessageContaining("LevelId cannot be null");
    }

    @Test
    void 카운트가_MIN_COUNT_미만이면_예외가_발생한다() {
        assertThatThrownBy(() ->
                Clear.create(ScheduleId.from(1L), LevelId.from(1L), 0)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Count must be greater than or equal to 1");
    }

    @Test
    void 카운트가_MAX_COUNT_초과면_예외가_발생한다() {
        assertThatThrownBy(() ->
                Clear.create(ScheduleId.from(1L), LevelId.from(1L), 21)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Count must be less than or equal to 20");
    }
}
