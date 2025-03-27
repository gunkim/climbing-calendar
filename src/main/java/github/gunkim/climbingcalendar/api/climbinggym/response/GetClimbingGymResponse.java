package github.gunkim.climbingcalendar.api.climbinggym.response;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetClimbingGymResponse(
        Long id,
        String name,
        String address,
        double latitude,
        double longitude,
        boolean isParkingAvailable
) {
    public static GetClimbingGymResponse from(ClimbingGym climbingGym) {
        return GetClimbingGymResponse.builder()
                .id(climbingGym.id())
                .name(climbingGym.name())
                .address(climbingGym.address())
                .latitude(climbingGym.latitude())
                .longitude(climbingGym.longitude())
                .isParkingAvailable(climbingGym.isParkingAvailable())
                .build();
    }
}
