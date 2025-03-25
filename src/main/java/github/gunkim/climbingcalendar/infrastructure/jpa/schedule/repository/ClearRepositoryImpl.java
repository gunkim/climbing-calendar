package github.gunkim.climbingcalendar.infrastructure.jpa.schedule.repository;

import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;
import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.dao.ClearDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.schedule.entity.ClearEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
