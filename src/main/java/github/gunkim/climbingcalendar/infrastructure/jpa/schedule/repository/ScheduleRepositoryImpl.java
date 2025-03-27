package github.gunkim.climbingcalendar.infrastructure.jpa.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.dao.ScheduleDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity.ScheduleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {
    private final ScheduleDao scheduleDao;

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleDao.save(ScheduleEntity.from(schedule)).toDomain();
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return scheduleDao.findById(id).map(ScheduleEntity::toDomain);
    }
}
