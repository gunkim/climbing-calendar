package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ClearId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
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
class GetClearServiceTest {

    @Mock
    private ClearRepository clearRepository;

    @InjectMocks
    private GetClearService getClearService;

    @Test
    void testGetClear_Success() {
        ClearId clearId = ClearId.from(1L);
        ScheduleId scheduleId = ScheduleId.from(101L);
        LevelId levelId = new LevelId(1L);

        Clear mockClear = new Clear(
                clearId,
                scheduleId,
                levelId,
                10,
                Instant.now(),
                Instant.now()
        );

        when(clearRepository.findById(clearId)).thenReturn(Optional.of(mockClear));

        Clear result = getClearService.getClear(clearId);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(clearId);
        assertThat(result.scheduleId()).isEqualTo(scheduleId);
        assertThat(result.levelId()).isEqualTo(levelId);
        assertThat(result.count()).isEqualTo(10);
        verify(clearRepository).findById(clearId);
    }

    @Test
    void testGetClearsByScheduleId_Success() {
        ScheduleId scheduleId = ScheduleId.from(101L);
        LevelId levelId1 = new LevelId(1L);
        LevelId levelId2 = new LevelId(2L);

        List<Clear> mockClears = List.of(
                new Clear(
                        ClearId.from(1L),
                        scheduleId,
                        levelId1,
                        5,
                        Instant.now(),
                        Instant.now()
                ),
                new Clear(
                        ClearId.from(2L),
                        scheduleId,
                        levelId2,
                        15,
                        Instant.now(),
                        Instant.now()
                )
        );

        when(clearRepository.findByScheduleId(scheduleId)).thenReturn(mockClears);

        List<Clear> result = getClearService.getClears(scheduleId);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).scheduleId()).isEqualTo(scheduleId);
        assertThat(result.get(1).scheduleId()).isEqualTo(scheduleId);
        verify(clearRepository).findByScheduleId(scheduleId);
    }

    @Test
    void testGetClearsByScheduleIds_Success() {
        ScheduleId scheduleId1 = ScheduleId.from(101L);
        ScheduleId scheduleId2 = ScheduleId.from(102L);
        LevelId levelId1 = new LevelId(1L);
        LevelId levelId2 = new LevelId(2L);

        List<Clear> mockClears = List.of(
                new Clear(
                        ClearId.from(1L),
                        scheduleId1,
                        levelId1,
                        5,
                        Instant.now(),
                        Instant.now()
                ),
                new Clear(
                        ClearId.from(2L),
                        scheduleId2,
                        levelId2,
                        15,
                        Instant.now(),
                        Instant.now()
                )
        );

        when(clearRepository.findByScheduleIdIn(List.of(scheduleId1, scheduleId2))).thenReturn(mockClears);

        List<Clear> result = getClearService.getClears(List.of(scheduleId1, scheduleId2));

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).scheduleId()).isEqualTo(scheduleId1);
        assertThat(result.get(1).scheduleId()).isEqualTo(scheduleId2);
        verify(clearRepository).findByScheduleIdIn(List.of(scheduleId1, scheduleId2));
    }

    @Test
    void testGetClear_ThrowsException_WhenNotFound() {
        ClearId clearId = ClearId.from(1L);
        when(clearRepository.findById(clearId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            getClearService.getClear(clearId);
        });

        assertThat(exception).isNotNull();
        verify(clearRepository).findById(clearId);
    }
}