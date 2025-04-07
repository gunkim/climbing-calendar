package github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ClearId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.dao.ClearDao;
import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.ClearEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClearRepositoryImpl implements ClearRepository {
    private final ClearDao clearDao;

    @Override
    public Clear save(Clear clear) {
        return clearDao.save(ClearEntity.from(clear)).toDomain();
    }

    @Override
    public List<Clear> saveAll(List<Clear> clears) {
        return clearDao.saveAll(clears.stream().map(ClearEntity::from).toList()).stream().map(ClearEntity::toDomain).toList();
    }

    @Override
    @Transactional
    public void deleteByScheduleId(ScheduleId scheduleId) {
        clearDao.deleteByScheduleId(scheduleId.value());
    }

    @Override
    public Optional<Clear> findById(ClearId id) {
        return clearDao.findById(id.value()).map(ClearEntity::toDomain);
    }

    @Override
    public List<Clear> findAllByScheduleId(ScheduleId scheduleId) {
        return clearDao.findByScheduleId(scheduleId.value()).stream()
                .map(ClearEntity::toDomain)
                .toList();
    }

    @Override
    public List<Clear> findAllByScheduleIdIn(List<ScheduleId> scheduleIds) {
        return clearDao.findAllByScheduleIdIn(scheduleIds.stream()
                        .map(ScheduleId::value)
                        .toList()).stream()
                .map(ClearEntity::toDomain)
                .toList();
    }
}
