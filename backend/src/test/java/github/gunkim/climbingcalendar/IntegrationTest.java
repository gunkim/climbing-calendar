package github.gunkim.climbingcalendar;

import github.gunkim.climbingcalendar.application.JwtProvider;
import github.gunkim.climbingcalendar.domain.user.model.User;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.user.dao.UserDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDao userDao;

    protected User createUser(String email, String name, String profileImage) {
        return userDao.save(UserEntity.from(User.registration(email, name, profileImage))).toDomain();
    }

    protected String createAccessToken(User user) {
        return jwtProvider.createToken(user.id());
    }
}
