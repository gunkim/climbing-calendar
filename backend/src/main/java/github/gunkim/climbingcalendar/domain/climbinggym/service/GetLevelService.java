package github.gunkim.climbingcalendar.domain.climbinggym.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.LevelId;
import github.gunkim.climbingcalendar.domain.climbinggym.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetLevelService {
    private final LevelRepository levelRepository;

    public Level getLevel(LevelId levelId) {
        return levelRepository.findById(levelId).orElseThrow();
    }

    public List<Level> getLevels(ClimbingGymId climbingGymId) {
        return levelRepository.findByClimbingGymId(climbingGymId);
    }

    public List<Level> getLevels() {
        return levelRepository.findAll();
    }

    public List<Level> getLevels(List<LevelId> levelIds) {
        return levelRepository.findAllByIdIn(levelIds);
    }
}
