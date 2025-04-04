package github.gunkim.climbingcalendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.gunkim.climbingcalendar.application.JwtProvider;
import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.service.*;
import github.gunkim.climbingcalendar.domain.user.model.User;
import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.dao.ClimbingGymDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.dao.LevelDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity.ClimbingGymEntity;
import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity.LevelEntity;
import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.dao.ClearDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.dao.ScheduleDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity.ClearEntity;
import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity.ScheduleEntity;
import github.gunkim.climbingcalendar.infrastructure.jpa.user.dao.UserDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("local-h2")
@AutoConfigureMockMvc
public class IntegrationTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected JwtProvider jwtProvider;

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

    protected User createUser(User user) {
        return userDao.save(UserEntity.from(
                user
        )).toDomain();
    }

    protected ClimbingGym addClimbingGym(ClimbingGym climbingGym) {
        return climbingGymDao.save(ClimbingGymEntity.from(climbingGym)).toDomain();
    }

    protected Schedule addSchedule(Schedule schedule) {
        return scheduleDao.save(ScheduleEntity.from(schedule)).toDomain();
    }

    protected Level addLevel(Level level) {
        return levelDao.save(LevelEntity.from(level)).toDomain();
    }

    protected Clear addClear(Clear clear) {
        return clearDao.save(ClearEntity.from(clear)).toDomain();
    }
}
