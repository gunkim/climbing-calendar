package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.repository;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.repository.ClimbingGymRepository;
import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.dao.ClimbingGymDao;
import github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity.ClimbingGymEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClimbingGymRepositoryImpl implements ClimbingGymRepository {
    private final ClimbingGymDao climbingGymDao;

    @Override
    public List<ClimbingGym> findAll() {
        return climbingGymDao.findAll().stream()
                .map(ClimbingGymEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<ClimbingGym> findById(Long id) {
        return climbingGymDao.findById(id).map(ClimbingGymEntity::toDomain);
    }
}
