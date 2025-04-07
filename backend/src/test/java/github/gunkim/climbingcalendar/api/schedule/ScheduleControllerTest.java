package github.gunkim.climbingcalendar.api.schedule;

import github.gunkim.climbingcalendar.IntegrationTest;
import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.GeoLocation;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Grade;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.user.model.User;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.dao.ClimbingGymDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.dao.LevelDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.ClimbingGymEntity;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.LevelEntity;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.dao.ScheduleDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.ScheduleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ScheduleController 테스트")
class ScheduleControllerTest extends IntegrationTest {
    @Autowired
    private ClimbingGymDao climbingGymDao;
    @Autowired
    private ScheduleDao scheduleDao;
    @Autowired
    private LevelDao levelDao;

    private User testUser;
    private ClimbingGym gym1, gym2;
    private Schedule schedule1, schedule2;

    @BeforeEach
    void setUp() {
        initializeTestUserAndGyms();
        initializeSchedules();
    }

    @Test
    @DisplayName("[GET] /api/v1/schedules - 모든 스케줄 조회")
    void testGetSchedules() throws Exception {
        String token = createAccessToken(testUser);
        String currentYear = String.valueOf(Instant.now().atZone(ZoneId.systemDefault()).getYear());
        String currentMonth = String.valueOf(Instant.now().atZone(ZoneId.systemDefault()).getMonth().getValue());

        mockMvc.perform(get("/api/v1/schedules")
                        .param("page", "0")
                        .param("size", "10")
//                        .param("year", currentYear)
//                        .param("month", currentMonth)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(schedule1.id().value()))
                .andExpect(jsonPath("$[1].id").value(schedule2.id().value()))
                .andDo(print());
    }

    @Nested
    @DisplayName("[GET] /api/v1/schedules/count")
    class GetSchedulesCountTests {
        @Test
        @DisplayName("모든 스케줄 개수 조회 (필터 없음)")
        void testGetSchedulesCountWithoutFilter() throws Exception {
            String token = createAccessToken(testUser);

            mockMvc.perform(get("/api/v1/schedules/count")
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.count").value(2))
                    .andDo(print());
        }

        @Test
        @DisplayName("?year={now} - 연도 필터를 사용한 스케줄 개수 조회")
        void testGetSchedulesCountWithYearFilter() throws Exception {
            String token = createAccessToken(testUser);
            int currentYear = Instant.now().atZone(ZoneId.systemDefault()).getYear();

            mockMvc.perform(get("/api/v1/schedules/count")
                            .param("year", String.valueOf(currentYear))
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.count").value(2))
                    .andDo(print());
        }

        @Test
        @DisplayName("?month={now} - 월 필터를 사용한 스케줄 개수 조회")
        void testGetSchedulesCountWithMonthFilter() throws Exception {
            String token = createAccessToken(testUser);
            String currentMonth = String.valueOf(Instant.now().atZone(ZoneId.systemDefault()).getMonth().getValue());

            mockMvc.perform(get("/api/v1/schedules/count")
                            .param("month", currentMonth)
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.count").value(2))
                    .andDo(print());
        }
    }

    private void initializeTestUserAndGyms() {
        testUser = createUser("test@gmail.com", "parkge", "1234567890");

        gym1 = addClimbingGym(ClimbingGym.create(
                "Mountain Peak Gym", "123 Mountain Road",
                new GeoLocation(37.7749, -122.4194), true));

        gym2 = addClimbingGym(ClimbingGym.create(
                "Rocky Gym", "456 Rocky Avenue",
                new GeoLocation(40.7128, -74.0060), false));

        addGymLevels(gym1);
        addGymLevels(gym2);
    }

    private void initializeSchedules() {
        schedule1 = addSchedule(Schedule.create(
                testUser.id(), gym1.id(), "Morning Climbing",
                "Go climbing early in the morning", Instant.now()));

        schedule2 = addSchedule(Schedule.create(
                testUser.id(), gym2.id(), "Evening Climbing",
                "Relax and climb in the evening", Instant.now()));
    }

    private void addGymLevels(ClimbingGym gym) {
        addLevel(Level.create(gym.id(), Color.RED, Grade.from(1, 2)));
        addLevel(Level.create(gym.id(), Color.BLUE, Grade.from(2, 3)));
        addLevel(Level.create(gym.id(), Color.GREEN, Grade.from(3, 4)));
    }

    private ClimbingGym addClimbingGym(ClimbingGym climbingGym) {
        return climbingGymDao.save(ClimbingGymEntity.from(climbingGym)).toDomain();
    }

    private void addLevel(Level level) {
        levelDao.save(LevelEntity.from(level));
    }

    private Schedule addSchedule(Schedule schedule) {
        return scheduleDao.save(ScheduleEntity.from(schedule)).toDomain();
    }
}