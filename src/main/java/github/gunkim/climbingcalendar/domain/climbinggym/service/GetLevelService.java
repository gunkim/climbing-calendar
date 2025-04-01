package github.gunkim.climbingcalendar.domain.climbinggym.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.Level;
import github.gunkim.climbingcalendar.domain.climbinggym.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetLevelService {
    private final LevelRepository levelRepository;

    public Level getLevel(Long levelId) {
        return levelRepository.findById(levelId).orElseThrow();
    }

    public List<Level> getLevels(Long climbingGymId) {
        return levelRepository.findByClimbingGymId(climbingGymId);
    }
}
