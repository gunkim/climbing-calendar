package github.gunkim.climbingcalendar.infrastructure.jpa.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;
import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.dao.ScheduleDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity.ScheduleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public void deleteById(ScheduleId scheduleId) {
        scheduleDao.deleteById(scheduleId.value());
    }

    @Override
    public Optional<Schedule> findById(ScheduleId id) {
        return scheduleDao.findById(id.value()).map(ScheduleEntity::toDomain);
    }

    @Override
    public List<Schedule> findByUserId(UserId userId) {
        return scheduleDao.findByUserId(userId.value()).stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }
}
