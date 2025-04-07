package github.gunkim.climbingcalendar;

import github.gunkim.climbingcalendar.application.JwtProvider;
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
    protected JwtProvider jwtProvider;
}
