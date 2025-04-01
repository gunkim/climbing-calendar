package github.gunkim.climbingcalendar.domain.climbinggym.service;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.repository.ClimbingGymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetClimbingGymService {
    private final ClimbingGymRepository climbingGymRepository;

    public List<ClimbingGym> getClimbingGyms() {
        return climbingGymRepository.findAll();
    }

    public ClimbingGym getClimbingGym(Long climbingGymId) {
        return climbingGymRepository.findById(climbingGymId).orElseThrow();
    }
}
