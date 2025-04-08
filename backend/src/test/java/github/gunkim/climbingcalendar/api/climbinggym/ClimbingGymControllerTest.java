package github.gunkim.climbingcalendar.api.climbinggym;

import github.gunkim.climbingcalendar.IntegrationTest;
import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Color;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.GeoLocation;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.Grade;
import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.user.model.User;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.dao.ClimbingGymDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.dao.LevelDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.ClimbingGymEntity;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.LevelEntity;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.dao.ClearDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.dao.ScheduleDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.ClearEntity;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.ScheduleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("ClimbingGymController 테스트")
class ClimbingGymControllerTest extends IntegrationTest {
    @Autowired
    private ClimbingGymDao climbingGymDao;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private ClearDao clearDao;

    private User testUser;
    private ClimbingGym gym1, gym2;
    private Map<ClimbingGymId, List<Level>> levelMap = new HashMap<>();

    @BeforeEach
    void setUp() {
        initializeTestUserAndGyms();
    }

    @Test
    @DisplayName("[GET] /api/v1/climbing-gyms - 모든 클라이밍 짐 조회")
    void testGetClimbingGyms() throws Exception {
        String token = createAccessToken(testUser);

        mockMvc.perform(get("/api/v1/climbing-gyms")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(gym1.id().value()))
                .andExpect(jsonPath("$[0].name").value(gym1.name()))
                .andExpect(jsonPath("$[1].id").value(gym2.id().value()))
                .andExpect(jsonPath("$[1].name").value(gym2.name()))
                .andDo(print());
    }

