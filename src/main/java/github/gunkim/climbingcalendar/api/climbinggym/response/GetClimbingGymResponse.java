package github.gunkim.climbingcalendar.api.climbinggym.response;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;

public record GetClimbingGymResponse(
        Long id,
        String name,
        String address,
        double latitude,
        double longitude,
        boolean isParkingAvailable
) {
    public static GetClimbingGymResponse from(ClimbingGym climbingGym) {
        return new GetClimbingGymResponse(climbingGym.id(), climbingGym.name(), climbingGym.address(), climbingGym.latitude(), climbingGym.longitude(),
                climbingGym.isParkingAvailable());
    }
}
