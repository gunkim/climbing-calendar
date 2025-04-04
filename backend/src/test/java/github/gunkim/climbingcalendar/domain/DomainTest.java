package github.gunkim.climbingcalendar.domain;

import github.gunkim.climbingcalendar.tool.InMemoryClearRepository;
import github.gunkim.climbingcalendar.tool.InMemoryScheduleRepository;
import org.junit.jupiter.api.BeforeEach;

public class DomainTest {
    protected InMemoryScheduleRepository scheduleRepository;
    protected InMemoryClearRepository clearRepository;

    @BeforeEach
    void setUp() {
        scheduleRepository = new InMemoryScheduleRepository();
        clearRepository = new InMemoryClearRepository();
    }
}
