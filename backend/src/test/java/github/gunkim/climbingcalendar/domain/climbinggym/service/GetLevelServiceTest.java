package github.gunkim.climbingcalendar.domain.climbinggym.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Grade;
import github.gunkim.climbingcalendar.domain.climbinggym.repository.LevelRepository;
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
class GetLevelServiceTest {

    @Mock
    private LevelRepository levelRepository;

    @InjectMocks
    private GetLevelService getLevelService;

    @Test
    void testGetLevel_Success() {
        LevelId levelId = new LevelId(1L);
        Level mockLevel = new Level(levelId, new ClimbingGymId(1L), Color.RED, Grade.from(1, 2), Instant.now(), Instant.now());

        when(levelRepository.findById(levelId)).thenReturn(Optional.of(mockLevel));

        Level result = getLevelService.getLevel(levelId);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(levelId);
        verify(levelRepository).findById(levelId);
    }

    @Test
    void testGetLevel_ThrowsException_WhenNotFound() {
        LevelId levelId = new LevelId(1L);
        when(levelRepository.findById(levelId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            getLevelService.getLevel(levelId);
        });

        assertThat(exception).isNotNull();
        verify(levelRepository).findById(levelId);
    }

    @Test
    void testGetLevelsByClimbingGymId_Success() {
        ClimbingGymId climbingGymId = new ClimbingGymId(1L);
        List<Level> mockLevels = List.of(
                new Level(new LevelId(1L), climbingGymId, Color.RED, Grade.from(1, 2), Instant.now(), Instant.now()),
                new Level(new LevelId(2L), climbingGymId, Color.BLUE, Grade.from(2, 3), Instant.now(), Instant.now())
        );
        when(levelRepository.findByClimbingGymId(climbingGymId)).thenReturn(mockLevels);

        List<Level> result = getLevelService.getLevels(climbingGymId);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(new LevelId(1L));
        verify(levelRepository).findByClimbingGymId(climbingGymId);
    }

    @Test
    void testGetLevels_Success() {
        ClimbingGymId climbingGymId = new ClimbingGymId(1L);
        List<Level> mockLevels = List.of(
                new Level(new LevelId(1L), climbingGymId, Color.RED, Grade.from(1, 2), Instant.now(), Instant.now()),
                new Level(new LevelId(2L), climbingGymId, Color.BLUE, Grade.from(2, 3), Instant.now(), Instant.now())
        );
        when(levelRepository.findAll()).thenReturn(mockLevels);

        List<Level> result = getLevelService.getLevels();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).color()).isEqualTo(Color.RED);
        verify(levelRepository).findAll();
    }

    @Test
    void testGetLevelsByIds_Success() {
        ClimbingGymId climbingGymId = new ClimbingGymId(1L);
        List<LevelId> levelIds = List.of(new LevelId(1L), new LevelId(2L));
        List<Level> mockLevels = List.of(
                new Level(new LevelId(1L), climbingGymId, Color.RED, Grade.from(1, 2), Instant.now(), Instant.now()),
                new Level(new LevelId(2L), climbingGymId, Color.BLUE, Grade.from(2, 3), Instant.now(), Instant.now())
        );
        when(levelRepository.findByIdsIn(levelIds)).thenReturn(mockLevels);

        List<Level> result = getLevelService.getLevels(levelIds);

        assertThat(result).hasSize(2);
        assertThat(result.get(1).grade().endGrade()).isEqualTo(3);
        verify(levelRepository).findByIdsIn(levelIds);
    }
}