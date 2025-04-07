package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.DomainTest;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("GetSchedulesCountService")
public class GetSchedulesCountServiceTest extends DomainTest {
    private GetSchedulesCountService sut;

    @BeforeEach
    void setUp() {
        sut = new GetSchedulesCountService(scheduleRepository);
    }

    @Test
    void 특정_연도에_해당하는_스케줄_카운트를_반환한다() {
        createAndSaveInitialSchedules();
        var getSchedulesCount = sut.getSchedulesCount(UserId.from(1L), 2025, null);
        assertThat(getSchedulesCount).isEqualTo(2);
    }

    @Test
    void 특정_월에_해당하는_스케줄_카운트를_반환한다() {
        createAndSaveInitialSchedules();
        var getSchedulesCount = sut.getSchedulesCount(UserId.from(1L), null, 3);
        assertThat(getSchedulesCount).isEqualTo(1);
    }

    @Test
    void 연도와_월이_null인_경우_전체_스케줄_카운트를_반환한다() {
        createAndSaveInitialSchedules();
        var getSchedulesCount = sut.getSchedulesCount(UserId.from(1L), null, null);
        assertThat(getSchedulesCount).isEqualTo(3);
    }

    private void createAndSaveInitialSchedules() {
        scheduleRepository.save(
                Schedule.create(
                        UserId.from(1L),
                        ClimbingGymId.from(1L),
                        "Initial Title1",
                        "Initial Memo",
                        Instant.parse("2024-04-04T00:00:00Z")
                )
        );
        scheduleRepository.save(
                Schedule.create(
                        UserId.from(1L),
                        ClimbingGymId.from(1L),
                        "Initial Title2",
                        "Initial Memo",
                        Instant.parse("2025-03-04T00:00:00Z")
                )
        );
        scheduleRepository.save(
                Schedule.create(
                        UserId.from(1L),
                        ClimbingGymId.from(1L),
                        "Initial Title3",
                        "Initial Memo",
                        Instant.parse("2025-04-04T00:00:00Z")
                )
        );
    }
}
