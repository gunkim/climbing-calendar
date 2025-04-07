package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleReadRepository;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSchedulesCountService {
    private final ScheduleReadRepository scheduleReadRepository;

    public int getSchedulesCount(UserId userId, Integer year, Integer month) {
        if (year != null) {
            return scheduleReadRepository.countByUserIdAndYear(userId, year);
        } else if (month != null) {
            return scheduleReadRepository.countByUserIdAndMonth(userId, month);
        }
        return scheduleReadRepository.countByUserId(userId);
    }
}
