package github.gunkim.climbingcalendar.domain.rockclimbingwall.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class ClimbingGym {
    private final Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private boolean isParkingAvailable;
    private Instant createdAt;
    private Instant updatedAt;
}
