package github.gunkim.climbingcalendar.domain.climbinggym.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.GeoLocation;
import github.gunkim.climbingcalendar.domain.climbinggym.repository.ClimbingGymRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GetClimbingGymServiceTest {

    @Mock
    private ClimbingGymRepository climbingGymRepository;

    @InjectMocks
    private GetClimbingGymService getClimbingGymService;

    @Test
    void testGetClimbingGyms_Success() {
        GeoLocation locationA = GeoLocation.from(37.7749, -122.4194);
        GeoLocation locationB = GeoLocation.from(40.7128, -74.0060);

        List<ClimbingGym> mockGyms = List.of(
                ClimbingGym.create(
                        "Gym A",
                        "Address A",
                        locationA,
                        true
                ),
                ClimbingGym.create(
                        "Gym B",
                        "Address B",
                        locationB,
                        false
                )
        );
        when(climbingGymRepository.findAll()).thenReturn(mockGyms);

        List<ClimbingGym> result = getClimbingGymService.getClimbingGyms();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).name()).isEqualTo("Gym A");
        assertThat(result.get(0).address()).isEqualTo("Address A");
        assertThat(result.get(0).geoLocation()).isEqualTo(locationA);
        assertThat(result.get(0).isParkingAvailable()).isTrue();
        assertThat(result.get(1).name()).isEqualTo("Gym B");
        assertThat(result.get(1).address()).isEqualTo("Address B");
        assertThat(result.get(1).geoLocation()).isEqualTo(locationB);
        assertThat(result.get(1).isParkingAvailable()).isFalse();
        verify(climbingGymRepository).findAll();
    }

    @Test
    void testGetClimbingGym_Success() {
        ClimbingGymId gymId = new ClimbingGymId(1L);
        GeoLocation location = GeoLocation.from(37.7749, -122.4194);

        ClimbingGym mockGym = new ClimbingGym(
                gymId,
                "Gym A",
                "Address A",
                GeoLocation.from(37.7749, -122.4194),
                true,
                Instant.now(),
                Instant.now()
        );

        when(climbingGymRepository.findById(gymId.value())).thenReturn(Optional.of(mockGym));

        ClimbingGym result = getClimbingGymService.getClimbingGym(gymId);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Gym A");
        assertThat(result.address()).isEqualTo("Address A");
        assertThat(result.geoLocation()).isEqualTo(location);
        assertThat(result.isParkingAvailable()).isTrue();
        verify(climbingGymRepository).findById(gymId.value());
    }

    @Test
    void testGetClimbingGym_ThrowsException_WhenNotFound() {
        ClimbingGymId gymId = new ClimbingGymId(1L);
        when(climbingGymRepository.findById(gymId.value())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            getClimbingGymService.getClimbingGym(gymId);
        });

        assertThat(exception).isNotNull();
        verify(climbingGymRepository).findById(gymId.value());
    }
}