package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.api.schedule.model.ClearVO;
import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClearRepository clearRepository;

    public void createSchedule(Long userId, Long climbingGymId, String title, String memo, Instant scheduleDate, List<ClearVO> clearList) {
        Schedule schedule = scheduleRepository.save(
                Schedule.create(
                        userId,
                        climbingGymId,
                        title,
                        memo,
                        scheduleDate
                )
        );

        clearList.forEach(clearVO -> clearRepository.save(
                Clear.create(
                        schedule.id(),
                        clearVO.LevelId(),
                        clearVO.count()
                )
        ));
    }
}
