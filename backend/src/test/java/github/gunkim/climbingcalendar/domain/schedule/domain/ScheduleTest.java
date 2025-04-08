package github.gunkim.climbingcalendar.domain.schedule.domain;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.exception.UnauthorizedScheduleException;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Schedule")
class ScheduleTest {
    @Test
    void 유저_아이디가_null이라면_예외가_발생한다() {
        assertThatThrownBy(() -> {
            Schedule.create(
                    null,
                    ClimbingGymId.from(1L),
                    "title",
                    "description",
                    Instant.now()
            );
        }).isInstanceOf(NullPointerException.class)
                .hasMessageContaining("UserId cannot be null");
    }

    @Test
    void 클라이밍짐_아이디가_null이라면_예외가_발생한다() {
        assertThatThrownBy(() -> {
            Schedule.create(
                    UserId.from(1L),
                    null,
                    "title",
                    "description",
                    Instant.now()
            );
        }).isInstanceOf(NullPointerException.class)
                .hasMessageContaining("ClimbingGymId cannot be null");
    }

    @Test
    void 제목이_null이라면_예외가_발생한다() {
        assertThatThrownBy(() -> {
            Schedule.create(
                    UserId.from(1L),
                    ClimbingGymId.from(1L),
                    null,
                    "description",
                    Instant.now()
            );
        }).isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Title cannot be null");
    }

    @Test
    void 일정_날짜가_null이라면_예외가_발생한다() {
        assertThatThrownBy(() -> {
            Schedule.create(
                    UserId.from(1L),
                    ClimbingGymId.from(1L),
                    "title",
                    "description",
                    null
            );
        }).isInstanceOf(NullPointerException.class)
                .hasMessageContaining("ScheduleDate cannot be null");
    }

    @Test
    void 업데이트() {
        var schedule = Schedule.create(
                UserId.from(1L),
                ClimbingGymId.from(1L),
                "title",
                "description",
                Instant.now()
        );
        schedule.update(
                ClimbingGymId.from(2L),
                "updated title",
                "updated description",
                Instant.now()
        );

        assertAll(
                () -> assertEquals(ClimbingGymId.from(2L), schedule.climbingGymId(), "클라이밍짐 ID는 변경되어야 합니다."),
                () -> assertEquals("updated title", schedule.title(), "제목은 변경되어야 합니다."),
                () -> assertEquals("updated description", schedule.memo(), "메모는 변경되어야 합니다."),
                () -> assertNotNull(schedule.updatedAt(), "업데이트 시간은 null이 아니어야 합니다.")
        );
    }

    @Test
    void 업데이트_시_클라이밍짐_아이디가_null이라면_예외가_발생한다() {
        var schedule = Schedule.create(
                UserId.from(1L),
                ClimbingGymId.from(1L),
                "title",
                "description",
                Instant.now()
        );

        assertThatThrownBy(() -> {
            schedule.update(
                    null,
                    "updated title",
                    "updated description",
                    Instant.now()
            );
        }).isInstanceOf(NullPointerException.class)
                .hasMessageContaining("ClimbingGymId cannot be null");
    }


    @Test
    void 업데이트_시_제목이_null이라면_예외가_발생한다() {
        var schedule = Schedule.create(
                UserId.from(1L),
                ClimbingGymId.from(1L),
                "title",
                "description",
                Instant.now()
        );

        assertThatThrownBy(() -> {
            schedule.update(
                    ClimbingGymId.from(2L),
                    null,
                    "updated description",
                    Instant.now()
            );
        }).isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Title cannot be null");
    }

    @Test
    void 스케줄의_주인이_아니라면_예외가_발생한다() {
        var schedule = Schedule.create(
                UserId.from(1L),
                ClimbingGymId.from(1L),
                "title",
                "description",
                Instant.now()
        );

        assertThatThrownBy(() -> schedule.validateOwner(UserId.from(2L)))
                .isInstanceOf(UnauthorizedScheduleException.class);
    }

    @Test
    void 스케줄의_주인이라면_예외가_발생하지_않는다() {
        var schedule = Schedule.create(
                UserId.from(1L),
                ClimbingGymId.from(1L),
                "title",
                "description",
                Instant.now()
        );

        assertThatCode(() -> schedule.validateOwner(UserId.from(1L))).doesNotThrowAnyException();
    }
}
