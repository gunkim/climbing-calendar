package github.gunkim.climbingcalendar.api.climbinggym.model.response;

import java.util.List;

public record GetChainClimbingGymResponse(
        String name,
        List<ClimbingGymItem> climbingGyms
) {
    public record ClimbingGymItem(
            Long id,
            String name,
            String address,
            List<LevelItem> levels,
            double latitude,
            double longitude
    ) {
        public record LevelItem(
                Long id,
                int startGrade,
                int endGrade,
                String color
        ) {
        }
    }
}
