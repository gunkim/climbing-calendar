package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ClearId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetClearService {
    private final ClearRepository clearRepository;

    public Clear getClear(ClearId clearId) {
        return clearRepository.findById(clearId).orElseThrow();
    }

    public List<Clear> getClears(ScheduleId scheduleId){
        return clearRepository.findAllByScheduleId(scheduleId);
    }
}
