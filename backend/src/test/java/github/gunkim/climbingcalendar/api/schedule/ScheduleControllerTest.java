package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.IntegrationTest;
import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.GeoLocation;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Grade;
import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ScheduleControllerTest extends IntegrationTest {

    @Test
    @DisplayName("GET /api/v1/schedules - 모든 스케줄 조회")
    void testGetSchedules() throws Exception {
        User user = createUser(
                User.registration(
                        "test@gmail.com",
                        "parkge",
                        "1234567890"
                )
        );

        ClimbingGym gym1 = addClimbingGym(
                ClimbingGym.create(
                        "Mountain Peak Gym",
                        "123 Mountain Road",
                        new GeoLocation(37.7749, -122.4194),
                        true
                )
        );

        ClimbingGym gym2 = addClimbingGym(
                ClimbingGym.create(
                        "Rocky Gym",
                        "456 Rocky Avenue",
                        new GeoLocation(40.7128, -74.0060),
                        false
                )
        );

        addLevel(Level.create(gym1.id(), Color.RED, Grade.from(1, 2)));
        addLevel(Level.create(gym1.id(), Color.BLUE, Grade.from(2, 3)));
        addLevel(Level.create(gym1.id(), Color.GREEN, Grade.from(3, 4)));
        addLevel(Level.create(gym2.id(), Color.YELLOW, Grade.from(1, 2)));
        addLevel(Level.create(gym2.id(), Color.BLACK, Grade.from(2, 3)));
        addLevel(Level.create(gym2.id(), Color.WHITE, Grade.from(3, 4)));

        Schedule schedule1 = addSchedule(Schedule.create(
                user.id(),
                gym1.id(),
                "Morning Climbing",
                "Go climbing early in the morning",
                Instant.parse("2023-11-10T08:00:00Z")
        ));

        Schedule schedule2 = addSchedule(Schedule.create(
                user.id(),
                gym2.id(),
                "Evening Climbing",
                "Relax and climb in the evening",
                Instant.parse("2023-11-15T18:00:00Z")
        ));

        addClear(Clear.create(
                schedule1.id(),
                LevelId.from(1L),
                3
        ));

        addClear(Clear.create(
                schedule1.id(),
                LevelId.from(2L),
                5
        ));

        addClear(Clear.create(
                schedule2.id(),
                LevelId.from(3L),
                2
        ));

        var token = jwtProvider.createToken(user.id());

        mockMvc.perform(get("/api/v1/schedules")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Morning Climbing"))
                .andExpect(jsonPath("$[0].scheduleDate").value("2023-11-10T08:00:00Z"))
                .andExpect(jsonPath("$[0].memo").value("Go climbing early in the morning"))
                .andExpect(jsonPath("$[0].climbingGymId").value(1L))
                .andExpect(jsonPath("$[0].climbingGymName").value("Mountain Peak Gym"))

                .andExpect(jsonPath("$[1].clearList").isArray())
                .andExpect(jsonPath("$[0].clearList[0].id").value(1L))
                .andExpect(jsonPath("$[0].clearList[0].count").value(3))
                .andExpect(jsonPath("$[0].clearList[1].id").value(2L))
                .andExpect(jsonPath("$[0].clearList[1].count").value(5))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Evening Climbing"))
                .andExpect(jsonPath("$[1].scheduleDate").value("2023-11-15T18:00:00Z"))
                .andExpect(jsonPath("$[1].memo").value("Relax and climb in the evening"))
                .andExpect(jsonPath("$[1].climbingGymId").value(2L))
                .andExpect(jsonPath("$[1].climbingGymName").value("Rocky Gym"))

                .andExpect(jsonPath("$[1].clearList").isArray())
                .andExpect(jsonPath("$[1].clearList[0].id").value(3L))
                .andExpect(jsonPath("$[1].clearList[0].count").value(2))
                .andDo(print());
    }
}