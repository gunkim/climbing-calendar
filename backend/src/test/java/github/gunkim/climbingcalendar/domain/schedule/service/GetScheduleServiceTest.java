package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private GetScheduleService getScheduleService;

    @Test
    void testGetSchedule_Success() {
        ScheduleId scheduleId = ScheduleId.from(1L);
        UserId userId = UserId.from(100L);
        ClimbingGymId climbingGymId = new ClimbingGymId(1L);

        Schedule mockSchedule = new Schedule(
                scheduleId,
                userId,
                climbingGymId,
                "Morning Climb",
                "Focus on endurance",
                Instant.parse("2023-10-15T09:00:00Z"),
                Instant.now(),
                Instant.now()
        );

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(mockSchedule));

        Schedule result = getScheduleService.getSchedule(scheduleId);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(scheduleId);
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.climbingGymId()).isEqualTo(climbingGymId);
        assertThat(result.title()).isEqualTo("Morning Climb");
        assertThat(result.memo()).isEqualTo("Focus on endurance");
        assertThat(result.scheduleDate()).isEqualTo(Instant.parse("2023-10-15T09:00:00Z"));
        verify(scheduleRepository).findById(scheduleId);
    }

    @Test
    void testGetSchedulesByUserId_Success() {
        UserId userId = UserId.from(100L);
        ClimbingGymId gymId1 = new ClimbingGymId(1L);
        ClimbingGymId gymId2 = new ClimbingGymId(2L);

        List<Schedule> mockSchedules = List.of(
                new Schedule(
                        ScheduleId.from(1L),
                        userId,
                        gymId1,
                        "Morning Session",
                        "Bouldering warm-ups",
                        Instant.parse("2023-10-15T09:00:00Z"),
                        Instant.now(),
                        Instant.now()
                ),
                new Schedule(
                        ScheduleId.from(2L),
                        userId,
                        gymId2,
                        "Evening Session",
                        "Lead climbing quickdraw practice",
                        Instant.parse("2023-10-15T18:00:00Z"),
                        Instant.now(),
                        Instant.now()
                )
        );

        when(scheduleRepository.findByUserId(userId)).thenReturn(mockSchedules);

        List<Schedule> result = getScheduleService.getSchedules(userId);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).userId()).isEqualTo(userId);
        assertThat(result.get(1).userId()).isEqualTo(userId);
        assertThat(result.get(0).title()).isEqualTo("Morning Session");
        assertThat(result.get(1).title()).isEqualTo("Evening Session");
        verify(scheduleRepository).findByUserId(userId);
    }

    @Test
    void testGetSchedule_ThrowsException_WhenNotFound() {
        ScheduleId scheduleId = ScheduleId.from(1L);
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            getScheduleService.getSchedule(scheduleId);
        });

        assertThat(exception).isNotNull();
        verify(scheduleRepository).findById(scheduleId);
    }
}