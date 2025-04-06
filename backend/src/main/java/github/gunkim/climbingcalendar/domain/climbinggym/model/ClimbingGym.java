package github.gunkim.climbingcalendar.domain.climbinggym.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.GeoLocation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
public class ClimbingGym {
    private final ClimbingGymId id;
    private String name;
    private String address;
    private GeoLocation geoLocation;
    private boolean isParkingAvailable;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    public ClimbingGym(ClimbingGymId id, String name, String address, GeoLocation geoLocation, boolean isParkingAvailable, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.geoLocation = geoLocation;
        this.isParkingAvailable = isParkingAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ClimbingGym create(String name, String address, GeoLocation geoLocation, boolean isParkingAvailable) {
        return ClimbingGym.builder()
                .name(name)
                .address(address)
                .geoLocation(geoLocation)
                .isParkingAvailable(isParkingAvailable)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
