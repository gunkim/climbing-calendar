package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.repository;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
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
    public List<Level> findByClimbingGymId(Long climbingGymId) {
        return levelDao.findByClimbingGymId(climbingGymId).stream()
                .map(LevelEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Level> findById(Long id) {
        return levelDao.findById(id).map(LevelEntity::toDomain);
    }
}
