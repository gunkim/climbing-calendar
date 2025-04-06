package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.DomainTest;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearCommand;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.tool.InMemoryClearRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UpdateClearService")
class UpdateClearServiceTest extends DomainTest {
    private UpdateClearService sut;

    @BeforeEach
    void setUp() {
        sut = new UpdateClearService(clearRepository);
    }

    @Test
    void 업데이트된다() {
        ScheduleId scheduleId = ScheduleId.from(1L);
        List<ClearCommand> commands = List.of(
                new ClearCommand(new LevelId(1L), 3),
                new ClearCommand(new LevelId(2L), 5)
        );

        sut.updateClears(scheduleId, commands);

        List<Clear> clears = clearRepository.findAllByScheduleId(scheduleId);
        assertThat(clears).hasSize(2);
    }

    @Test
    void 리스트가_비어있다면_멱등성을_위해_모든_리스트가_제거된다() {
        ScheduleId scheduleId = ScheduleId.from(1L);
        Clear existingClear = Clear.create(scheduleId, new LevelId(1L), 3);
        clearRepository.save(existingClear);

        sut.updateClears(scheduleId, List.of());

        List<Clear> clears = clearRepository.findAllByScheduleId(scheduleId);
        assertThat(clears).isEmpty();
    }

    @Test
    void 카운트가_0인_항목은_제외되어_업데이트된다() {
        ScheduleId scheduleId = ScheduleId.from(1L);
        List<ClearCommand> commands = List.of(
                new ClearCommand(new LevelId(1L), 3),
                new ClearCommand(new LevelId(2L), 5),
                new ClearCommand(new LevelId(3L), 0)
        );
        sut.updateClears(scheduleId, commands);

        List<Clear> clears = clearRepository.findAllByScheduleId(scheduleId);
        assertThat(clears).hasSize(2);
    }
}