package github.gunkim.climbingcalendar.domain.schedule.service;

import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.ClearCommand;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateClearService {
    private final ClearRepository clearRepository;

    public void updateClears(ScheduleId scheduleId, List<ClearCommand> clearCommands) {
        deleteExistingClears(scheduleId);
        List<Clear> clearsToSave = filterAndConvertClearCommands(scheduleId, clearCommands);
        saveClears(clearsToSave);
    }

    private void deleteExistingClears(ScheduleId scheduleId) {
        clearRepository.deleteByScheduleId(scheduleId);
    }

    private List<Clear> filterAndConvertClearCommands(ScheduleId scheduleId, List<ClearCommand> clearCommands) {
        return clearCommands.stream()
                .filter(ClearCommand::isNotEmpty)
                .map(command -> command.toClear(scheduleId))
                .toList();
    }

    private void saveClears(List<Clear> clearsToSave) {
        clearRepository.saveAll(clearsToSave);
    }
}