    @Test
    @DisplayName("[GET] /api/v1/climbing-gyms/{id}/levels - 특정 클라이밍 짐의 레벨 목록 조회")
    void testGetClimbingGymOfLevels() throws Exception {
        String token = createAccessToken(testUser);
        long gymId = gym1.id().value();

        mockMvc.perform(get("/api/v1/climbing-gyms/{id}/levels", gymId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(levelMap.get(gym1.id()).get(0).id().value()))
                .andExpect(jsonPath("$[0].color").value(levelMap.get(gym1.id()).get(0).color().name()))
                .andExpect(jsonPath("$[0].startGrade").value(levelMap.get(gym1.id()).get(0).grade().startGrade()))
                .andExpect(jsonPath("$[0].endGrade").value(levelMap.get(gym1.id()).get(0).grade().endGrade()))
                .andExpect(jsonPath("$[1].id").value(levelMap.get(gym1.id()).get(1).id().value()))
                .andExpect(jsonPath("$[1].color").value(levelMap.get(gym1.id()).get(1).color().name()))
                .andExpect(jsonPath("$[1].startGrade").value(levelMap.get(gym1.id()).get(1).grade().startGrade()))
                .andExpect(jsonPath("$[1].endGrade").value(levelMap.get(gym1.id()).get(1).grade().endGrade()))
                .andExpect(jsonPath("$[2].id").value(levelMap.get(gym1.id()).get(2).id().value()))
                .andExpect(jsonPath("$[2].color").value(levelMap.get(gym1.id()).get(2).color().name()))
                .andExpect(jsonPath("$[2].startGrade").value(levelMap.get(gym1.id()).get(2).grade().startGrade()))
                .andExpect(jsonPath("$[2].endGrade").value(levelMap.get(gym1.id()).get(2).grade().endGrade()))
                .andDo(print());
    }

    @Nested
    @DisplayName("[GET] /api/v1/climbing-gyms/count")
    class GetClimbingGymCountTests {
        @Test
        @DisplayName("필터 조건이 없을 경우 사용자의 암장 별 방문 횟수와 클리어 횟수를 조회")
        void testGetClimbingGymOfVisitAndClearCounts() throws Exception {
            String token = createAccessToken(testUser);

            mockMvc.perform(get("/api/v1/climbing-gyms/count")
                            .param("limit", "10")
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].climbingGymName").value(gym1.name()))
                    .andExpect(jsonPath("$[0].visitCount").value(3))
                    .andExpect(jsonPath("$[0].clearCount").value(6))
                    .andExpect(jsonPath("$[0].lastVisitDate").value("2025-04-12T00:00:00Z"))
                    .andExpect(jsonPath("$[1].climbingGymName").value(gym2.name()))
                    .andExpect(jsonPath("$[1].visitCount").value(2))
                    .andExpect(jsonPath("$[1].clearCount").value(7))
                    .andExpect(jsonPath("$[1].lastVisitDate").value("2025-04-13T00:00:00Z"))
                    .andDo(print());
        }

        @Test
        @DisplayName("사용자의 암장 별 방문 횟수와 클리어 횟수를 조회 가장 최근에 방문한 암장 순으로 정렬")
        void testGetClimbingGymOfVisitAndClearCountsOrderByClear() throws Exception {
            String token = createAccessToken(testUser);

            mockMvc.perform(get("/api/v1/climbing-gyms/count")
                            .param("limit", "10")
                            .param("orderBy", "recent")
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].climbingGymName").value(gym2.name()))
                    .andExpect(jsonPath("$[0].visitCount").value(2))
                    .andExpect(jsonPath("$[0].clearCount").value(7))
                    .andExpect(jsonPath("$[0].lastVisitDate").value("2025-04-13T00:00:00Z"))
                    .andExpect(jsonPath("$[1].climbingGymName").value(gym1.name()))
                    .andExpect(jsonPath("$[1].visitCount").value(3))
                    .andExpect(jsonPath("$[1].clearCount").value(6))
                    .andExpect(jsonPath("$[1].lastVisitDate").value("2025-04-12T00:00:00Z"))
                    .andDo(print());
        }

        @Test
        @DisplayName("사용자의 암장 별 방문 횟수와 클리어 횟수를 조회 가장 많이 방문한 암장 순으로 정렬")
        void testGetClimbingGymOfVisitAndClearCountsWithLimit() throws Exception {
            String token = createAccessToken(testUser);

            mockMvc.perform(get("/api/v1/climbing-gyms/count")
                            .param("limit", "5")
                            .param("orderBy", "visit")
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].climbingGymName").value(gym1.name()))
                    .andExpect(jsonPath("$[0].visitCount").value(3))
                    .andExpect(jsonPath("$[0].clearCount").value(6))
                    .andExpect(jsonPath("$[0].lastVisitDate").value("2025-04-12T00:00:00Z"))
                    .andExpect(jsonPath("$[1].climbingGymName").value(gym2.name()))
                    .andExpect(jsonPath("$[1].visitCount").value(2))
                    .andExpect(jsonPath("$[1].clearCount").value(7))
                    .andExpect(jsonPath("$[1].lastVisitDate").value("2025-04-13T00:00:00Z"))
                    .andDo(print());
        }
    }

    private void initializeTestUserAndGyms() {
        testUser = createUser("test@gmail.com", "parkge", "1234567890");

        gym1 = addClimbingGym(ClimbingGym.create(
                "더 클라임 강남점", "서울특별시 강남구 강남대로 123",
                new GeoLocation(37.498095, 127.027610), true));

        gym2 = addClimbingGym(ClimbingGym.create(
                "클라이밍 파크 홍대점", "서울특별시 마포구 홍대로 456",
                new GeoLocation(37.556530, 126.924120), false));

        addGymLevels(gym1);
        addGymLevels(gym2);

        initializeTestSchedules();
    }

    private void initializeTestSchedules() {
        addScheduleWithClear(gym1, levelMap.get(gym1.id()).getFirst().id(), Instant.parse("2025-03-22T00:00:00Z"), 2);
        addScheduleWithClear(gym1, levelMap.get(gym1.id()).get(1).id(), Instant.parse("2025-04-02T00:00:00Z"), 3);
        addScheduleWithClear(gym1, levelMap.get(gym1.id()).get(2).id(), Instant.parse("2025-04-12T00:00:00Z"), 1);
        addScheduleWithClear(gym2, levelMap.get(gym2.id()).getFirst().id(), Instant.parse("2025-03-13T00:00:00Z"), 2);
        addScheduleWithClear(gym2, levelMap.get(gym2.id()).get(1).id(), Instant.parse("2025-04-13T00:00:00Z"), 5);
    }

    private void addScheduleWithClear(ClimbingGym gym, LevelId levelId, Instant scheduleDate, int clearCount) {
        var schedule = addSchedule(Schedule.create(testUser.id(), gym.id(), gym.name() + "방문", "재미있었다.", scheduleDate));
        addClear(schedule.id(), levelId, clearCount);
    }

    private void addGymLevels(ClimbingGym gym) {
        List<Level> levels = new ArrayList<>();
        levels.add(addLevel(Level.create(gym.id(), Color.RED, Grade.from(1, 2))));
        levels.add(addLevel(Level.create(gym.id(), Color.BLUE, Grade.from(2, 3))));
        levels.add(addLevel(Level.create(gym.id(), Color.GREEN, Grade.from(3, 4))));
        levelMap.put(gym.id(), levels);
    }

    private Schedule addSchedule(Schedule schedule) {
        return scheduleDao.save(ScheduleEntity.from(schedule)).toDomain();
    }

    private void addClear(ScheduleId scheduleId, LevelId levelId, int clearCount) {
        clearDao.save(ClearEntity.from(
                Clear.create(
                        scheduleId,
                        levelId,
                        clearCount
                )
        ));
    }

    private ClimbingGym addClimbingGym(ClimbingGym climbingGym) {
        return climbingGymDao.save(ClimbingGymEntity.from(climbingGym)).toDomain();
    }

    private Level addLevel(Level level) {
        return levelDao.save(LevelEntity.from(level)).toDomain();
    }
}