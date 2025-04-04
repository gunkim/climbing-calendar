package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.DomainTest;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.exception.UnauthorizedScheduleException;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import github.gunkim.climbingcalendar.tool.InMemoryClearRepository;
import github.gunkim.climbingcalendar.tool.InMemoryScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DeleteScheduleService")
class DeleteScheduleServiceTest extends DomainTest {
    private DeleteScheduleService sut;

    @BeforeEach
    void setUp() {
        var getScheduleService = new GetScheduleService(scheduleRepository);
        sut = new DeleteScheduleService(scheduleRepository, clearRepository, getScheduleService);
    }

    @Test
    void 사용자의_스케줄이_아니면_삭제가_거부된다() {
        var originalSchedule = createAndSaveInitialSchedule();

        assertThrows(UnauthorizedScheduleException.class, () ->
                        sut.deleteSchedule(originalSchedule.id(), UserId.from(2L)),
                "스케줄 소유자가 아닌 사용자가 삭제를 시도하면 UnauthorizedScheduleException이 발생해야 합니다."
        );
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
}