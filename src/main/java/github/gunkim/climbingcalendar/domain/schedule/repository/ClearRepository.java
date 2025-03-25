package github.gunkim.climbingcalendar.domain.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Clear;

public interface ClearRepository {
    Clear save(Clear clear);
}
