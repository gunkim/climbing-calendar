package github.gunkim.climbingcalendar.application.model;

import github.gunkim.climbingcalendar.common.Pageable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ScheduleSearchCriteria 테스트")
class ScheduleSearchCriteriaTest {
    @Test
    void 모든_값_넣어_생성된다() {
        var pageable = Pageable.of(1, 10);
        var year = 2023;
        var month = 10;

        var criteria = new ScheduleSearchCriteria(pageable, year, month);

        assertAll(
                () -> assertEquals(pageable, criteria.pageable()),
                () -> assertEquals(year, criteria.year()),
                () -> assertEquals(month, criteria.month())
        );
    }

    @Test
    void 날짜_가_모두_null인_경우_생성된다() {
        var pageable = Pageable.of(1, 10);
        Integer year = null;
        Integer month = null;

        var criteria = new ScheduleSearchCriteria(pageable, year, month);

        assertAll(
                () -> assertEquals(pageable, criteria.pageable()),
                () -> assertEquals(year, criteria.year()),
                () -> assertEquals(month, criteria.month())
        );
    }

    @Test
    void 날짜가_제공됐을때_year가_null이면_예외가발생한다() {
        var pageable = Pageable.of(1, 10);
        Integer year = null;
        Integer month = 10;

        assertThatThrownBy(() -> new ScheduleSearchCriteria(pageable, year, month))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 날짜가_제공됐을때_month가_null이면_예외가발생한다() {
        var pageable = Pageable.of(1, 10);
        Integer year = 2023;
        Integer month = null;

        assertThatThrownBy(() -> new ScheduleSearchCriteria(pageable, year, month))
                .isInstanceOf(IllegalArgumentException.class);
    }
}