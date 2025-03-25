package github.gunkim.climbingcalendar.domain.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Clear;

import java.util.List;

public interface ClearRepository {
    Clear save(Clear clear);

    List<Clear> saveAll(List<Clear> list);
}
