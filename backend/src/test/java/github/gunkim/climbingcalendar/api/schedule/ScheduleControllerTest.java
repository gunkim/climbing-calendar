package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.IntegrationTest;
import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.GeoLocation;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Grade;
import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.user.model.User;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.dao.ClimbingGymDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.dao.LevelDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.ClimbingGymEntity;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.LevelEntity;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.dao.ClearDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.dao.ScheduleDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.ClearEntity;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.ScheduleEntity;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.user.dao.UserDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ScheduleControllerTest extends IntegrationTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ClimbingGymDao climbingGymDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private ClearDao clearDao;

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

        Level level1 = addLevel(Level.create(gym1.id(), Color.RED, Grade.from(1, 2)));
        Level level2 = addLevel(Level.create(gym1.id(), Color.BLUE, Grade.from(2, 3)));
        Level level3 = addLevel(Level.create(gym1.id(), Color.GREEN, Grade.from(3, 4)));
        addLevel(Level.create(gym2.id(), Color.YELLOW, Grade.from(1, 2)));
        addLevel(Level.create(gym2.id(), Color.BLACK, Grade.from(2, 3)));
        addLevel(Level.create(gym2.id(), Color.WHITE, Grade.from(3, 4)));

        Schedule schedule1 = addSchedule(Schedule.create(
                user.id(),
                gym1.id(),
                "Morning Climbing",
                "Go climbing early in the morning",
                Instant.now()
        ));

        Schedule schedule2 = addSchedule(Schedule.create(
                user.id(),
                gym2.id(),
                "Evening Climbing",
                "Relax and climb in the evening",
                Instant.now()
        ));

        Clear clear1 = addClear(Clear.create(
                schedule1.id(),
                level1.id(),
                3
        ));

        Clear clear2 = addClear(Clear.create(
                schedule1.id(),
                level2.id(),
                5
        ));

        Clear clear3 = addClear(Clear.create(
                schedule2.id(),
                level3.id(),
                2
        ));
        var token = jwtProvider.createToken(user.id());
        Month month = Instant.now().atZone(ZoneId.systemDefault()).getMonth();

        mockMvc.perform(get("/api/v1/schedules")
                        .param("page", "0")
                        .param("size", "10")
                        .param("month", month.name())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(schedule1.id().value()))
                .andExpect(jsonPath("$[0].title").value(schedule1.title()))
                .andExpect(jsonPath("$[0].scheduleDate").exists())
                .andExpect(jsonPath("$[0].memo").value(schedule1.memo()))
                .andExpect(jsonPath("$[0].climbingGymId").value(schedule1.climbingGymId().value()))
                .andExpect(jsonPath("$[0].climbingGymName").value(gym1.name()))
                .andExpect(jsonPath("$[0].clearList").isArray())
                .andExpect(jsonPath("$[0].clearList[0].id").value(clear1.id().value()))
                .andExpect(jsonPath("$[0].clearList[0].count").value(clear1.count()))
                .andExpect(jsonPath("$[0].clearList[1].id").value(clear2.id().value()))
                .andExpect(jsonPath("$[0].clearList[1].count").value(clear2.count()))
                .andExpect(jsonPath("$[1].id").value(schedule2.id().value()))
                .andExpect(jsonPath("$[1].title").value(schedule2.title()))
                .andExpect(jsonPath("$[1].scheduleDate").exists())
                .andExpect(jsonPath("$[1].memo").value(schedule2.memo()))
                .andExpect(jsonPath("$[1].climbingGymId").value(schedule2.climbingGymId().value()))
                .andExpect(jsonPath("$[1].climbingGymName").value(gym2.name()))
                .andExpect(jsonPath("$[1].clearList").isArray())
                .andExpect(jsonPath("$[1].clearList[0].id").value(clear3.id().value()))
                .andExpect(jsonPath("$[1].clearList[0].count").value(clear3.count()))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/v1/schedules/count?year={now} - 필터조건에 해당하는 모든 스케줄 갯수 조회")
    void testGetSchedulesCountByYear() throws Exception {
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

        Level level1 = addLevel(Level.create(gym1.id(), Color.RED, Grade.from(1, 2)));
        Level level2 = addLevel(Level.create(gym1.id(), Color.BLUE, Grade.from(2, 3)));
        Level level3 = addLevel(Level.create(gym1.id(), Color.GREEN, Grade.from(3, 4)));
        addLevel(Level.create(gym2.id(), Color.YELLOW, Grade.from(1, 2)));
        addLevel(Level.create(gym2.id(), Color.BLACK, Grade.from(2, 3)));
        addLevel(Level.create(gym2.id(), Color.WHITE, Grade.from(3, 4)));

        Schedule schedule1 = addSchedule(Schedule.create(
                user.id(),
                gym1.id(),
                "Morning Climbing",
                "Go climbing early in the morning",
                Instant.now()
        ));

        Schedule schedule2 = addSchedule(Schedule.create(
                user.id(),
                gym2.id(),
                "Evening Climbing",
                "Relax and climb in the evening",
                Instant.now()
        ));

        addClear(Clear.create(
                schedule1.id(),
                level1.id(),
                3
        ));

        addClear(Clear.create(
                schedule1.id(),
                level2.id(),
                5
        ));

        addClear(Clear.create(
                schedule2.id(),
                level3.id(),
                2
        ));
        var token = jwtProvider.createToken(user.id());

        int year = Instant.now().atZone(ZoneId.systemDefault()).getYear();
        mockMvc.perform(get("/api/v1/schedules/count")
                        .header("Authorization", "Bearer " + token)
                        .param("year", String.valueOf(year)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(2))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/v1/schedules/count?month={now} - 필터조건에 해당하는 모든 스케줄 갯수 조회")
    void testGetSchedulesCountByMonth() throws Exception {
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

        Level level1 = addLevel(Level.create(gym1.id(), Color.RED, Grade.from(1, 2)));
        Level level2 = addLevel(Level.create(gym1.id(), Color.BLUE, Grade.from(2, 3)));
        Level level3 = addLevel(Level.create(gym1.id(), Color.GREEN, Grade.from(3, 4)));
        addLevel(Level.create(gym2.id(), Color.YELLOW, Grade.from(1, 2)));
        addLevel(Level.create(gym2.id(), Color.BLACK, Grade.from(2, 3)));
        addLevel(Level.create(gym2.id(), Color.WHITE, Grade.from(3, 4)));

        Schedule schedule1 = addSchedule(Schedule.create(
                user.id(),
                gym1.id(),
                "Morning Climbing",
                "Go climbing early in the morning",
                Instant.now().minusSeconds(1000000000)
        ));

        Schedule schedule2 = addSchedule(Schedule.create(
                user.id(),
                gym2.id(),
                "Evening Climbing",
                "Relax and climb in the evening",
                Instant.now()
        ));

        addClear(Clear.create(
                schedule1.id(),
                level1.id(),
                3
        ));

        addClear(Clear.create(
                schedule1.id(),
                level2.id(),
                5
        ));

        addClear(Clear.create(
                schedule2.id(),
                level3.id(),
                2
        ));
        var token = jwtProvider.createToken(user.id());

        Month month = Instant.now().atZone(ZoneId.systemDefault()).getMonth();
        mockMvc.perform(get("/api/v1/schedules/count")
                        .header("Authorization", "Bearer " + token)
                        .param("month", String.valueOf(month.getValue())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(1))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/v1/schedules/count -  필터가 존재하지않는 경우 모든 스케줄 갯수 조회")
    void testGetSchedulesCountByNotParm() throws Exception {
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

        Level level1 = addLevel(Level.create(gym1.id(), Color.RED, Grade.from(1, 2)));
        Level level2 = addLevel(Level.create(gym1.id(), Color.BLUE, Grade.from(2, 3)));
        Level level3 = addLevel(Level.create(gym1.id(), Color.GREEN, Grade.from(3, 4)));
        addLevel(Level.create(gym2.id(), Color.YELLOW, Grade.from(1, 2)));
        addLevel(Level.create(gym2.id(), Color.BLACK, Grade.from(2, 3)));
        addLevel(Level.create(gym2.id(), Color.WHITE, Grade.from(3, 4)));

        Schedule schedule1 = addSchedule(Schedule.create(
                user.id(),
                gym1.id(),
                "Morning Climbing",
                "Go climbing early in the morning",
                Instant.now()
        ));

        Schedule schedule2 = addSchedule(Schedule.create(
                user.id(),
                gym2.id(),
                "Evening Climbing",
                "Relax and climb in the evening",
                Instant.now()
        ));

        addClear(Clear.create(
                schedule1.id(),
                level1.id(),
                3
        ));

        addClear(Clear.create(
                schedule1.id(),
                level2.id(),
                5
        ));

        addClear(Clear.create(
                schedule2.id(),
                level3.id(),
                2
        ));
        var token = jwtProvider.createToken(user.id());

        mockMvc.perform(get("/api/v1/schedules/count")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(2))
                .andDo(print());
    }


    private User createUser(User user) {
        return userDao.save(UserEntity.from(
                user
        )).toDomain();
    }

    private ClimbingGym addClimbingGym(ClimbingGym climbingGym) {
        return climbingGymDao.save(ClimbingGymEntity.from(climbingGym)).toDomain();
    }

    private Schedule addSchedule(Schedule schedule) {
        return scheduleDao.save(ScheduleEntity.from(schedule)).toDomain();
    }

    private Level addLevel(Level level) {
        return levelDao.save(LevelEntity.from(level)).toDomain();
    }

    private Clear addClear(Clear clear) {
        return clearDao.save(ClearEntity.from(clear)).toDomain();
    }
}