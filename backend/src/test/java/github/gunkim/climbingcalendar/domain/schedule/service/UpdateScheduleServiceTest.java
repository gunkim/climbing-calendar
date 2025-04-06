package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.DomainTest;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearCommand;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UpdateScheduleService")
class UpdateScheduleServiceTest extends DomainTest {
    private UpdateScheduleService sut;

    @BeforeEach
    void setUp() {
        var getScheduleService = new GetScheduleService(scheduleRepository);
        var updateClearService = new UpdateClearService(clearRepository);
        sut = new UpdateScheduleService(getScheduleService, updateClearService, scheduleRepository);
    }

    @Test
    void 업데이트된다() {
        var originalSchedule = createAndSaveInitialSchedule();

        var updatedSchedule = sut.updateSchedule(
                originalSchedule.id(),
                originalSchedule.climbingGymId(),
                "Updated Title",
                Instant.parse("2025-05-05T00:00:00Z"),
                "Updated Memo",
                List.of(new ClearCommand(LevelId.from(1L), 12))
        );

        assertScheduleUpdate(originalSchedule, updatedSchedule);
    }

    private Schedule createAndSaveInitialSchedule() {
        return scheduleRepository.save(
                Schedule.create(
                        UserId.from(1L),
                        ClimbingGymId.from(1L),
                        "Initial Title",
                        "Initial Memo",
                        Instant.parse("2025-04-04T00:00:00Z")
                )
        );
    }

    private void assertScheduleUpdate(Schedule original, Schedule updated) {
        assertAll("Updated Schedule",
                () -> assertEquals(original.id(), updated.id(), "스케줄 ID는 변경되지 않아야 합니다."),
                () -> assertEquals(original.userId(), updated.userId(), "사용자 ID는 변경되지 않아야 합니다."),
                () -> assertEquals(original.climbingGymId(), updated.climbingGymId(), "클라이밍짐 ID는 변경되지 않아야 합니다."),
                () -> assertEquals("Updated Title", updated.title(), "타이틀이 업데이트 되었어야 합니다."),
                () -> assertEquals("Updated Memo", updated.memo(), "메모가 업데이트 되었어야 합니다."),
                () -> assertEquals(Instant.parse("2025-05-05T00:00:00Z"), updated.scheduleDate(), "일정 날짜가 업데이트 되었어야 합니다.")
        );
    }
}