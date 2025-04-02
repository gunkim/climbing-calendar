package github.gunkim.climbingcalendar.domain.climbinggym.model;

import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Geo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class ClimbingGym {
    private final ClimbingGymId id;
    private String name;
    private String address;
    private Geo geo;
    private boolean isParkingAvailable;
    private Instant createdAt;
    private Instant updatedAt;
}
