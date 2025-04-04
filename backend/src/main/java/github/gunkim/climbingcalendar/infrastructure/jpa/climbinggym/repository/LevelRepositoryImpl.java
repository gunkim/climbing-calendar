package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.repository;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.climbinggym.repository.LevelRepository;
import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.dao.LevelDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity.LevelEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LevelRepositoryImpl implements LevelRepository {
    private final LevelDao levelDao;

    @Override
    public List<Level> findByClimbingGymId(ClimbingGymId climbingGymId) {
        return levelDao.findByClimbingGymId(climbingGymId.value()).stream()
                .map(LevelEntity::toDomain)
                .toList();
    }

    @Override
    public List<Level> findAllByIdsIn(List<LevelId> levelIds) {
        return levelDao.findAllByIdIn(levelIds.stream()
                        .map(LevelId::value)
                        .toList()).stream()
                .map(LevelEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Level> findById(LevelId id) {
        return levelDao.findById(id.value()).map(LevelEntity::toDomain);
    }

    @Override
    public List<Level> findAll() {
        return levelDao.findAll().stream()
                .map(LevelEntity::toDomain)
                .toList();
    }
}
